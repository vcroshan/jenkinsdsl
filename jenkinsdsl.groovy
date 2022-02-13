freeStyleJob('simple-maven-app-freestyle'){
    description ('Job created using Jobdsl plugin. This is a freestyle job')
    parameters{
        stringParam('Inputparam1',defaultValue ='hello',description = '')
        stringParam('Inputparam2',defaultValue ='world',description = '')
    }
    scm {
        git {
            branch('master')
            remote {
                credentials ('2d64d980-832f-4dd0-b22d-b5cb971e0a7a')
                github('vcroshan/simple-java-maven-app', protocol = 'https', host = 'github.com')

            }
        }
    }
    steps {
        shell('echo $WORKSPACE;echo $BUILD_ID;echo $JOB_NAME;echo $JENKINS_HOME;echo $Inputparam1 $Inputparam2')
        maven {
            mavenInstallation('maven')
            goals('clean package')
            properties(skipTests: true)
        }
         maven {
            mavenInstallation('maven')
            goals('test')
            }
        maven {
            mavenInstallation('maven')
            goals('-gs settings.xml deploy')
            }
    }   
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/TEST-com.mycompany.app.AppTest.xml') {
             allowEmptyResults()
            retainLongStdout()
            healthScaleFactor(1.0)
            testDataPublishers {
            publishTestStabilityData()
            }
        }
    }     
}

pipelineJob('simple-maven-app-pipeline') {
    parameters{
        stringParam('Inputparam1',defaultValue ='hello',description = '')
        stringParam('Inputparam2',defaultValue ='world',description = '')
    }
    
    definition{
        cpsScm {
            scm {
                git {
                    branch('master')
                    remote {
                        credentials ('2d64d980-832f-4dd0-b22d-b5cb971e0a7a')
                        github('vcroshan/simple-java-maven-app', protocol = 'https', host = 'github.com')
                    }
               }
            }
            lightweight(lightweight = true)
            scriptPath('Jenkinsfile')
        }
    }
}
