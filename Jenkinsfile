pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

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
                    allure --version
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

            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            ])

            archiveArtifacts artifacts: 'target/allure-results/**, target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}
