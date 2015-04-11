#! /bin/sh

jarFiles=`find . -name "*.jar"`
jarPath=`echo $jarFiles | sed -e 's/ /:/g'`

mkdir -p logs charts

java -cp $jarPath net.commerce.zocalo.experiment.ExperimentServer
