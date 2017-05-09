package swampup

/**
 * Created by stanleyf on 25/04/2017.
 */
class Main {

    public static void main (def args) {

        def testApp = new testDockerApp()
        String testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/latestDockerApp.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.runTest()

        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/qaApprovalCriteria.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.sendApproval()

        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/latestDockerAppReleaseApproval.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.downloadToProduction()

        testLatestApp = "/Users/stanleyf/git/swampup/sample/src/swampup/listPropertiesOnDeployedDockerApp.aql"
        testApp.setAqlFile(testLatestApp)
        testApp.listPropertiesDeployedDockerApp()
    }

}
