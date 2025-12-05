pipeline {
    agent any

    tools {
        maven 'Maven-3.9.0' // Configure this name in Jenkins Global Tool Configuration
        jdk 'JDK-17' // Configure this name in Jenkins Global Tool Configuration
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser for test execution')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'staging', 'prod'], description: 'Select environment')
        string(name: 'TEST_TAGS', defaultValue: '@login', description: 'Cucumber tags to execute (e.g., @login, @smoke)')
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
        PROJECT_DIR = 'Naukri'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout scm
                sh 'git log -1 --pretty=format:"%h - %an, %ar : %s"'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                dir("${PROJECT_DIR}") {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                dir("${PROJECT_DIR}") {
                    sh 'mvn test -Dtest=TestNG.xml || true'
                }
            }
        }

        stage('Integration Tests') {
            steps {
                echo "Running integration tests with browser: ${params.BROWSER}"
                dir("${PROJECT_DIR}") {
                    script {
                        // Run Cucumber tests with specified tags
                        sh """
                            mvn verify \
                            -Dcucumber.filter.tags='${params.TEST_TAGS}' \
                            -Dbrowser=${params.BROWSER} \
                            -Denvironment=${params.ENVIRONMENT} \
                            || true
                        """
                    }
                }
            }
        }

        stage('Generate Reports') {
            steps {
                echo 'Generating test reports...'
                dir("${PROJECT_DIR}") {
                    // Publish Cucumber reports
                    cucumber buildStatus: 'UNSTABLE',
                            reportTitle: 'Cucumber Test Report',
                            fileIncludePattern: '**/cucumber.json',
                            trendsLimit: 10,
                            classifications: [
                                [key: 'Browser', value: "${params.BROWSER}"],
                                [key: 'Environment', value: "${params.ENVIRONMENT}"]
                            ]
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving test artifacts...'
                dir("${PROJECT_DIR}") {
                    archiveArtifacts artifacts: 'target/cucumber-reports.html, target/cucumber.json, target/surefire-reports/**',
                                     allowEmptyArchive: true
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                echo 'Running code quality checks...'
                dir("${PROJECT_DIR}") {
                    // Optional: Add SonarQube analysis
                    // sh 'mvn sonar:sonar'
                    echo 'Code quality analysis step - configure SonarQube if needed'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed'
            dir("${PROJECT_DIR}") {
                // Clean workspace
                cleanWs()
            }
        }

        success {
            echo 'Pipeline executed successfully!'
            // Optional: Send success notification
            // emailext body: 'Test execution completed successfully',
            //          subject: 'Jenkins Build Success',
            //          to: 'sridharsush@gmail.com'
        }

        failure {
            echo 'Pipeline execution failed!'
            // Optional: Send failure notification
            // emailext body: 'Test execution failed. Please check the logs.',
            //          subject: 'Jenkins Build Failure',
            //          to: 'sridharsush@gmail.com'
        }

        unstable {
            echo 'Pipeline executed with test failures'
        }
    }
}
