package swampup

import org.jfrog.artifactory.client.*
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl

/**
 * Created by stanleyf on 25/04/2017.
 */
class testDockerApp {
    def buildName
    def buildNumber
    def response
    def artifactoryUrl = 'http://jfrog.local/artifactory/'
    String aqlFile
    Artifactory artifactory = ArtifactoryClient.create(artifactoryUrl, "admin", "password")

    public def setAqlFile (String aqlFile) {
        this.aqlFile = aqlFile
    }

    public def downloadToProduction () {
        def testPropertyMap = [:]
        response = aqlQueryRequest()
        getBuildInfo()
        testPropertyMap.put("devops.deployed","true")
        testPropertyMap.put("devops.deploy","data-center-1")
    }

    public def runTest () {
        def testPropertyMap = [:]
        response = aqlQueryRequest()
        getBuildInfo()
        if (runAppTestSuite()) {
            testPropertyMap.put ("qa.manual-test","pass")
            testPropertyMap.put("qa.test-team","platform")
        } else {
            testPropertyMap.put ("qa.manual-test","fail")
            testPropertyMap.put ("qa.test-team","platform")
            testPropertyMap.put ("qa.jira-tickets","jira-123")
        }
        updateTestProperty(testPropertyMap)
        println "Test Complete"
    }

    public def sendApproval () {
        def testPropertyMap = [:]
        response = aqlQueryRequest()
        if (checkApprovalCriteria()) {
            testPropertyMap.put ("qa.release-approved","yes")
            testPropertyMap.put ("qa.approver","swampup-qa")
            updateTestProperty(testPropertyMap)
            println "Release Approved"
        } else {
            testPropertyMap.put ("qa.release-approved","no")
            updateTestProperty(testPropertyMap)
            println "Release NOT Approved"
        }
    }

    def updateTestProperty (def properties) {
        properties.each {it ->
            artifactory.repository("automation-docker-prod-local")
                .folder("docker-app/${buildNumber}")
                .properties()
                .addProperty(it.key as String, it.value as String)
                .doSet(true);

            artifactory.repository("automation-docker-prod-local")
                    .folder("docker-app/latest")
                    .properties()
                    .addProperty(it.key as String, it.value as String)
                    .doSet(true);
        }
    }

    def static runAppTestSuite() {
        println "Docker Application started: http://localhost/swampup; Please run your tests"
        return true
    }

    def listPropertiesDeployedDockerApp () {
        response = aqlQueryRequest()
        getBuildInfo()
        List properties = response.results[0].properties
        properties.each {it ->
            println (it.key + ":" + it.value)
        }
    }

    def checkApprovalCriteria () {
        List properties = response.results[0].properties
        def approved = true
        properties.each { it ->
            switch (it.key) {
                case "build.name" :
                    buildName = it.value
                    println "Jenkins Build Name: " + it.value
                    break
                case "build.number" :
                    buildNumber = it.value
                    println "Jenkins Build Number: " + it.value
                    break
                case "xray*.alert.topSeverity" :
                    if (it.value == "Critical") {
                        approved = false
                    }
                    break
            }
        }
        return approved
    }

    def getBuildInfo () {
        List properties = response.results[0].properties
        properties.each { it ->
            switch (it.key) {
                case "build.name" :
                    buildName = it.value
                    println "Jenkins Build Name: " + it.value
                    break
                case "build.number" :
                    buildNumber = it.value
                    println "Jenkins Build Number: " + it.value
                    break
            }
        }
    }

    def aqlQueryRequest () {
        def aqlQuery = new File (aqlFile).text
        ArtifactoryRequest aqlRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/search/aql")
                .method(ArtifactoryRequest.Method.POST)
                .requestType(ArtifactoryRequest.ContentType.TEXT)
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .requestBody(aqlQuery);
        return artifactory.restCall(aqlRequest)
    }
}
