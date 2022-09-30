pipeline{
    agent any
    
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
             environment {
            AWS_ACCESS_KEY_ID=credentials('aws-access-key')
            AWS_SECRET_ACCESS_KEY_ID=credentials('aws-secret-access-key')
          }
            steps {
                echo 'deploying the application'
                echo  'deploying the app version '
                sh 'kubectl create deployment nginx-deployment --image=nginx'
            }
        }
    }
}
