@echo off

:     Copyright 2008 Chris Hibbert.  All rights reserved.
:    This file is published under the terms of the MIT license, a copy of
:    which has been included with this distribution in the LICENSE file.

if exist etc if exist jars goto rightDirs
   echo "    " Please run this command in the directory containing the 
   echo "    " etc and jars directories.
   goto end
:rightDirs

if exist etc\zocalo.conf goto foundConfig
   echo "    " Could not find etc\zocalo.conf.  Something is wrong with
   echo "    " the installation.  Please try re-installing.
   goto end
:foundConfig

set jarPath=.

for /f "tokens=*" %%f in ('dir /b jars\*.jar') do (call :appendJarPath %%f)
goto :doneBuildingPath

:appendJarPath
  set jarPath=%jarPath%;jars\%1
goto :eof

:doneBuildingPath

if not exist logs            mkdir logs
if not exist webpages        mkdir webpage
if not exist webpages\charts mkdir webpages\charts

start "Command Prompt" java -cp %jarPath% -Djava.awt.headless=true -DPERSISTENT=on net.commerce.zocalo.service.AllMarkets 
