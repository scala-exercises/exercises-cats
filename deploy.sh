#!/bin/sh

echo $KEYS_PASSPHRASE | gpg --passphrase-fd 0 keys.tar.gpg
tar xfv keys.tar.gz
sbt publishSigned
