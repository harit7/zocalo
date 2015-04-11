#!/usr/bin/env python

#     Copyright 2008 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

import subprocess

SQLFILE="etc/sqltool.rc"

countSql='''select claims.claims_id, count(*) as posCount
            from claims, positions
            where positions.claims_id = claims.claims_id
            group by claims_id ; '''

child = subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--rcfile", SQLFILE, "--sql", countSql, "zdb"],
                         stdout=subprocess.PIPE)
lines = child.stdout.readlines()

for line in lines:
    if line.count( "rows") == 0 and line.count( "POSCOUNT") == 0 \
              and line.count( "--") == 0  :
       line = line.strip().split()
       if  len(line) == 2 :
          id=line[0]
          count=int(line[1])
          queryString= '''update positions set idx = mod(positions_id, %(count)s) 
                        where claims_id=%(id)s ; commit;''' %  \
                { "count" : count, "id" : id }
          subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit", "--rcfile", 
           SQLFILE, "--sql", queryString, "zdb"])


createview='''CREATE VIEW maxTrades AS
                        select positions.claims_id as claims, max(trades.time) as lastDate
                        from public.Trades, public.positions
                           where trades.pos = positions.positions_id
                        group by positions.claims_id'''

subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit", "--rcfile", SQLFILE, "--sql", createview, "zdb"])

updateLastTrades='''update market
                set market.lastTrade =
                           (select maxTrades.lastDate from maxTrades
                    where market.claim = maxTrades.claims);'''

subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit",
                  "--rcfile", SQLFILE, "--sql", updateLastTrades, "zdb"])

subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit",
                  "--rcfile", SQLFILE, "--sql", '''drop view maxTrades;''', "zdb"])

updateBookTypes='''update book set book.book_Type = 'Binary' where book_Type is null;'''

subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit",
                  "--rcfile", SQLFILE, "--sql", updateBookTypes, "zdb"])

