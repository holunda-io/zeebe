#!/bin/sh -eux


export JAVA_TOOL_OPTIONS="$JAVA_TOOL_OPTIONS -XX:MaxRAMFraction=$((LIMITS_CPU))"

su jenkins -c "mvn -v"

su jenkins -c "mvn -o -B -T$LIMITS_CPU -s .ci/settings.xml verify -pl clients/java -DtestMavenId=3"
