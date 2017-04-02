name = userInput (
    type : "STRING",
    description : "Please provide a repository name"
  )

repoDescription = userInput (
    type : "STRING",
    description : "Please provide a public description"
  )

repo = userInput (
    type : "REPOSITORY",
    description : "Please provide repositories to aggregate ",
    multivalued : true
  )
DefaultRepo = userInput (
    type : "REPOSITORY",
    description : "Please provide Default deployment",
    multivalued : false
  )
virtualRepository(name) {
  description "$repoDescription"
  repositories (repo*.key) 
  notes "Created through JFrog Mission Control"
  defaultDeploymentRepo "$DefaultRepo.key"
  includesPattern "**/*" 
  excludesPattern "" 
  packageType "maven" 
}
