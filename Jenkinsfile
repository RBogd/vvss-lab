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
                git branch: 'master', url: 'http://gitea:3000/admin/RepoDemoL02TestingBBT.git'
                sh 'tree'
            }
        }
        stage('Build') {
            steps {
                dir('src') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Run a Test') {
            steps {
                dir('src') {
                    sh 'mvn -Dtest=AppTest,AppTestWBT verify'
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
