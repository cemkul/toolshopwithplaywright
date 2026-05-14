pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Environment Info') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    git --version
                    pwd
                    ls -la
                '''
            }
        }

        stage('Run API Tests') {
            steps {
                sh 'mvn clean test -Dtest=ProductApiTests'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
