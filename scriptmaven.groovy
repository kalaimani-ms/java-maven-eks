def gv

def Buildjar() {
    echo 'building the applications by github webhook trigger....haha...'
    echo 'building the maven application..'
    sh 'mvn package'
    }
    
def Buildimage() {
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
    

def Testapp() {
        echo 'Testing the application..'
        sh 'mvn test'
    }

def Deployapp() {
       echo 'deploying the application'
    }

return this
