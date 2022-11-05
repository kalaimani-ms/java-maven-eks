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
                script {withSonarQubeEnv(credentialsId: 'sonar') {
                    sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=maven-project \
                        -Dsonar.host.url=http://3.239.218.102:9000 \
                        -Dsonar.login=sqp_5228417a2b64f143711af7cc831a6a98c34fcaf2' 
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
                    sh 'envsubst < /var/jenkins_home/workspace/cluster_eks-clus_sonar/kubernetes/deployment.yaml | kubectl apply -f - '
                    sh 'envsubst < /var/jenkins_home/workspace/cluster_eks-clus_sonar/kubernetes/service.yaml | kubectl apply -f - '
                    sh 'kubectl get pod '
                    sh 'kubectl get all'
                }
            }
        }
        stage('commit version update to Github ') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'kalaimani-ms-git', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        //  git config here for the first time running
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh "git remote set-url origin https://$USER:$PASS@github.com/kalaimani-ms/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:eks-clus/sonar'
                    }
                    
                }
            }
        }
    }
}
