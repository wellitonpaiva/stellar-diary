pipeline {
    agent {
        docker { image 'openjdk:15-jdk-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'java --version'
            }
        }
    }
}