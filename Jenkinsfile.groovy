/*pipeline {
    agent any

     stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/your-user/RockPaperScissorgame.git'
            }
        }

        stage('Deploy to Server') {
            steps {
                script {
                    sh '''
                    # Define your deployment steps
                    echo "Deploying to server..."
                    cp -r * /var/www/html/  # Copy files to Apache/Nginx root
                    '''
            }
        }
           }   }
} */
// integate docker into ci/cd pipeline using jenkins
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/bhavana-1997/RockPaperScissorgame'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t chowdary175/rockpaperscissors .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                    sh 'docker push chowdary175/rockpaperscissors'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                sh 'docker run -d -p 3000:3000 chowdary175/rockpaperscissors'
            }
        }
    }
}
