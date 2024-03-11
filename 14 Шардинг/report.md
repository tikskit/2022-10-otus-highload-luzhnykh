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
им пишут многие, но отвечают они как обычные пользователи или меньше. Таким образом это не приведет перекосу данных
на какому-то одном шарде, а все сообщения будут размазаны по всем шардам равномерно.

Недостатком такого подхода является то, что диалоги придется склеивать в приложении, выполняя сортировку сообщений в памяти.
Кроме того, он не позволяет на уровне запроса выполнять пагинацию сообщений, а такая
функция точно нужна. Поэтому придется реализовывать пагинацию тоже в коде приложения, а не в запросах.
Эти операции увеличат нагрузку на CPU и память. При необходимости можно увеличивать количество инстансов приложения.
