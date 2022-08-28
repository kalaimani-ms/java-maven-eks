pipeline{
    agent any
    parameters {
        choice(name:'VERSION',choices:['1.2.0','1.2.1','1.2.3'],description:'')
        booleanParam(name:'executeTests',defaultvalue:'True',description:'')
    }
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                echo  "building the app version ${params.VERSION}"
            }
        }
        stage("test") {
            when {
                expression{
                    param.executeTests
                }

            }
            steps {
                echo 'Testing the application..'
                echo  "testing the app version ${params.VERSION}"
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application'
                echo  "deploying the app version ${params.VERSION}"
            }
        }
    }
}
