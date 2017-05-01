# swampup
Automation Training - Swampup Edition
=====================================

Url Links
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
[Aritfactory Query Language] - https://www.jfrog.com/confluence/display/RTF/Artifactory+Query+Language
```

Metadata Used
-------------
List of metadata used and what are the actionable events
```XML
[Security team deploy approved Java JDK and Tomcat: security-approval, approver] - security approvals for downloded packaes.
[Jenkins(gradle-examle build): unit-test, qa-team] - target qa team to perform tests; QA takes build if unit-test passes
[Jenkins(docker-framework-build): functional-test] - automated functional test pass, QA team to start testing
[DockerApp on Automation-docker-prod-local]: qa.manual-test, qa.test-team, qa.jira-tickets] - qa team tesing status and Jira tickets for failed test cases.
[DockerApp Approval for release: qa.release-approved, qa.approver] - approval for devops to deploy to production
[Docker App deployment] - DevOps list datacenters where the Docker App has been deployed.
[XRray - xray metadata] - xRay scan results on deployed artifacts from Jenkins to Artifactory
```

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
*   Jfrog rt c show
*   jfrog rt c swampup-automation --url=http://jfrog.local:8084/artifactory --user=admin --password=password
*   jfrog rt c artifactory-ha –url=://jfrog.local/artifactory –user=admin –password=password
*   jfrog rt dl --server-id=swampup-automation --spec framework-download.json
*   jfrog rt u --server-id=artifactory-ha --spec framework-upload.json

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
