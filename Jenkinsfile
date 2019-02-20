#!groovy

pipeline {
    agent { node { label 'headless' } }

    environment {
        LANGUAGE = 'en-GB'
    }
    parameters {
        booleanParam(
                name: 'SMOKE_TEST_ONLY',
                defaultValue: false,
                description: 'Run smoke tests only'
        )
        booleanParam(
                name: 'ANALYSIS_MODULE_ONLY',
                defaultValue: false,
                description: 'Run analysis module tests only'
        )
    }

    stages {
        stage('Smoke Tests') {
            when {
                expression { return params.SMOKE_TEST_ONLY }
            }
            steps {
                echo 'TODO: Add smoke tests'
            }
        }

        stage('Pipeline Tests') {
            when {
                expression { return params.ANALYSIS_MODULE_ONLY }
            }
            steps {
                sh 'chmod +x webdriver/linux/chromedriver.linux64'

                withMaven(
                        options: [artifactsPublisher(disabled: true)],
                        maven: 'mvn-3.3.9',
                ) {
                    sh 'mvn clean verify ' +
                            '-Dspring.profiles.active=bdd-env,native,chrome-headless,chrome-linux ' +
                            '-Dcucumber.options="--tags @Pipeline"'
                }
            }
        }

        stage('BDD Tests') {
            when {
                not { expression { return params.SMOKE_TEST_ONLY || params.ANALYSIS_MODULE_ONLY } }
            }
            steps {
                sh 'chmod +x webdriver/linux/chromedriver.linux64'

                withMaven(
                        options: [artifactsPublisher(disabled: true)],
                        maven: 'mvn-3.3.9',
                ) {
                    sh 'mvn clean verify -Dspring.profiles.active=bdd-env,native,chrome-headless,chrome-linux'
                }
            }
        }
    }

    post {
        always {
            cucumber 'target/*cucumber.json'
            junit 'target/*-reports/**/*.xml'
        }
        failure {
            office365ConnectorSend(
                    message: "[Open Cucumber Reports](${env.BUILD_URL}cucumber-html-reports/overview-features.html)",
                    status: '**BDD Tests Failed**',
                    color: '#FF4136',
                    webhookUrl: 'https://outlook.office.com/webhook/925394cd-41cb-4424-9a2c-751e908f8760@bfab70be-ba6a-4fec-8004-db823aa1fa9c/JenkinsCI/256b331337354e34ae75493d057566a7/a5ad0ed9-0723-4493-b2f8-d737bdd97552'
            )
        }
    }
}
