set pgsql_path=C:\Program Files (x86)\PostgreSQL\8.3\bin
%pgsql_path%\pg_dump -U PostgreSQL -ci -F t -f acacia-ddl-%1.tar acacia