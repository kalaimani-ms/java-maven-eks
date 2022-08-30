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
            steps{
                script {
                    gv.Deployapp()
                }
            }
        }
       }
}
