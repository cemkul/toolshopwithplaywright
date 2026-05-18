pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['API', 'UI', 'INTEGRATION', 'ALL'],
            description: 'Choose which test suite to run'
        )
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('01 - Checkout Source') {
            steps {
                checkout scm
            }
        }

        stage('02 - Environment Check') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    git --version
                    /opt/allure/bin/allure --version || true
                '''
            }
        }

        stage('03 - Run API Tests') {
            when {
                expression { params.TEST_SUITE == 'API' || params.TEST_SUITE == 'ALL' }
            }
            steps {
                sh 'mvn clean test -Dtest=ProductApiTests'
            }
        }

        stage('04 - Run UI Tests') {
            when {
                expression { params.TEST_SUITE == 'UI' || params.TEST_SUITE == 'ALL' }
            }
            steps {
                sh 'mvn clean test -Dtest=ProductDetailsTest'
            }
        }

        stage('05 - Run Integration Tests') {
            when {
                expression { params.TEST_SUITE == 'INTEGRATION' || params.TEST_SUITE == 'ALL' }
            }
            steps {
                sh 'mvn clean test -Dtest=ProductApiUiIntegrationTests'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'

            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            ])

            archiveArtifacts artifacts: 'target/allure-results/**, target/surefire-reports/**', allowEmptyArchive: true
        }

        success {
            echo "Build successful for suite: ${params.TEST_SUITE}"
        }

        failure {
            echo "Build failed for suite: ${params.TEST_SUITE}"
        }
    }
}
