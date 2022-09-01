def gv

def Testapp() {
        echo 'Testing the application branch by multibranch scan webohook trigger
        echo "Testing the application branch $BRANCH_NAME.."
        sh 'mvn test'
    }

def Buildjar() {
    echo 'building the maven application..'
    sh 'mvn package'
    }
    
def Buildimage() {
    echo 'building the maven application Image..'
    withCredentials([usernamePassword(credentialsId: 'kalaimanims-Dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    sh 'docker images'
    sh 'docker build -t kalaimanims/mavenapp:1.3 .'
    sh 'docker images'
    sh "echo $PASS | docker login -u $USER --password-stdin"
    sh 'docker push kalaimanims/mavenapp:1.3'
    sh 'docker images'
}
}

def Deployapp() {
       echo 'deploying the application'
    }

return this
