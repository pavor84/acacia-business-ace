set pgsql_path="C:\Program Files\PostgreSQL\8.3\bin"
set pgsql_host=localhost
set db_name=acacia
set username=postgres
set sql_script=acacia-ddl-2009-04-22.sql
set host=localhost
@rem set host=10.20.30.1

%pgsql_path%\psql -q -W -U %username% -h %host% -d %db_name% -f %sql_script%
