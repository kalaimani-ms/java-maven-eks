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
            when {
                expression{
                    BRANCH_NAME == 'master'
                }
            }
            steps {
              script  {
                gv.Buildjar()
                }
            }
        }
        stage("buildimage") {
            when {
                expression{
                    BRANCH_NAME == 'master'
                }
            }
            steps {
              script  {
                gv.Buildimage()
                }
            }
        }
        
        stage("deploy") {
          environment {
            AWS_ACCESS_KEY_ID=credentials('aws-access-key')
            AWS_SECRET_ACCESS_KEY_ID=credentials('aws-secret-access-key')
          }
            when {
                expression{
                    BRANCH_NAME == 'master'
                }
            }
            steps{
                script {
                    gv.Deployapp()
                }
            }
        }
       }
}
