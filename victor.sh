#!/usr/bin/env bash
git fetch origin;
git rebase -i origin/develop;
git push origin develop;
