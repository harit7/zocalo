@echo off

:     Copyright 2008 Chris Hibbert.  All rights reserved.
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

java -cp %jarPath% -Djava.awt.headless=true -DPERSISTENT=on net.commerce.zocalo.hibernate.ShutdownDB 

:end
