#!/usr/bin/env python

import sys
import re

write = sys.stdout.write
word = sys.argv[1]
pattern=re.compile(word + '[:=] *(.*)')
f = open("etc\zocalo.conf")

for line in f:
   result=pattern.match(line.strip())
   if not result is None:
	   write(result.expand(r"set " + word + "=\\1").strip())
	   exit


