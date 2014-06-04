#!/bin/sh

export PORT=1337
export MONGO_PORT=27017

mvn exec:java -Dexec.mainClass="xorrr.github.io.Main"
