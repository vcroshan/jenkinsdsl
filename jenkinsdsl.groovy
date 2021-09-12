job('simple-maven-app') {
    scm {
        git { 
            branch('master')
            remote { 
                credentials('MyGithubaccount')
                github('vcroshan/simple-java-maven-app', protocol = 'https', host ='github.com')
                }
        }
    }
    steps {
        shell{
            command('echo "WORKSPACE: ${WORKSPACE}";echo "Jenkins Job Name: ${JOB_NAME}"')
        }
        maven{
            goals('clean package')
            mavenInstallation('Maven3.8')
            properties(skipTests: true)
        } 
        maven{
            goals('test')
            mavenInstallation('Maven3.8')
        } 
        maven{
            goals('-gs settings.xml deploy')
            mavenInstallation('Maven3.8')
        } 
     }
     publishers{
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/**/*.xml')

     }
}

pipelineJob('simple-maven-app-pipeline') {
    description ('This job is a pipelinejob')

    definition{
        cpsScm{
                scm {
                    git {
                        branch('master')
                        remote { 
                            credentials('MyGithubaccount')
                            github('vcroshan/simple-java-maven-app', protocol = 'https', host ='github.com')
                        }
                    }
                }
                scriptPath('Jenkinsfile')
                lightweight(lightweight = true)
            }

    }
}
