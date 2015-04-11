#! /bin/sh

#     Copyright 2008 Chris Hibbert.  All rights reserved.
#    This file is published under the terms of the MIT license, a copy of
#    which has been included with this distribution in the LICENSE file.

if [ ! -d etc -o ! -d bin -o ! -d jars ] 
then
   echo "    " Please run this command in the directory containing the bin, 
   echo "    " etc, and jars directories.
   exit 2
fi

if [ ! -f etc/zocalo.conf ] 
then
   echo "    " Could not find etc/zocalo.conf.  Something is wrong with
   echo "    " the installation.  Please try re-installing.
   exit 2
fi

 jarFiles=`find . -name "*.jar"`
 jarPath=`echo $jarFiles | sed -e 's/ /:/g'`

mkdir -p logs webpages/charts

java -cp $jarPath -Djava.awt.headless=true -DPERSISTENT=on net.commerce.zocalo.experiment.config.ConfigManager
