def gv

def Buildapp() {
        echo 'building the application..'
        echo  "building the app version ${params.VERSION}"
    }
    
def Testapp() {
        echo 'Testing the application..'
        echo  "testing the app version ${params.VERSION}"
    }

def Deployapp() {
        echo 'deploying the application'
        echo  "deploying the app version ${params.VERSION}"
    }

return this
