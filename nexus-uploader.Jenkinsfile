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
                    def matcher = readFile('pom.xml') =~ '<version>(SNAPSHOT.+)</version>'
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
                    nexusArtifactUploader artifacts: [
                        [artifactId: 'java-maven-app', 
                        classifier: '', 
                        file: 'target/java-maven-app-1.1.9-SNAPSHOT.jar', 
                        type: 'jar']
                        ], 
                        credentialsId: 'nexus-id', 
                        groupId: 'com.example', 
                        nexusUrl: '3.110.168.3:8081', 
                        nexusVersion: 'nexus3', 
                        protocol: 'http',
                        repository: 'java-maven', 
                        version: '1.1.9'
                }
            }
        }  
    }   
}