@echo off

set SQL_URL=jdbc:mysql://monorail.proxy.rlwy.net:54121/railway
set SQL_USER=root
set     SQL_PASSWORD=OVlKTqoHQsCrRbSOaJLVFoWiTtwXjkKb

java --add-opens java.base/java.lang.reflect=ALL-UNNAMED -jar BiblioManager.jar
pause