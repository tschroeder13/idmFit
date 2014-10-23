#!/bin/sh
cd `dirname $0`
java -cp 'lib/dbfit-docs-3.1.0.jar:lib/fitnesse-standalone-20140903.jar' fitnesseMain.FitNesseMain $@
