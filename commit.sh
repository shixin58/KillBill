#!/usr/bin/env bash

git status

git add --all

git commit -m "$1"

git fetch origin;

git rebase -i origin/develop;

git push origin develop;
