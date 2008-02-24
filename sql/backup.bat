set pgsql_path="C:\Program Files\pgAdmin III\1.8"
set pgsql_host=10.1.1.11
%pgsql_path%\pg_dump -i -h %pgsql_host% -p 5432 -U PostgreSQL -F p -O -D -v -f acacia-ddl-%1.sql acacia