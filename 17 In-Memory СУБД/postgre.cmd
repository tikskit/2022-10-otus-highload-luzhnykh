docker run -d -p 5432:5432 -v "F:\OTUS\HighLoad\db\hw17\socnet:/var/lib/postgresql/data" -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=postgres -e POSTGRES_DB=soc_net postgres:16