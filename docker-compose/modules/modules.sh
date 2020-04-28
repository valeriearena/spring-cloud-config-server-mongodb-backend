#!/bin/bash
read -p "[start] [stop] [update]: "  command

if [ "${command}" == "start" ]
then
  cd $INTELLIJ_PROJECT_PATH/docker-compose/docker-compose-prerequisites
  docker-compose up -d
  sleep 5

  cd $INTELLIJ_PROJECT_PATH/docker-compose/modules/
  docker-compose up -d
  exit
fi

if [ "${command}" == "stop" ]
then
  cd $INTELLIJ_PROJECT_PATH/docker-compose/docker-compose-prerequisites
  docker-compose down

  cd $INTELLIJ_PROJECT_PATH/docker-compose/modules/
  docker-compose down
  exit
fi

if [ "${command}" == "update" ]
then
  cd $INTELLIJ_PROJECT_PATH/docker-compose/modules/
  docker-compose up -d
  exit
fi