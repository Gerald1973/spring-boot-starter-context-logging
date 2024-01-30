#!/bin/bash
mvn clean install
mvn install:install-file \
   -Dfile="./target/spring-boot-starter-context-logging-0.0.1-SNAPSHOT.jar" \
   -DgroupId="tech.marechal" \
   -DartifactId="spring-boot-starter-context-logging" \
   -Dversion="0.0.1-SNAPSHOT" \
   -Dpackaging="jar" \
   -DgeneratePom=true
