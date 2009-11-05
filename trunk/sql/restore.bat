set pgsql_path="C:\Program Files\PostgreSQL\8.3\bin"
set pgsql_cmd=%pgsql_path%\psql
set pgsql_host=localhost
set db_name=acacia
set PGUSER=postgres
set PGPASSWORD=postgres
set host=localhost
@rem set host=10.20.30.1
set drop_db="DROP DATABASE %db_name%"
set create_db="CREATE DATABASE %db_name% WITH OWNER = postgres ENCODING = 'UTF8'"
@rem set common_options=-q -U %PGUSER% -h %host%
set common_options=-U %PGUSER% -h %host%

%pgsql_cmd% %common_options% -c %drop_db%
%pgsql_cmd% %common_options% -c %create_db%
%pgsql_cmd% %common_options% -d %db_name% -f %1