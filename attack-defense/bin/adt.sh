#!/bin/bash

ROOT=`dirname $0`/..

# not yet completed 
# -- gather jars from an installation directory structure, 
# -- instead of the dev/build directory as below

LOCAL_CP=`ls $ROOT/build/jar/*.jar`

for JAR in $ROOT/lib/*.jar; do
        LOCAL_CP="$LOCAL_CP:$JAR"
done

# avoid * arguments expansion, if any
set -f 

# run
java -cp $LOCAL_CP main.Main $*

