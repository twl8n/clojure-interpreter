#!/bin/bash
if [[ -e clojint.jar ]]
then
    echo "Using ./clojint.jar"
    java -jar clojint.jar $@
else
    echo "Using target/uberjar/clojint-standalone.jar"
    java -jar target/uberjar/clojint-standalone.jar $@
fi;


