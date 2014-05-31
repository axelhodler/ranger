#!/bin/sh

echo "Create user:"
USER_ID=$(curl -X POST -d '{"login":"xorrr"}' http://localhost:1337/users)

echo "Create media:"
MEDIA_ID=$(curl -X POST -d '{"url":"www.foo.org"}' http://localhost:1337/media)

echo "Add range to media:"
curl -X PUT -d '{"startTime":10, "endTime":20}' -H "user: $USER_ID" http://localhost:1337/media/$MEDIA_ID
