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
            steps {
                script {
                   echo 'deploying docker image to EC2...'

                   def shellCmd = "bash ./server-cmds.sh 'kalaimanims/mavenapp:1.4'"
                   def ec2Instance = "ubuntu@13.42.7.56"

                   sshagent(['ec2-server-key']) {
                       sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ubuntu"
                       sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ubuntu"
                       sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                   }
                }
            }
        }
    }
}