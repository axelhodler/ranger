#!/bin/sh

export PORT=1337
export MONGO_PORT=12345

mvn integration-test
