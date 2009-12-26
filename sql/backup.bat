@ECHO OFF
@rem echo %date%
set day=%Date:~4,2%
set month=%Date:~7,2%
set year=%Date:~10,4%
set ymd=%year%%month%%day%

@rem echo %time%
set hour=%time:~0,2%
set minute=%time:~3,2%
set second=%time:~6,2%
set hms=%hour%%minute%%second%

set ymd_hms=%ymd%-%hms%

set pgsql_path="C:\Program Files\PostgreSQL\8.3\bin"
set pgsql_host=localhost
set pgsql_port=5432
set username=postgres
set output_file_name="E:\Works.NB\Acacia\Acacia-Business-Ace\trunk\sql\acacia-ddl-%ymd_hms%.sql"
echo The output file on %date% at %time% will be:
echo %output_file_name%
pause

set db_schema=\"public\"
set db_name=acacia
set dump_cmd=%pgsql_path%\pg_dump
set options=-h %pgsql_host% -p %pgsql_port% -U %username%
set options=%options% -F p -s -s -O -C -v
set options=%options% -f %output_file_name%
set options=%options% -n %db_schema%
set options=%options% %db_name%
%dump_cmd% %options%