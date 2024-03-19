vacuum;
explain analyze select p.postid, p."text", p.authorid, p.postedat
from socnet.users u
inner join socnet.friends f on f.userid = u.userid 
inner join socnet.posts p on p.authorid = f.friendid
where u.userid = '45aaf307-cf29-468e-8d73-ed5b0aa48a1b'
order by p.postedat, p.postid
offset 0
limit 1000;


explain analyze select u.*
from socnet.users u
where u.userid = '90523d42-9551-4154-bafc-1e78b477a1b9'
offset 0
limit 10


select *
from socnet.users u;

select *
from socnet.accounts a;

/*

45aaf307-cf29-468e-8d73-ed5b0aa48a1b


7f55b35f-141e-4130-b05a-3089007e1960
32b87c76-c60e-45a8-987c-275be0f20637
1b507abf-918f-4549-b75b-61ce089a0e28
4865b682-734f-4a62-8ebf-15ef3daa9cc9
331a28c8-d574-4d97-b2f8-7dd635ce6860
e2d8e8c0-e93b-4889-8a53-b6d2f921ab4a
555a8834-c72b-41b5-abc3-b831edfe950b

*/

insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '7f55b35f-141e-4130-b05a-3089007e1960') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '32b87c76-c60e-45a8-987c-275be0f20637') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '1b507abf-918f-4549-b75b-61ce089a0e28') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '4865b682-734f-4a62-8ebf-15ef3daa9cc9') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '331a28c8-d574-4d97-b2f8-7dd635ce6860') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', 'e2d8e8c0-e93b-4889-8a53-b6d2f921ab4a') on conflict do nothing;
insert into socnet.friends(userid, friendid) values('45aaf307-cf29-468e-8d73-ed5b0aa48a1b', '555a8834-c72b-41b5-abc3-b831edfe950b') on conflict do nothing;

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '7f55b35f-141e-4130-b05a-3089007e1960', 'text-1-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '32b87c76-c60e-45a8-987c-275be0f20637', 'text-1-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '1b507abf-918f-4549-b75b-61ce089a0e28', 'text-2-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '4865b682-734f-4a62-8ebf-15ef3daa9cc9', 'text-3-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '331a28c8-d574-4d97-b2f8-7dd635ce6860', 'text-4-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), 'e2d8e8c0-e93b-4889-8a53-b6d2f921ab4a', 'text-5-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);

insert into socnet.posts(postid, authorid, "text", postedat)
select gen_random_uuid(), '555a8834-c72b-41b5-abc3-b831edfe950b', 'text-6-' || generate_series, timestamp '2023-01-01 00:00:00' + random() * (timestamp '2024-02-20 23:59:59' - timestamp '2023-01-01 00:00:00') FROM generate_series(100,(100 + random() * 100000)::int);


select count(*) from socnet.posts;