#!/bin/bash

set -f

server=$DEPLOY_SERVER
user=$DEPLOY_USER
branch=$DEPLOY_BRANCH
gittoken=$DEPLOY_GITLAB_TOKEN
gituser=$DEPLOY_GITLAB_USER

echo "Deploying project on server ${server} as ${user} from branch ${branch}"

apt-get update && apt-get install -y openssh-client

## Rolling update
command="ls -ltr && \
 cd /home/devuser/kurator && \
 git pull origin ${branch} && \
 docker-compose up --build -d --remove-orphans && \
 echo 'DONE DEPLOYING'"

## Complete build
# command="ls -ltr && \
#  mkdir -p /home/devuser/kurator && \
#  cd /home/devuser/kurator && \
#  docker-compose -f docker-compose.yml down && \
#  cd /home/devuser && \
#  rm -rf /home/devuser/kurator && \
#  git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/kurator-itc-wave-02/kurator.git -b ${branch} && \
#  cd /home/devuser/kurator && \
#  echo 'Deploying the Kurator Application' && \
#  docker-compose up --build -d --remove-orphans && \
#  echo 'DONE DEPLOYING'"

# command="ls -ltr && \
#  git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/kurator-itc-wave-02/kurator.git -b ${branch} && \
#  cd /home/devuser/kurator/ && \
#  echo 'Deploying the Application' && \
#  docker-compose up --build -d --remove-orphans && \
#  echo 'DONE DEPLOYING'"

echo "About to run the command: " $command

ssh $user@$server $command
