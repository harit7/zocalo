@echo off

:     Copyright 2009 Chris Hibbert.  All rights reserved.
:    This file is published under the terms of the MIT license, a copy of
:    which has been included with this distribution in the LICENSE file.

if exist jars goto rightDirs
   echo "    " Please run this command in the directory containing the jars directory.
   goto end
:rightDirs

set jarPath=

for /f "tokens=*" %%f in ('dir /b jars\*.jar') do (call :appendJarPath %%f)
goto :doneBuildingPath

:appendJarPath
  set jarPath=%jarPath%;jars\%1
goto :eof

:doneBuildingPath

set create="update"
if exist ..\P-data  goto dbExists
   mkdir ..\P-data
   set create="create"
:dbExists

java -cp jars\hsqldb.jar org.hsqldb.Server -database.0 ..\P-data\zocalo -dbname.0 zdb

java -Dhibernate.connection.url=jdbc:hsqldb:zdb -Dhibernate.hbm2ddl.auto=%create% -cp %jarPath%:jars\hibernate3.jar org.hibernate.tool.hbm2ddl.SchemaExport jars\hibernate.jar

:end
