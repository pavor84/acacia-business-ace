set pgsql_path="E:\PostgreSQL\8.3\bin"
set pgsql_host=localhost
set db_name=acacia
@rem set sql_script=acacia-ddl-%1.sql
set sql_script=acacia-ddl-2008-07-29.sql
@rem %pgsql_path%\psql -q -U postgres -h %pgsql_host% -v c -d %db_name% -v %sql_script%
%pgsql_path%\psql -q -W -U postgres -h localhost -d acacia -f acacia-ddl-2008-07-29.sql
@rem %pgsql_path%\psql -q -W -U postgres -h 10.20.30.1 -d acacia -f acacia-ddl-2008-07-29.sql
