#!/usr/bin/env bash
echo "Executing postconfigure.sh"

err="false"
if [ -z "$JDG_HOST" ];
then
    echo "JDG_HOST not set";
    err="true";
fi
if [ -z "$JDG_PORT" ];
then
    echo "JDG_PORT not set";
    err="true";
fi

$JBOSS_HOME/bin/jboss-cli.sh --file=$JBOSS_HOME/extensions/offload-session-cache-to-jdg.cli;

