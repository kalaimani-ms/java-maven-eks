#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'Maven'
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
        stage ('buildimage') {
            steps {
                script {
                    echo 'building the docker images'
                    sh 'docker images'
                    withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub',usernameVariable : 'USER',passwordVariable: 'PASS')]) {
                        sh "docker build -t kalaimanims/mavenapp:${IMAGE_NAME} ."
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh "docker push kalaimanims/mavenapp:${IMAGE_NAME}"
                        sh 'docker images'
                    }
                }
            }
        }
        stage ('deployapp') {
            steps {
                script {
                    echo 'deploying the app'
                }
            }
        }
        stage ('commit update from jenkins') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        echo 'commit updating to github from Jenkins'
                        sh 'git config --global user.email jenkins@jenkins.com'
                        sh 'git config --global user.name jenkins'

                        sh 'git branch'
                        sh 'git status'
                        sh 'git config --list'

                        sh "git remote set-url https://$USER:$PASS@github.com/kalaimani-ms/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "CI: commit bump"'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                    
                }
            }
        }
    }
}
