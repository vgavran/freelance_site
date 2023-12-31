#!/usr/bin/env bash

gradle test --info -DincludeTags=$1
mv ./build/allure-results/* /allure/
