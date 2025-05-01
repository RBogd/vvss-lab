pipeline {
    agent any
    stages {
        stage('Clean Workspace') {
            steps {
                sh 'rm -rf *'
            }
        }
        stage('Clone the repository') {
            steps {
                git branch: 'master', url: 'http://gitea:3000/admin/project.git'
                sh 'tree'
            }
        }
        stage('Build') {
            steps {
                dir('') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Run a Test') {
            steps {
                dir('') {
                    sh 'mvn -Dtest=TakeHomeMockTests -Dsurefire.failIfNoSpecifiedTests=false verify'
                }
            }
        }
        stage('Publish Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }
}
