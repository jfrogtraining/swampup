package swampup

import org.jfrog.artifactory.client.Artifactory
import org.jfrog.artifactory.client.ArtifactoryRequest
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl

/**
 * Created by stanleyf on 25/04/2017.
 */
class testDockerApp {
    def buildName
    def buildNumber
    def response
    Artifactory artifactory
    String aqlFile

    testDockerApp(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    public def setAqlFile (String aqlFile) {
        this.aqlFile = aqlFile
    }

    // run simulated tests and update the properties.
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


    public def downloadToProduction () {
        def testPropertyMap = [:]
        response = aqlQueryRequest()
        getBuildInfo()
        testPropertyMap.put("devops.deployed","true")
        testPropertyMap.put("devops.deploy","data-center-1")
        updateTestProperty(testPropertyMap)
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


    def listPropertiesDeployedDockerApp () {
        response = aqlQueryRequest()
        getBuildInfo()
        List properties = response.results[0].properties
        properties.each {it ->
            println (it.key + ":" + it.value)
        }
    }

    // verify no xray critical errors; the aql checks for other critiera.
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

    // place holder for tests.
    def static runAppTestSuite() {
        println "Docker Application started: http://localhost/swampup; Please run your tests"
        return true
    }

    // prints the build number that is tested - retrieving the latest tag - need to know the build number.
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

    // AQL request to artifactory
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
