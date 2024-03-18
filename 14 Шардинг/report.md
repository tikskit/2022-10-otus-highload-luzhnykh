# Выбираем ключи шардирования
Для хранения диалогов будет использоваться таблица:
```
create table dialogs(
    senderid varchar(36) not null,
    receiverid varchar(36) not null,
    text varchar not null,
    datetime timestamp with time zone not null
)
```

Поэтому, для считывания всего диалога будем использовать два похожих запроса:
```
select d."text", d.datetime
from dialogs d
where d.senderid = 'A'
and d.receiverid = 'B'
order by d.datetime;
```
и
```
select d."text", d.datetime
from dialogs d
where d.senderid = 'B'
and d.receiverid = 'A'
order by d.datetime;
```
Судя по планам выполнения, два эти запроса в сумме выполняются не хуже, чем запросы, которые могут выбрать весь диалог, 
такие как:
```
select d."text", d.senderid, d.receiverid, d.datetime
from dialogs d
where (d.senderid = 'A' and d.receiverid = 'B')
	or (d.senderid = 'B' and d.receiverid = 'A')
order by d.datetime;
```
или
```
select d."text", d.senderid, d.receiverid, d.datetime
from dialogs d
where d.senderid = 'A'
	and d.receiverid = 'B'
union all
select d."text", d.senderid, d.receiverid, d.datetime
from dialogs d
where d.senderid = 'B'
	and d.receiverid = 'A'
order by datetime;
```

Для горизонтального масштабирования первые два запроса подходят лучше, потому что позвоняют шардироваться по одному ключу 
senderid.
Тогда у нас все сообщения одного пользователя оказываются на одном шарде. И это, вроде, нормально, даже в случае с celebrity:
им пишут многие, но пишут сами/отвечают они как обычные пользователи или меньше. Таким образом это не приведет перекосу данных
на какому-то одном шарде, а все сообщения будут размазаны по всем шардам равномерно.

Недостатком такого подхода является то, что диалоги придется склеивать в приложении, выполняя сортировку сообщений в памяти.
Кроме того, он не позволяет на уровне запроса выполнять пагинацию сообщений, а такая
функция точно нужна. Поэтому придется реализовывать пагинацию тоже в коде приложения, а не в запросах.
Эти операции увеличат нагрузку на CPU и память. При необходимости можно увеличивать количество инстансов приложения.

# Настраиваем citus

1. Сперва запускаем через docker-compose
Запускаем:
```
docker-compose -p citus up --scale worker=2 -d
```

Далее вручную создаем схему и таблицы

2. Для этого подключимся по psql:
```
docker exec -it citus_master psql -U postgres
```
3. Создадим схему, таблицы:

```
CREATE SCHEMA socnet;
CREATE TABLE socnet.dialogs (senderid VARCHAR(36) NOT NULL, receiverid VARCHAR(36) NOT NULL, text VARCHAR NOT NULL, datetime TIMESTAMP with time zone NOT NULL);
CREATE INDEX ix_dialogs_senderid_receiverid_datetime ON socnet.dialogs(senderid, receiverid, datetime);
CREATE TABLE socnet.users (userid VARCHAR(36) NOT NULL, firstname VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, dob date NOT NULL, biography VARCHAR, city VARCHAR(50), CONSTRAINT pk_users PRIMARY KEY (userid));
CREATE TABLE socnet.accounts (userid VARCHAR(36) NOT NULL, passhash INTEGER NOT NULL, CONSTRAINT pk_account PRIMARY KEY (userid));
CREATE TABLE socnet.tokens (token VARCHAR(36) NOT NULL, CONSTRAINT pk_token PRIMARY KEY (token));
GRANT USAGE ON SCHEMA socnet TO postgres;

insert into socnet.dialogs(senderid, receiverid, text, datetime) values('7f701cb4-71e3-421f-a83e-5d754ce3dd92', 'a50b3982-5ca6-409e-ad38-aeb783bf6e73', 'Привет, пойдешь гулять?', now());
insert into socnet.dialogs(senderid, receiverid, text, datetime) values('a50b3982-5ca6-409e-ad38-aeb783bf6e73', '7f701cb4-71e3-421f-a83e-5d754ce3dd92', 'Привет, я ещё уроки не сделал', now());
insert into socnet.dialogs(senderid, receiverid, text, datetime) values('7f701cb4-71e3-421f-a83e-5d754ce3dd92', 'a50b3982-5ca6-409e-ad38-aeb783bf6e73', 'Ок, а когда сделаешь?', now());
insert into socnet.dialogs(senderid, receiverid, text, datetime) values('a50b3982-5ca6-409e-ad38-aeb783bf6e73', '7f701cb4-71e3-421f-a83e-5d754ce3dd92', 'Часа через два', now());

insert into socnet.tokens(token) values('7f701cb4-71e3-421f-a83e-5d754ce3dd92');
```

4. Создаем распределенную таблицу диалогов
```
SELECT create_distributed_table('socnet.dialogs', 'senderid');
```

5. Наполним её данными:
```
insert into socnet.dialogs(senderid, receiverid, text, datetime)
select
gen_random_uuid(),
gen_random_uuid(),
'text-1-' || generate_series,
TIMESTAMP with time zone '2023-01-01 00:00:00' + random() * (TIMESTAMP with time zone '2024-02-20 23:59:59' - TIMESTAMP with time zone '2023-01-01 00:00:00')
FROM generate_series(100,(100 + random() * 100000)::int);
```

6. Теперь можно получить диалоги между двумя пользоваетялми

curl --location 'http://localhost:8080/dialog/a50b3982-5ca6-409e-ad38-aeb783bf6e73/list' \
--header 'token: 7f701cb4-71e3-421f-a83e-5d754ce3dd92'

