package swampup

import org.jfrog.artifactory.client.Artifactory
import org.jfrog.artifactory.client.ArtifactoryClient

/**
 * Created by stanleyf on 10/05/2017.
 */
class DemoSwampUpTraining extends GroovyTestCase {
    def artifactoryUrl = 'http://jfrog.local/artifactory/'
    Artifactory artifactory = ArtifactoryClient.create(artifactoryUrl, "admin", "password")

    def testApp = new testDockerApp (artifactory)
    String testLatestApp

    void setUp() {
        super.setUp()
    }

    void test1_testLatestDockerApp () {
        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/latestDockerApp.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.runTest()
    }

    void test2_giveApprovalForRelease () {
        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/qaApprovalCriteria.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.sendApproval()
    }

    void test3_devOpsDeployRelease () {
        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/latestDockerAppReleaseApproval.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.downloadToProduction()
    }

    void test4_whereDeployed () {
        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/listPropertiesOnDeployedDockerApp.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.listPropertiesDeployedDockerApp()
    }
}
