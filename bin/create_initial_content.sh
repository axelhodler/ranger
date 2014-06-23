#!/bin/sh

printf "Create initial users:"
USER_ID=$(curl -X POST -d '{"login":"xorrr"}' http://localhost:1337/users)
USER2_ID=$(curl -X POST -d '{"login":"pete"}' http://localhost:1337/users)

printf "Create initial medias:"
MEDIA_ID=$(curl -X POST -d '{"url":"www.foo.org"}' http://localhost:1337/media)
MEDIA2_ID=$(curl -X POST -d '{"url":"www.bar.org"}' http://localhost:1337/media)
MEDIA3_ID=$(curl -X POST -d '{"url":"www.baz.org"}' http://localhost:1337/media)

printf "Create range for medium: $MEDIA_ID and user: $USER_ID\n"
curl -X POST -d '{"startTime":50, "endTime":100}' -H "Content-Type: application/json" http://localhost:1337/ranges/$MEDIA_ID?userId=$USER_ID
printf "\n"

printf "Create range for medium: $MEDIA_ID and user: $USER2_ID\n"
curl -X POST -d '{"startTime": 100, "endTime":200}' -H "Content-Type: application/json" http://localhost:1337/ranges/$MEDIA_ID?userId=$USER2_ID
printf "\n"

printf "Modify range for medium: $MEDIA_ID and user: $USER_ID\n"
curl -X PUT -d '{"startTime":20, "endTime":80}' -H "Content-Type: application/json" http://localhost:1337/ranges/$MEDIA_ID?userId=$USER_ID
printf "\n"
