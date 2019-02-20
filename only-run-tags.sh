#!/usr/bin/env bash

if [[ $# -gt 0 && $1 =~ ^(@[a-zA-Z0-9\-]+)(,@[a-zA-Z0-9\-]+)*$ ]]; then

    [[ -z $2 ]] && optionalProfilesToActivate="default" || optionalProfilesToActivate="$2"

    mvn clean verify -Dcucumber.options="--tags $1" -Dspring.profiles.active=$optionalProfilesToActivate
else
    echo "    Missing argument or arguments have the wrong format"
    echo "    usage: ./only-run-tags.sh @TagName[,@AnotherTagName...] [profileToActivate[,anotherProfileToActivate...]]"
fi
