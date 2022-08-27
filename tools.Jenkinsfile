pipeline{
    agent any
    tools {
        maven 'Maven'
        nodejs 'NodeJS'
    }
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                sh 'npm install'

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
                sh 'npm test'
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application'
                sh 'npm publish'
            }
        }
       }
}
