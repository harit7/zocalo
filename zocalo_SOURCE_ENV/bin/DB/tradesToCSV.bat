@echo off

:     Copyright 2008 Chris Hibbert.  All rights reserved.
:    This file is published under the terms of the MIT license, a copy of
:    which has been included with this distribution in the LICENSE file.

if exist etc if exist jars goto rightDirs
   echo "    " Please run this command in the directory containing the 
   echo "    " etc and jars directories.
   goto end
:rightDirs

if exist etc\sqltool.rc goto foundTool
   echo "    " Could not find etc\sqltool.rc.  Something is wrong with
   echo "    " the installation.  Please try re-installing.
   goto end
:foundTool

set SQLFILE=

set sql="SELECT owner as trader, price, quantity, claims.name as claim, positions.name as pos, time, openingprice, closingprice from PUBLIC.TRADES, public.claims, public.positions where trades.pos = positions.positions_id and claims.claims_id = positions.claims_id; "

java -jar jars/hsqldb.jar --rcfile etc/sqltool.rc --sql %sql% zdb



