#!/bin/bash
if [[ -e clojint.jar ]]
then
    echo "Using ./clojint.jar"
    java -jar clojint.jar $@
else
    echo "Using target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar"
    java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar $@
fi;


