pipeline{
    agent any
    tools{
        maven 'Maven'
    }
       stages {
        stage("buildJAR") {
            steps {
                echo 'building the maven application..'
                sh 'mvn package'
            }
        }
        stage("buildImage") {
            steps {
                echo 'building the maven application Image..'
                withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh 'docker images'
                sh 'docker build -t kalaimanims/mavenapp:1.2 .'
                sh 'docker images'
                sh "echo $PASS | docker login -u $USER --password-stdin"
                sh 'docker push kalaimanims/mavenapp:1.2'
                sh 'docker images'
}
                
            }
        }
        stage("test") {
            steps {
                echo 'Testing the application..'
                sh 'mvn test'
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application'
            }
        }
       }
}
