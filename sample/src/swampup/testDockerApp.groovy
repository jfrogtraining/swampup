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

    public def runTest () {
        def testPropertyMap = [:]
        response = aqlQueryRequest()
        getBuildInfo()
        if (runAppTestSuite()) {
            testPropertyMap.put ("manual-test","pass")
            testPropertyMap.put("test-team","platform")
        } else {
            testPropertyMap.put ("manual-test","fail")
            testPropertyMap.put ("test-team","platform")
            testPropertyMap.put ("jira-tickets","jira-123")
        }
        updateTestProperty(testPropertyMap)
        println "Test Complete"
    }


    def updateTestProperty (def properties) {
        properties.each {it ->
            artifactory.repository("automation-docker-prod-local")
                .folder("docker-app/${buildNumber}")
                .properties()
                .addProperty(it.key as String, it.value as String)
                .doSet(true)
        }
    }

    def static runAppTestSuite() {
        println "Docker Application started: http://localhost/swampup; Please run your tests"
        return true
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
