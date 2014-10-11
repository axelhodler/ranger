#!/bin/sh

export PORT=1337
export MONGO_PORT=27017

java -jar target/ranger-1.0-SNAPSHOT.jar server config.yml
