#!/bin/bash

cd /usr/app/hsqldb/TP/
java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:mydb --dbname.0 tp


