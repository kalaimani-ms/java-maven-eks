@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
       stages {
        stage("init") {
            steps {
                script {
                    gv = load 'multiscript.groovy'
                }
                
            }
        }
        stage("test") {
            steps {
                script {
                    gv.Testapp()
                }
            }
        }
        stage("build") {
            steps {
              script  {
                buildjar()
                }
            }
        }
        stage("buildimage") {
            steps {
              script  {
                buildimage 'kalaimanims/mavenapp:1.4'
                }
            }
        }
        
        stage("deploy") {
          environment {
            AWS_ACCESS_KEY_ID=credentials(aws-access-key)
            AWS_SECRECRT_ACCESS_KEY_ID=creddentials(aws-secret-acess-key)
          }
            steps{
                script {
                    gv.Deployapp()
                }
            }
        }
       }
}
