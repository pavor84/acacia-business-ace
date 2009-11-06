@ECHO OFF
set gfadmin=call D:\GlassFish\v2.1\bin\asadmin.bat
set gf_command=stop-appserv
set gfdeploy_options=
set filepath=
%gfadmin% %gf_command% %gfdeploy_options% %filepath%