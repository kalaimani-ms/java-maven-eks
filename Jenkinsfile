pipeline{
    agent any
    environment{
        NEW_VERSION = '1.3.0'}
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                echo  "building the app version ${NEW_VERSION}"

            }
        }
        stage("test") {
            when {
                expression{
                    env.BRANCH_NAME=='conditionals'
                }

            }
            steps {
                echo 'Testing the application..'
                echo  "testing the app version ${NEW_VERSION}"
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application'
                echo  "deploying the app version ${NEW_VERSION}"
            }
        }
        post {
            failure {
                echo 'test and deploy got skipped due to previous failure'
            }
            success {
                echo ' build Test Deploy are success'
        }
       }
    }
}
