def gv

pipeline{
    agent any
    parameters{
        choice(name:'VERSION',choices:['1.2.0','1.2.1','1.2.3'],description:'')
        booleanParam(name:'executeTests',defaultValue:true,description:'')
    }   
       stages {
        stage("init") {
            steps {
                script {
                    gv = load 'script.groovy'
                }
                
            }
        }
        stage("build") {
            steps {
              script  {
                gv.Buildapp()
                }
            }
        }
        stage("test") {
            when{
                expression{
                    params.executeTests
                }
            }
            steps {
                script {
                    gv.Testapp()
                }
            }
        }
        stage("deploy") {
            steps{
                script {
                    gv.Deployapp()
                }
            }
        }
       }
}
