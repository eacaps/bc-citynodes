#!/bin/bash

mvn install
java -cp target/bc-citynodes-1.0-SNAPSHOT.jar pub.eacaps.citynodes.Main $1
