#!/bin/bash

RED='\033[0;41;30m'
STD='\033[0;0;39m'
# change per student
APIKEY='AKCp2WWshJKjZjguhB3vD2u3RMwHA7gmxWUohWVhs1FqacHBAzKaiL2pp24NNUEhWHm5Dd4JY'

exercise1() {
   echo "performing exercise 1"
   cd $HOME/swampup/automation/docker-framework
# make sure student change /etc/hosts on the VM with jfrog.local and the IP address from Orbitera. 
   curl -H "X-JFrog-Art-Api:${APIKEY}" -H "Content-Type:application/vnd.org.jfrog.artifactory.repositories.LocalRepositoryConfiguration+json" -X PUT "http://jfrog.local/artifactory/api/repositories/automation-gradle-release-local" -T automation-gradle-release-local.json
   cd tomcat
   jfrog rt c clear
# Change password per student
   jfrog rt c swampup-automation --url=http://jfrog.local:8084/artifactory --user=admin --password=password
   jfrog rt c artifactory-ha --url=http://jfrog.local/artifactory --user=admin --password=password
   jfrog rt dl --server-id=swampup-automation --spec framework-download.json
   jfrog rt u --server-id=artifactory-ha --spec framework-upload.json
   jfrog rt s --server-id=artifactory-ha --spec framework-verify.json
   cd $HOME/swampup/automation
   echo "Should see both java jdk and apache-tomcat dependencies returned"
   echo "exercise 1 completed"
}

read_options(){
        local choice
        read -p "Enter choice [ 1 - 4] " choice
        case $choice in
                1) exercise1 ;;
                2) exit 0 ;;
                *) echo -e "${RED}Error...${STD}" && sleep 2
        esac
}

# function to display menus
show_menus() {
        echo "~~~~~~~~~~~~~~~~~~~~~~~~~~"
        echo " Automation Catch Up Menu "
        echo "~~~~~~~~~~~~~~~~~~~~~~~~~~"
        echo "1. Excercise 1"
        echo "2. Exit"
}

while true
do
        show_menus
        read_options
done
