#!/bin/bash

if [[ "$1" = '-s' ]];then
	cd /PBS & sbt run
elif [[  "$1" = '-t' ]];then
	cd /PBS & sbt test
else
	cd /PBS & sbt test
fi
