#! /bin/sh

#     Copyright 2008 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

SQLFILE=etc/sqltool.rc

countSql='select claims.claims_id, count(*) as "count" FROM claims, positions WHERE positions.claims_id = claims.claims_id group by claims_id ; '

countlist=`java -jar jars/hsqldb.jar --rcfile $SQLFILE --sql  "$countSql" zdb | egrep -v rows\|count\|--`

declare -a counts
counts=($countlist)

words=`echo $countlist | wc -w`
lines=`expr $words / 2`
for (( i=0 ; i < $words ; )) ; do 
    id=${counts[i+0]}
    count=${counts[i+1]} 
   java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql "update positions set positions.idx = mod(positions.positions_id, $count) where positions.claims_id=$id;"  zdb
 i=`expr $i + 2`
     echo ; done


createview='CREATE VIEW maxTrades AS select positions.claims_id as claims, max(trades.time) as lastDate from public.Trades, public.positions where trades.pos = positions.positions_id group by positions.claims_id'

java -jar jars//hsqldb.jar --autoCommit --rcfile $SQLFILE --sql  "$createview" zdb 

updateLastTrades='update market set market.lastTrade = (select maxTrades.lastDate from maxTrades where market.claim = maxTrades.claims);'

java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql "$updateLastTrades"  zdb

java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql  "drop view maxTrades " zdb 

updateBookTypes="update book set book.book_Type = 'Binary' where book_Type is null;"

java -jar jars/hsqldb.jar --autoCommit --rcfile $SQLFILE --sql "$updateBookTypes"  zdb
