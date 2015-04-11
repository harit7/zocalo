#!/usr/bin/env python

#     Copyright 2008 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.
import subprocess

SQLFILE="etc/sqltool.rc"

creatview='''CREATE VIEW countSubsidy AS select claims.name, count(*) as c
          from public.claims, public.positions, public.marketmaker, public.market
         where positions.claims_id = claims.claims_id
                and marketmaker.market = market.markets_id
                and market.claim = claims.claims_id
            group by name; '''

wait=subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit", "--rcfile",
                  SQLFILE, "--sql", creatview, "zdb"])

countSql='''select distinct marketmaker.marketmaker_id, marketmaker.subsidy, countsubsidy.c
         from public.claims, public.positions, public.marketmaker, public.market, countSubsidy
         where marketmaker.market = market.markets_id
               and market.claim = claims.claims_id
               and positions.claims_id = claims.claims_id
               and market.claim = claims.claims_id
               and countSubsidy.name = claims.name ;'''

child = subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--rcfile", SQLFILE, "--sql", countSql, "zdb"],
                         stdout=subprocess.PIPE)
lines = child.stdout.readlines()

for line in lines:
    if line.count( "rows") == 0 and line.count( "SUBSIDY") == 0 \
              and line.count( "--") == 0  :
       line = line.strip().split()
       if  len(line) == 3 :
          id=line[0]
          subsidy=float(line[1])
          count=int(line[2])
          queryString= '''update MarketMaker set beta = ( %(subsidy)s * log(%(count)s) )
                        where marketmaker_id=%(id)s ; commit;''' %  \
                { "subsidy" : subsidy, "count" : count, "id" : id }
          subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit", "--rcfile", 
           SQLFILE, "--sql", queryString, "zdb"])

subprocess.Popen(['java', "-jar", "jars/hsqldb.jar", "--autoCommit", "--rcfile", SQLFILE, "--sql",
                  "drop view countSubsidy ", "zdb"])
