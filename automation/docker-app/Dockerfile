FROM jfrogtraining-docker-dev-local.jfrog.io/docker-framework:latest

MAINTAINER Stanley Fong stanleyf@jfrog.com

ADD war/*.war /home/exec/tomcat/webapps/swampup.war

CMD /bin/bash -c cd /home/exec; /home/exec/tomcat/bin/catalina.sh run
