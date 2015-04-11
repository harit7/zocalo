ant build clean
ant build jsp-pm

rm -rf ../../zocalo_PRODUCTION_ENV/jars/
cp -r jars ../../zocalo_PRODUCTION_ENV/
cp jars/jsp.jar jars/zocalo.jar ../../zocalo_PRODUCTION_ENV/jars/

