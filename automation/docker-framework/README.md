Automation Training - Docker Framework
======================================
This folder contains scripts used for the Automation SwampUp Training exercises.

Script Details
--------------
The following files in the tomcat folder

```XML
[framework-download.json] fileSpec - download java jdk and tomcat for docker-framework build. Used with JFrogCli
[framework-upload.json] fileSpec - upload java jdk and tomcat to artifactory with properties.  Used with JFrogCli
[framework-verify.json] fileSpec - verify the jdk and tomcat are uploaded correctly.  Used with JFrogCli
[framework-download-template] - fileSpec skeleton used to create fileSpec for exercise 1.
```

The following flies are in the docker-framework folder
```XML
[local-repository-template] json file to create local repository
[automation-framework-prod-local.json] - json file to create repo needed for exercise 1.
```

Jenkins Details
---------------
The following files are used for Docker-Framework build

```XML
[Dockerfile] Put the apache tomcat, Java jdk and Ubuntu in docker container
[Jenkinsfile] pipeline code for docker-framework build
[retag.json] Tag the latest build with LATEST tag.
[framework-download.json] fileSpec to download java jdk and tomcat based on properties
[gradeWar-download.json] fileSpect to download latest war file from gradle build
```
