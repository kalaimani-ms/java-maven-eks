def gv

pipeline{
    agent any
    tools {
        maven 'Maven'
    }
       stages {
        stage("init") {
            steps {
                script {
                    gv = load 'scriptmaven.groovy'
                }
                
            }
        }
        stage("build") {
            steps {
              script  {
                gv.Buildjar()
                }
            }
        }
        stage("buildimage") {
            steps {
              script  {
                gv.Buildimage()
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
        stage("deploy") {
            steps{
                script {
                    gv.Deployapp()
                }
            }
        }
       }
}
