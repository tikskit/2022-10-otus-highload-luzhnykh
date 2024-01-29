# Асинхронная репликация

## Как выполнялось тестирование
В JMeter в 50 потоках в течение 1 минуты вызывалась АПИ /user/search и в неё передавались параметры, полученные из того же csv файла, который использовался для заполнения таблицы тестовыми данными. Это нужно, чтобы избежать кэширования данных на стороне постгриса.

Одновременно в 50 потоках в течение 1 минуты вызывалась АПИ /user/register, в которую передавались случайные данные для создания пользователей (берутся из другого генерёного csv файла)

Скрипт JMeter jmeter/register_search.jmx

Была настроена асинхронная репликация.

В первой части эксперимента обе АПИ, и /user/search, и /user/register смотрели только на мастер.

Во второй части эксперимента /user/search смотрела на слейв, а /user/register - на мастер.

До и между эксперементами удалялись вставленные записи, чтобы тестирование производилось на одинаковом наборе данных.
Перезапускались инстансы мастера и слейва, чтобы обнулить кэш страниц.
Выполнялся сброс статистики pg_stat_statements:
`SELECT pg_stat_statements_reset();`

Микросервис запускался в докере с параметром --cpus=10
Мастер и слейв запускались в докере с параметрами --cpus=1 -m 1Gb

Параметры Latency, Throughput брались из JMeter.
CPU и Mem из docker stats

Данные о вводе-выводе брались из  из pg_stat_statements:

    SELECT sum(shared_blks_read) as "количество страниц прочитано с диска"
    ,sum(shared_blks_written) as "количество страниц записано на диск"
    FROM pg_stat_statements;`

## Результаты тестирования, когда обе АПИ смотрели на мастер
| API             | Latency 95%, ms | Throughput, rps | CPU master, % | Mem maser, mb | CPU slave, % | Mem slave, mb | Страниц прочитано master | Страниц записано master | Страниц прочитано slave | Страниц записано slave |   
|-----------------|-----------------|-----------------|---------------|---------------|--------------|---------------|--------------------------|-------------------------|-------------------------|------------------------|
| /user/search    | 83              | 4073.1          | 176           | 101           | 0.38         | 82            | 22136                    | 33                      | 2                       | 0                      |
| /user/register  | 2518            | 40.7            | 176           | 101           | 0.38         | 82            | 22136                    | 33                      | 2                       | 0                      |


## Результаты тестирования, когда /user/search сотрела на slave
| API             | Latency 95%, ms | Throughput, rps | CPU master, % | Mem maser, mb | CPU slave, % | Mem slave, mb | Страниц прочитано master | Страниц записано master | Страниц прочитано slave | Страниц записано slave |
|-----------------|-----------------|-----------------|---------------|---------------|--------------|---------------|--------------------------|-------------------------|-------------------------|------------------------|
| /user/search    | 80              | 4512            | 2.4           | 165           | 101          | 144           | 2204                     | 43                      | 6584                    | 0                      |
| /user/register  | 1597            | 64              | 2,4           | 165           | 101          | 144           | 2204                     | 43                      | 6584                    | 0                      |


## Выводы
Разнесение на мастер и слейв не повлияло на la и пропускную способность АПИ, которая выполняла чтение. Однако пропускная способность апи, которая записывала улучшишась на 36,4%.

Кроме того, использование реплики позволило перенести нагрузку с мастера на слейв. Похоже, что запись не создает большой нагрузки на CPU, видимо из-за того, что эта операция тратит много времени на ожидание дискового воода-вывода. 


# Синхронная репликация
## Как выполнялось
1. Настроил postgresql.conf на мастере
```
ssl = off
wal_level = replica
max_wal_senders = 4 # expected slave num
```

2. Создал на мастере пользователя для репликации
```docker exec -it socnetmaster su - postgres -c psql
create role replicator with login replication password 'pass';
exit
```

3. Добавляем запись в socnetmaster/pg_hba.conf с subnet, которую полуил при создании сети
```
host    replication     replicator       __SUBNET__          md5
```

4. Перезапустил мастер
```
docker restart socnetmaster
```

5. Создал бэкап реплик:
```
docker exec -it socnetmaster bash
mkdir /socnetslavesync1
pg_basebackup -h socnetmaster -D /socnetslavesync1 -U replicator -v -P --wal-method=stream
exit
```
 
6. Копировал в директорию на диске:
```
docker cp socnetmaster:/socnetslavesync1 F:/OTUS/HighLoad/db/hw10/socnetslavesync1
```

7. Добавил файл standby.signal в директорию F:/OTUS/HighLoad/db/hw10/socnetslavesync1

8. Изменил postgresql.conf на реплике socnetslavesync1
```
primary_conninfo = 'host=socnetmaster port=5432 user=replicator password=pass application_name=socnetslavesync1'
```

9. Проделал всё то же самое ещё раз, чтобы добавить ещё одну реплику socnetslavesync2

10. Включил синхронную репликацию на socnetmaster
```
synchronous_commit = on
synchronous_standby_names = 'FIRST 2 (socnetslavesync1, socnetslavesync2)'
```

11. Перечитал конфиг
```
docker exec -it socnetmaster su - postgres -c psql
select pg_reload_conf();
exit;
```

12. Проверил, что появилось две синхронные реплики:
```
docker exec -it socnetmaster su - postgres -c psql
select application_name, sync_state from pg_stat_replication;
exit;
```

Увидел такой вывод:
```
postgres=# select application_name, sync_state from pg_stat_replication;
application_name | sync_state
------------------+------------
socnetslave1     | async
socnetslavesync1 | sync
socnetslavesync2 | sync
```

Две синхронные реприки и одна осинхронная, оставшаяся от предыдущих экспериментов, я не стал её отключать.

Далее я удалил все записи из таблицы socnet.users и при помощи jMeter (jmeter/insert.jmx) стал в 10 потоков выполнять
insert в эту таблицу. Данные брались из файла jmeter/registerdata.csv

Убил процесс мастера командой
```docker container kill socnetmaster```

Запромоутил одну из синхронных реплик до мастера, а вторую синхронную реплику подключил к ней: 

```
postgres=# select application_name, sync_state from pg_stat_replication;
application_name | sync_state
------------------+------------
socnetslavesync2 | quorum
```


Далее в бивере смотрел, сколько записей есть в таблицах socnet.users:

| Реплика          | Количество записей | Описание реплики                                          |
|------------------|--------------------|-----------------------------------------------------------|
| socnetmaster     | 1189               | Бывший мастер                                             |
| socnetslavesync1 | 1184               | Изначально синхронный слейв, был пропромоучен до мастера  |
| socnetslavesync2 | 1184               | Синхронный слейв, был переключен на socnetslavesync1      |
| socnetslave1     | 1184               | Асинхронный слейв                                         |


## Выводы
При внезапном отключении мастера, даже при настроенных 2х синхронных репликах, было потеряно 5 записей.