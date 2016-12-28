#!/bin/bash
set -ev

mkdir scalaex
cd scalaex
git init
git pull https://$GH_TOKEN@github.com/scala-exercises/exercises-cats.git
git checkout testing-redeploying-scala-exercises-server
git commit --allow-empty -m "exercises-cats update, auto-shipping"
git push https://$GH_TOKEN@github.com/scala-exercises/exercises-cats.git testing-redeploying-scala-exercises-server

