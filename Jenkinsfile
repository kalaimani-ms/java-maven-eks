pipeline{
    agent any
        environment {
            AWS_ACCESS_KEY_ID=credentials('aws-access-key')
            AWS_SECRET_ACCESS_KEY_ID=credentials('aws-secret-access-key')
          }    
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                
            }
        }
        stage("test") {
            
            steps {
                echo 'Testing the application..'
                echo  'testing the app version ' 
            }
        }    
        stage("deploy") {
            steps {
                echo 'deploying the application'
                echo  'deploying the app version '
                sh 'kubectl create deployment nginx-deployment --image=nginx'
                sh 'kubectl get nodes'
                sh 'kubectl get pod'
            }
        }
    }
}
