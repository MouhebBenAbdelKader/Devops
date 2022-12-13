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

         stage('DOCKER COMPOSE') {
         steps{
                sh 'docker compose up -d --build'
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
           nexusArtifactUploader artifacts:
           [[artifactId: 'ExamThourayaS2',
           classifier: '',
           file: 'target/ExamThourayaS2-1.0.0.jar',
           type: 'jar']],
           credentialsId: 'nexus3',
           groupId: 'tn.esprit',
           nexusUrl: '192.168.1.105:8081/repository/maven-releases',
           nexusVersion: 'nexus3',
           protocol: 'http',
           repository: 'docker-exam-releases',
           version: '1.0.0'
        }
     }

     /*-----------------*/
    stage("Build docker image") {
        steps{
        script {

              sh" sudo chmod 666 /var/run/docker.sock"
           sh ' docker build -t mouhebabdelkadder/examthourayas2-1.0 .'


          }
        }
        }



          stage("Publish  image to docker hub") {
        steps{
        sh 'docker login -u mouhebabdelkadder -p dckr_pat_oanTvQFnRjlS_gv0RjWASysUDKA'
            	sh  'docker push mouhebabdelkadder/examthourayas2-1.0:latest'
              }
        }


	  /* stage("Push image to nexus") {
        steps{
                sh ' docker build -t 192.168.1.105:8083/examthourayas2-1.0 .'
         	    sh 'docker login -u admin -p admin 192.168.1.105:8083'
            	sh  'docker push 192.168.1.105:8083/examthourayas2-1.0:latest'
              }

        }*/



  }
}