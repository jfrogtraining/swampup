# swampup
Automation Training - Swampup Edition
=====================================

Url Links - substitute the IP address assigned from Orbitera. 
-----
```XML
[Artifactory Main HA] - http://35.185.192.7/artifactory
[Artifactory Primary] - http://35.185.192.7:8081/artifactory
[Aritfactory Secondary] - http://35.185.192.7:8082/artifactory
[Artifacatory DR] - http://35.185.192.7:8083/artifactory
[Artifactory Pro -1] - http://35.185.192.7:8084/artifactory
[Artifactory Pro -2] - http://35.185.192.7:8085/artifactory
[Jenkins] - http://35.185.192.7:8088/artifactory
[Xray] - http://35.185.192.7:8080/web
[jfmc] - http://35.185.192.7:8080
[Jenkins HTTP Proxy] - http://35.185.192.7:8086/artifactory
[automation-docker-virtual] - http://35.185.192.7:5002
[docker-virtual] - http://35.185.192.7:5001
[automation-docker-prod-local] - http://35.185.192.7:5003
[swampup scripts] - https://github.com/jfrogtraining/swampup
[project-example] - https://github.com/jfrogtraining/project-examples.git branch swampup201
[Artifactory User Plugins] - https://www.jfrog.com/confluence/display/RTF/User+Plugins
[Artifactory Public API] - for use with Artifactory User Plugin - http://repo.jfrog.org/artifactory/oss-releases-local/org/artifactory/artifactory-papi/%5BRELEASE%5D/artifactory-papi-%5BRELEASE%5D-javadoc.jar!/index.html
[Artifactory User Plugin] - https://github.com/JFrogDev/artifactory-user-plugins
```

API Documentation Links
-----------------------

```XML
[Artifactory REST API ] - https://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API also on Artifactory 5.x dashboard
[Retrieve LATEST Documentation ] - https://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API#ArtifactoryRESTAPI-RetrieveLatestArtifact
[JFrog CLI] - https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory
[FileSpec] - https://www.jfrog.com/confluence/display/CLI/CLI+for+JFrog+Artifactory#CLIforJFrogArtifactory-UsingFileSpecs
[Jenkins Aritfactory Plugin - DSL] - https://wiki.jenkins-ci.org/display/JENKINS/Artifactory+-+Working+With+the+Pipeline+Jenkins+Plugin
[Jenkins Artifactory Pipeline examples] - https://github.com/JFrogDev/project-examples/tree/master/jenkins-pipeline-examples 
[Aritfactory Query Language] - https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language
[Aritfactory Client Java] - https://github.com/JFrogDev/artifactory-client-java 
[Artifactory User Plugin] - https://www.jfrog.com/confluence/display/RTF/User+Plugins
[Artifactory Public API] - http://repo.jfrog.org/artifactory/oss-releases-local/org/artifactory/artifactory-papi/%5BRELEASE%5D/artifactory-papi-%5BRELEASE%5D-javadoc.jar!/index.html 
[Artifactory User Plugins on GitHub] - https://github.com/JFrogDev/artifactory-user-plugins
[Spock test framework for Artifactory User plugin] - http://spockframework.org/spock/docs/1.1/index.html 
```
Download Slide: Automation
---------------
Click on the below link - 
https://dl.bintray.com/jfrog/Training-Presentations/Advanced-Automation-With-JFrog-Artifactory.pdf?expiry=1496361600000&id=K8v%2BJBItDfdcU9%2BBa2lxho%2Fg%2FqmA%2F0CR8Tm5UYJ4YeuuqQ1NZiCIC9J6TIMtlTR3fZXLLXsklEZVJG2pjcWZlA%3D%3D&signature=faU6JLBsLILR%2BZr86O9MfFhwNMqQQtuzfAlD2bjDod5Qlk7rxi1eOss7f17ivA0Am0m9w4Zoc%2FZvVkSN4uvKtA%3D%3D


Download Slide: Advanced CI
---------------
Click the link below --
https://dl.bintray.com/jfrog/Training-Presentations/Advanced%20CI%20-%20Commit%20to%20deployment%20for%20docker_V2.pdf?expiry=1496361600000&id=K8v%2BJBItDfdcU9%2BBa2lxhh1LiT3lYxM1CXYoDHYbvIjEf%2BFfL5XXjp4U42osv%2BGeSr%2BFhsyA6LA2iq0xjbPIvw%3D%3D&signature=I%2BYTJHgxtX6oAbv094DEb1JwszGXmHeKqVC0aRKe4EsPfERQeFtK1idA0KnWYlnMqckfEXnBOBLsZLg%2FszXUZg%3D%3D

Excercise 1  - Set Up
---------------------
Introduction to Artifactory API and JFrog CLI.  The automation-docker-framework builds will fail without teh automation-docker-prod-local repository created;

- Clone github/jfrogtraining/swampup
- Create automation-docker-prod-local repostorty using artifactory API; hint: see swampup/docker-framework/local-repository-template; Use your API Keys.
- Manually configure to enable automation-docker-prod-local for XRay scan and configure XRay build and repository watch for automation-prod-local.
- Develop JFrog CLI file spec to download the following files to your Artifactory HA instance - repository - tomcat-local.  use tomcat-vritual repo in your FileSpec.  Info are:
-   Files to download and upload to Artifactory HA instance -
*     i. tomcat-local/java/jdk-8u91-linux-x64.tar.gz
*     ii. tomcat-local/org/apache/apache-tomcat/apache-tomcat-8.5.5.tar.gz
*     Source of these files - a.	http://jfrog.local:8084/artifactory; Repository: tomcat-local
- Verify your work by running - "jfrog rt s --server-id=artifactory-ha --spec framework-verify.json"
- Execute both the following builds
*   1. gradle-example
*   2. automation-docker-framework
*   3. automation-docker-app
- References
*   1. Jfrog rt c show
*   2. jfrog rt c swampup-automation --url=http://jfrog.local:8084/artifactory --user=admin --password=password
*   3. jfrog rt c artifactory-ha –url=://jfrog.local/artifactory –user=admin –password=password
*   4. jfrog rt dl --server-id=swampup-automation --spec framework-download.json
*   5. jfrog rt u --server-id=artifactory-ha --spec framework-upload.json

```XML
[Framework-download.json] - downloads JDK Java and Tomcat
[Framework-upload.json] - uploads JDK Java and Tomcat to Artifactory HA instance
[Framework-verify.json] - verify the upload is successful else the docker builds will fail.
[To include json file to curl command: Curl …] use -T <json file>
[catchup.sh] - runs all of excerise 1
```

Contents of this directory
--------------------------
The docker-framework has its own readme.md file.

maven-example
-------------
The following files in the maven-example folder

```XML
[jenkinsfile] - maven build enabling XRay Scan; property insertion, test cases results
```

gradle-example
--------------
The dev folder is not used at the moment.

```XML
[release/jenkinsfile] - gradle build; property insertion
```
