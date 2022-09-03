pipeline{
    agent any
    tools{
        maven 'Maven'
    }
       stages {
        stage("incremental_version") {
            steps {
                script {
            echo 'incrementing the app version'
            sh 'mvn build-helper:parse-Version Versions:set -Dnewversion = ///${parsedVersion.majorVersion}. \
            ${parsedVersion.minorVersion}.${parsedVersion.newIncrementalVersion} \
            Versions:commit '
            def matcher =readFile('pom.xml') =~ '</version>(.+)<version>'
            def version = matcher[0][1]
            env.IMAGE_NAME= "$version-$BUILD_NUMBER"
            }
        }
        }
        stage("buildJAR") {
            steps {
                script {
                echo 'building the maven application..'
                sh 'mvn clean package'
                }
            }
        }
        stage("buildImage") {
            steps { 
                script {
                echo 'building the maven application Image..'
                withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh 'docker images'
                sh "docker build -t kalaimanims/mavenapp:${IMAGE_NAME} ."
                sh 'docker images'
                sh "echo $PASS | docker login -u $USER --password-stdin"
                sh "docker push kalaimanims/mavenapp:${IMAGE_NAME}"
                sh 'docker images'
                }
}
                
            }
        }
        stage("test") {
            steps {
                script {
                echo 'Testing the application..'
                sh 'mvn test'
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                echo 'deploying the application'
                }
            }
        }
       }
}
