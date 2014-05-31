#!/bin/sh

printf "Create initial users:"
USER_ID=$(curl -X POST -d '{"login":"xorrr"}' http://localhost:1337/users)
USER2_ID=$(curl -X POST -d '{"login":"pete"}' http://localhost:1337/users)

printf "Create initial medias:"
MEDIA_ID=$(curl -X POST -d '{"url":"www.foo.org"}' http://localhost:1337/media)
MEDIA2_ID=$(curl -X POST -d '{"url":"www.bar.org"}' http://localhost:1337/media)
MEDIA3_ID=$(curl -X POST -d '{"url":"www.baz.org"}' http://localhost:1337/media)

printf "Add range to media: $MEDIA_ID\n"
printf "Range added to media: "
curl -X PUT -d '{"startTime":10, "endTime":20}' -H "user: $USER_ID" http://localhost:1337/media/$MEDIA_ID
printf "\n"

printf "Add range to media: $MEDIA_ID\n"
printf "Range added to media: "
curl -X PUT -d '{"startTime":20, "endTime":30}' -H "user: $USER2_ID" http://localhost:1337/media/$MEDIA_ID
printf "\n"

printf "Add range to media: $MEDIA2_ID\n"
printf "Range added to media: "
curl -X PUT -d '{"startTime":1, "endTime":5}' -H "user: $USER2_ID" http://localhost:1337/media/$MEDIA2_ID
printf "\n"
