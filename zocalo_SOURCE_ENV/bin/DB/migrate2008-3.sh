#! /bin/sh

#     Copyright 2008 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

SQLFILE=etc/sqltool.rc

createview='CREATE VIEW countSubsidy AS select claims.name, count(*) as c from public.claims, public.positions, public.marketmaker, public.market where positions.claims_id = claims.claims_id and marketmaker.market = market.markets_id and market.claim = claims.claims_id group by name; '

java -jar jars//hsqldb.jar --autoCommit --rcfile $SQLFILE --sql  "$createview" zdb 

countSql='select distinct marketmaker.marketmaker_id, marketmaker.subsidy, countsubsidy.c from public.claims, public.positions, public.marketmaker, public.market, countSubsidy where marketmaker.market = market.markets_id and market.claim = claims.claims_id and positions.claims_id = claims.claims_id and market.claim = claims.claims_id and countSubsidy.name = claims.name ;'

countlist=`java -jar jars/hsqldb.jar --rcfile $SQLFILE --sql  "$countSql" zdb | egrep -v rows\|SUBSIDY\|--`

declare -a counts
counts=($countlist)

words=`echo $countlist | wc -w`
lines=`expr $words / 3`
for (( i=0 ; i < $words ; )) ; do 
    id=${counts[i+0]}
    subsidy=${counts[i+1]}
    count=${counts[i+2]} 
  java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql "update MarketMaker set beta = ( $subsidy * log($count) ) where marketmaker_id=$id; commit;"  zdb
  i=`expr $i + 3`
    echo ; done

java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql  "drop view countSubsidy " zdb 
