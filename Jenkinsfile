pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['API', 'UI', 'ALL'],
            description: 'Choose test suite'
        )
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t toolshop-playwright .'
            }
        }

        stage('Prepare Reports') {
            steps {
                sh '''
                    rm -rf docker-target
                    mkdir -p docker-target
                '''
            }
        }

        stage('Run API Tests') {
            when {
                expression { params.TEST_SUITE == 'API' || params.TEST_SUITE == 'ALL' }
            }

            steps {
                sh '''
                    docker run --rm \
		    -u "$(id -u):$(id -g)" \
                    -v "$WORKSPACE/docker-target:/app/target" \
                    toolshop-playwright \
                    mvn test \
		    -Dmaven.repo.local=/app/target/.m2/repository \
                    -Dheadless=true \
                    -Dtest=ProductApiTests
                '''
            }
        }

        stage('Run UI Tests') {
            when {
                expression { params.TEST_SUITE == 'UI' || params.TEST_SUITE == 'ALL' }
            }

            steps {
                sh '''
                    docker run --rm \
		    -u "$(id -u):$(id -g)" \
                    -v "$WORKSPACE/docker-target:/app/target" \
                    toolshop-playwright \
                    mvn test \
		    -Dmaven.repo.local=/app/target/.m2/repository \
		    -Dheadless=true \
                    -Dbrowser=chromium \
                    -Dtest=AuthTests,CartTest,CheckoutTest,HomeTests,ProductTests
                '''
            }
        }
    }

    post {

        always {

            junit(
                allowEmptyResults: true,
                testResults: 'docker-target/surefire-reports/*.xml'
            )

            allure([
                includeProperties: false,
                results: [[path: 'docker-target/allure-results']]
            ])

            archiveArtifacts(
                artifacts: 'docker-target/**',
                allowEmptyArchive: true
            )
        }

        success {
            echo 'Docker tests completed'
        }

        failure {
            echo 'Docker execution failed'
        }
    }
}
