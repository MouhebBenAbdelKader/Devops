pipeline {
   agent {
   		label "jenkins"
   }

   tools {
      maven 'M2_HOME'
    }


  stages {


            stage('Get Project from Github') {
            steps {
                   echo 'Getting Project from Git'
                git branch: 'main', url: 'https://github.com/MouhebBenAbdelKader/Devops.git',
                credentialsId:'git_access'

            }
}

         stage("MVN Clean Install after getting  "){
            steps {
                sh """mvn clean install -Dskiptest """
               /*
               sh " mvn clean  -ptest"
               sh " mvn clean package  -pprod"
                */
            }
        }

      stage("Sonar") {
        steps {

       sh "mvn clean verify  sonar:sonar \
        -Dsonar.projectKey=ExamThourayaS2 \
        -Dsonar.host.url=http://192.168.1.105:9000 \
        -Dsonar.login=admin \
        -Dsonar.password=admin123 \
        -Dsonar.java.binaries=src/main "


               }
     }
     stage("Mockito Tests "){
                      steps {
                             sh 'mvn test -Ptest'
                           //sh " mvn clean package  -pprod"
                             }
                  }

     stage("nexus") {
        steps{
           echo "deploy project on nexus"
           nexusArtifactUploader artifacts: [
           		[
           			artifactId: 'tpAchatProject',
           			classifier: '',
           			file: 'target/tpAchatProject-1.0.jar',
           			type: 'jar'
           		]
           	],
           	credentialsId: 'nexus3',
           	groupId: 'com.esprit.examen',
           	nexusUrl: '192.168.1.240:8081/repository/maven-releases',
           	nexusVersion: 'nexus3',
           	protocol: 'http',
           	repository: 'deploymentRepo',
           	version: '1.0'
        }
     }

     /*-----------------*/
    stage("Build docker image") {
        steps{
        script {

              sh" sudo chmod 666 /var/run/docker.sock"
           sh ' docker build -t houssemriahi/tp_achat_project-1.0 .'


          }
        }
        }
     /*
          stage("Publish  image to docker hub") {
        steps{
        sh 'docker login -u houssemriahi -p dckr_pat_lnrW2ppnnwvpL194maOAhLuS_7E'
            	sh  'docker push houssemriahi/tp_achat_project-1.5:latest'
              }
        }
	  */

	   stage("Push image to nexus") {
        steps{
                sh ' docker build -t 192.168.1.240:5050/tp_achat_project-1.0 .'
         	    sh 'docker login -u admin -p 1327 192.168.1.240:5050'
            	sh  'docker push 192.168.1.240:5050/tp_achat_project-1.0:latest'
              }

        }

        stage('DOCKER COMPOSE') {
       steps{
                sh 'docker-compose up -d --build'
            }
      }


     /*-----------*/
     stage('Mailling') {
            steps {
                echo "Sending mail ..."

                    }
            }

  }
  post {
    always {
	     mail to: "houssem.riahi@esprit.tn",
               subject: "pipline successfully executed",
                body: "pipline successfully executed "
      cleanWs()


    }

  }
}