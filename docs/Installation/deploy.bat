@ECHO OFF
set gfadmin=call D:\GlassFish\v2.1\bin\asadmin.bat
set gfdeploy_options=--user admin
set gfdeploy_options=%gfdeploy_options% --passwordfile "C:\Documents and Settings\user\.asadminpass"
set gfdeploy_options=%gfdeploy_options% --host localhost
set gfdeploy_options=%gfdeploy_options% --port 4849

set gf_command=add-resources
set filepath=D:\Acacia\sun-jdbc-resources.xml
%gfadmin% %gf_command% %gfdeploy_options% %filepath%

set filepath=D:\Acacia\sun-jms-resources.xml
%gfadmin% %gf_command% %gfdeploy_options% %filepath%

set gf_command=deploy
set filepath=D:\Acacia\AcaciaBusinessAce.ear
%gfadmin% %gf_command% %gfdeploy_options% %filepath%