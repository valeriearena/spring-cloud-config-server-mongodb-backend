#!/bin/bash
read -p "[start] [stop]: "  command

if [ "${command}" == "start" ]
then
  cd $INTELLIJ_PROJECT_PATH/docker-compose/docker-compose-prerequisites
  docker-compose up -d
  sleep 5
  exit
fi

if [ "${command}" == "stop" ]
then
  cd $INTELLIJ_PROJECT_PATH/docker-compose/docker-compose-prerequisites
  docker-compose down
  exit
fi