#!/usr/bin/env sh
set -e
cd "$(dirname "$0")"
mkdir -p bin
javac -cp biuoop-1.4.jar -d bin src/*.java
java -cp "bin:biuoop-1.4.jar" Ass6Game "$@"
