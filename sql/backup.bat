set pgsql_path=C:\Program Files (x86)\PostgreSQL\8.3\bin
%pgsql_path%\pg_dump -i -h localhost -p 5432 -U PostgreSQL -F p -O -D -v -f acacia-%1.sql acacia