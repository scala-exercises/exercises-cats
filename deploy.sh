#!/bin/sh

function decipherKeys{
   echo $KEYS_PASSPHRASE | gpg --passphrase-fd 0 keys.tar.gpg
   tar xfv keys.tar.gz
}

function publish{
   sbt compile publishSigned
}

function release {
    decipherKeys
    publish
}

if [[ $TRAVIS_BRANCH == 'master' ]]
   echo "Master branch, releasing..."
   release
else
    echo "Not in master branch, skipping release"
    # fixme: adding it here for testing in a non-master branch
    release
fi

