pipeline{
    agent any
    environment{
        NEW_VERSION = '1.3.0'
        server_credentials=credentials(	"kalaimani-ms-git")}
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                echo  "building the app version ${NEW_VERSION}"
                echo  "building the application with ${server_credentials}"

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
    }
}
