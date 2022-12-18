#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        APP_NAME= 'mavenapp'
    }
    stages {
        stage('incremental version') {
            steps {
                script {
                    echo 'incrementing the app version'
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }    
            }
        }
        stage ('buidiljar') {
            steps {
                script {
                    echo 'building the application'
                    sh 'mvn clean package'
                }
            }
        }
        stage ('sonarqube analysis'){
            steps {
                script {withSonarQubeEnv(credentialsId: 'sonar-id') {
                    sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=new-project \
                        -Dsonar.host.url=http://3.110.54.87:9000 \
                        -Dsonar.login=squ_46829241141aefdcfe1f3305ffcaac9be1b6cb1a'
                    }
                }
            }
        }
        stage ('push image to Dockerhub') {
            steps {
                script {
                    echo 'building the docker images'
                    sh 'docker images'
                    withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub',usernameVariable : 'USER',passwordVariable: 'PASS')]) {
                        sh "docker build -t kalaimanims/mavenapp:${IMAGE_NAME} ."
                        sh "echo $PASS | docker login -u $USER --password-stdin "
                        sh "docker push kalaimanims/mavenapp:${IMAGE_NAME}"
                        sh 'docker images'
                    }
                }
            }
        }
        stage ('deployapp to k8s') {
            steps {
                script {
                    echo 'deploying the java-maven-app to kubernetes cluster from jenkins'
                    sh 'envsubst < /var/jenkins_home/workspace/ter-sonar-jenkins_eks_sonar_test/kubernetes/deployment.yaml | kubectl apply -f - '
                    sh 'envsubst < /var/jenkins_home/workspace/ter-sonar-jenkins_eks_sonar_test/kubernetes/service.yaml | kubectl apply -f - '
                    sh 'kubectl get pod '
                    sh 'kubectl get all'
                }
            }
        }
    }
}
