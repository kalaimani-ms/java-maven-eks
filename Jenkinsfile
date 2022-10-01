pipeline{
    agent any
        environment {
            AWS_ACCESS_KEY_ID=credentials('aws-access-key')
            AWS_SECRET_ACCESS_KEY_ID=credentials('aws-secret-access-key')
          }    
       stages {
        stage("build") {
            steps {
                echo 'building the application..'
                
            }
        }
        stage("test") {
            
            steps {
                echo 'Testing the application..'
                echo  'testing the app version ' 
            }
        }
        stage('kubeconfig') {
            steps {
                withKubeConfig([caCertificate: 'LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUMvakNDQWVhZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRJeU1UQXdNVEU0TXpneE5Wb1hEVE15TURreU9ERTRNemd4TlZvd0ZURVRNQkVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTldFCnlBZk1BRVNIdW9KUDU4TUJ0RElHcU1xcmVVZjk3aDlleGY1MXAyZ2ZoYkFIdkZqeSszRWdkam94dXhRQk1BK0YKVGRmRGJXQ1MxdGs3ZCttNm1OaWVkYXg0SnhZdjhHalhjejBYdGJ2V3RDTHBuaGhoZzIwYitEQlMxNjdUVGg3Vwo2OTlyeGxrbExTSVJpb1h0eU54N21VcHFxZUdkUFF3WCs1bEpuOUZ1cy8wUXA1Qk01d2RUR3J4dnBRTDdzOVF4CmVWOUl1WXhiUnUzRU5YdnUwQWNlT0VsVzRjNFlJQ3hneEgxTXJDVkxtMCswOFhCWnF4eW4wenFIK21TSzA3NmQKNk81U2pjR3JvR2gvZTlQaDMwUkNLQ3NDek92U3pwbW1Vc2JxR3JrRjJMUTdzMGZQNG8rRVQxOVIvN0czSUVQSwoyK28xVSsrb0xsNTc4NkdtdXJVQ0F3RUFBYU5aTUZjd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU1CQWY4d0hRWURWUjBPQkJZRUZGeDBmOStJZWd6Z3ZkVDNjRTdGeXVlUUhVL2lNQlVHQTFVZEVRUU8KTUF5Q0NtdDFZbVZ5Ym1WMFpYTXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRVR2SUVzMGdHM2xqdDJPMDFUYwpoOTRxRU9XS0MxMVBuK3NqQis2MUpNWWVXekJ1bmgrQ01PK01NTU9UeEFIQ0RNN29xZXNxdFdTVXFWQTB6OXVqCnM4bDFEaWVwOG9HZ0FQaDNhSXpXNnU3WkhFZEtZR1R2dWUzWURXeGxMYTJ4b3E1NHNSYmhXT3RTa1RzOEh6MW8KUnJQaVJXVHNpemN2aGluc1d2QThtcU8xS3ZXeUVFakk4S0FiN3RVSGxDMi9XaWlwT0k2UGIzeEtSK1JaekVzSApyTFlwY0R4dThscExLZmV4Y0hJcU42Y0VyRzZyRmhZNG95NjhWVjRVT2FXSGEwNnZNbHVsS0YvcS9HWmhEUHYwCmhMUitDem9saTBNaGFYakhrUzBZNE1FeDRaVVZsNEVkeGZva3djZko1VC9nbllvTjZhZGJHR29lejlWRnVzbXIKY05ZPQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==', clusterName: 'arn:aws:eks:us-east-1:667122861845:cluster/kalai-cluster', contextName: 'kalai-cluster', credentialsId: 'kubeconfig-id', namespace: 'default', serverUrl: 'https://E90A91F8222BEAA0695D293A55C01DD3.gr7.us-east-1.eks.amazonaws.com' ]) {
                sh 'curl -o kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/linux/amd64/kubectl'
                sh 'chmod +x ./kubectl'
                sh 'mv ./kubectl /usr/local/bin'
                sh 'kubectl version --short --client'
                }
            }
        }       
        stage("deploy") {
            steps {
                echo 'deploying the application'
                echo  'deploying the app version '
                sh 'kubectl create deployment nginx-deployment --image=nginx'
            }
        }
    }
}
