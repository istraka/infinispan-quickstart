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

sed -i "s/<resolve-parameter-values>false<\/resolve-parameter-values>/<resolve-parameter-values>true<\/resolve-parameter-values>/" $JBOSS_HOME/bin/jboss-cli.xml
$JBOSS_HOME/bin/jboss-cli.sh --file=$JBOSS_HOME/extensions/offload-session-cache-to-jdg.cli;

