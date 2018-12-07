node {

  currentBuild.result = "SUCCESS"
  try {
    stage('Checkout') {
      git 'https://github.com/boto/boto3.git'
    }

    stage('Prepare environment'){
      sh 'python3 -m virtualenv -p python3 .virtualenv/'
      sh 'readlink -f .virtualenv/bin/'
      sh 'ls -al .virtualenv/bin'
      sh '. .virtualenv/bin/activate'
      sh 'pip3 install tox'
    }

    stage('Unit Tests'){
      // Hack para poder generar archivo .xml con resultados
      sh 'sed -i -e \'19s/$/  \\x27--xunit-file=test-results-unit.xml\\x27,/g\' scripts/ci/run-tests'
      
      // Ejecución de pruebas unitarias
      sh 'python3 -m  tox -e py35 -- unit/'
      
      // Ejecución de pruebas unitarias
      sh 'git checkout scripts/ci/run-tests'
    }

    stage('Functional Tests'){
      //sh 'python3 -m tox -e py35 -- functional/'
    }

    // This should be on post
    stage('Cleanup') {
      // To allow junit to find the file
      sh 'ln -s tests/test-results-unit.xml $WORKSPACE'
      
      junit "test-results-unit.xml"
      
      echo 'prune and cleanup'
      // Send email notification
      // Send slack notification
    }
  }
  catch (err) {
    currentBuild.result = "FAILURE"
    // Send email notification
    // Send slack notificaiton
    throw err
  }
}
