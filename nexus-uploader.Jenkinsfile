#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        APP_NAME= 'mavenapp'
    }
    options {
  buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '1', numToKeepStr: '3')
}
    stages {
        stage ('buidiljar') {
            steps {
                script {
                    echo 'building the application'
                    sh 'mvn  clean package'
                }
            }
        }
        stage ('sonarqube analysis'){
            steps {
                script {withSonarQubeEnv(credentialsId: 'sonar') {
                    sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=java-maven \
                        -Dsonar.host.url=http://13.235.86.29:9000 \
                        -Dsonar.login=sqp_0fef8cfe634cbe66ecb4913feafb3e05c6848220' 
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script{
                    timeout(time: 1, unit: 'HOURS') {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                          emailext body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:
                          
                          Check  console output at $BUILD_URL to view the results.''', recipientProviders: [developers()], subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'kalaimanicit@gmail.com,kalaidhoni95@gmail.com'
                          error "Pipeline aborted due to quality gate failure: ${qg.status}"
                       }
                    }
                }   
            }
        }
        stage ('Artifact Upload to Nexus'){
            steps{
                script{
                    def pomAppVersion = readMavenPom file:'pom.xml'
                    def pomAppRepo = pomAppVersion.version.endsWith('SNAPSHOT') ? "java-maven-snapshot" : "java-maven-release"
                        nexusArtifactUploader artifacts: [
                        [artifactId: 'java-maven-app', 
                        classifier: '', 
                        file: "target/java-maven-app-${pomAppVersion.version}.jar", 
                        type: 'jar']
                        ], 
                        credentialsId: 'nexus-id', 
                        groupId: 'com.example', 
                        nexusUrl: '3.110.168.3:8081', 
                        nexusVersion: 'nexus3', 
                        protocol: 'http',
                        repository: pomAppRepo, 
                        version: "${pomAppVersion.version}"
                }
            }
        }
        stage('build and push docker image'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'kalai-nexus',usernameVariable : 'USER',passwordVariable: 'PWD')]) {
                        sh "docker build -t 3.110.168.3:8081/repository/java-maven-docker-images/:${IMAGE_NAME} ."
                        sh "echo $PWD | docker login -u $USER --password-stdin"
                        sh "docker push 3.110.168.3:8081/repository/java-maven-docker-images/:${IMAGE_NAME}"
                        sh 'docker images'
                }
            }
        }  
    }   
}