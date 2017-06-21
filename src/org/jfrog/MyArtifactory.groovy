package org.jfrog; 

class MyArtifactory implements Serializable {

    def server
    def buildInfo
    def rtMaven

    MyArtifactory (artifactory, bNumber) {
        this.server = artifactory.server 'artifactory-ha'
        this.rtMaven = artifactory.newMavenBuild()
        this.buildInfo = artifactory.newBuildInfo()
        this.buildInfo.env.capture = true
        this.buildInfo.retention maxBuilds: 10, maxDays: 7, deleteBuildArtifacts: 5
    
        this.rtMaven.tool = 'mvn' // Tool name from Jenkins configuration
        this.rtMaven.deployer releaseRepo:'automation-mvn-excercise-local', snapshotRepo:'automation-mvn-excercise-snapshot-local', server: server
        this.rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
    }

    def runMaven () {
        this.rtMaven.run pom: 'maven-example/pom.xml', goals: 'clean install', buildInfo: this.buildInfo
    }

    def upLoadToArtifactory () {
        def uploadSpec = """{
            "files": [
               {
                    "pattern": "/var/lib/jenkins/workspace/HAP-935/(*).zip",
                    "target": "hap-935a/tibco/bNumber/{1}.zip",
                    "props": "hap-935=true",
                    "flat": "false",
                    "regexp":"false"
                },
                {
                    "pattern" : "(*)pom.xml",
                    "target":"hap-935a/tibco/bNumber/{1}.xml",
                    "flat":"false",
                    "props":"type=pom;status=ready"
                }
            ]
        }"""

        this.server.upload(uploadSpec, buildInfo)
        this.server.publishBuildInfo(buildInfo)   
    }
}
