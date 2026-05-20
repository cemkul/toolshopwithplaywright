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

withCredentials([usernamePassword(
    credentialsId: 'toolshop-auth',
    usernameVariable: 'AUTH_EMAIL',
    passwordVariable: 'AUTH_PASSWORD'
)]) {
    sh '''
        docker run --rm \
        -u "$(id -u):$(id -g)" \
        -e AUTH_EMAIL="$AUTH_EMAIL" \
        -e AUTH_PASSWORD="$AUTH_PASSWORD" \
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

        withCredentials([usernamePassword(
        credentialsId: 'toolshop-auth',
         usernameVariable: 'AUTH_EMAIL',
        passwordVariable: 'AUTH_PASSWORD'
        )]) {
             sh '''
        docker run --rm \
        -u "$(id -u):$(id -g)" \
        -e AUTH_EMAIL="$AUTH_EMAIL" \
        -e AUTH_PASSWORD="$AUTH_PASSWORD" \
        -v "$WORKSPACE/docker-target:/app/target" \
        toolshop-playwright \
        mvn test \
        -Dmaven.repo.local=/app/target/.m2/repository \
        -Dheadless=true \
        -Dtest=ProductApiTests
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
