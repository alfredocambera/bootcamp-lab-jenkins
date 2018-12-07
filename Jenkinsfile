node {
  currentBuild.result = "SUCCESS"

  try {
    stage('Checkout') {
      // Deletes any file or directory from current workspace
      deleteDir()

      git 'https://github.com/boto/boto3.git'
    }

    stage('Prepare environment'){
      sh 'python3 -m virtualenv -p python3 .virtualenv/'
      sh 'readlink -f .virtualenv/bin/'
      sh '. .virtualenv/bin/activate'
      sh 'pip3 install tox'
    }

    stage('Unit Tests'){
      // Makes unit tests generate a junit compatible file for reporting
      sh 'sed -i -e \'19s/$/  \\x27--xunit-file=test-results-unit.xml\\x27,/g\' scripts/ci/run-tests'
      
      // Runs unit tests
      sh 'python3 -m  tox -e py35 -- unit/'
      
      // Restores 'run-tests'
      sh 'git checkout scripts/ci/run-tests'
    }

    //stage('Functional Tests'){
    //  sh 'python3 -m tox -e py35 -- functional/'
    //}

    stage('Create Report') {
      // Checks if file exists before creating symlink
      sh 'ls tests/test-results-unit.xml && ln -s $WORKSPACE/tests/test-results-unit.xml $WORKSPACE'
      
      junit "test-results-unit.xml"
    }
    
  }
  catch (err) {
    currentBuild.result = "FAILURE"
    throw err
  }

  finally {

    if (currentBuild.result == 'SUCCESS') {
      echo 'This will run on SUCCESS'
    }
    else if  (currentBuild.result == 'FAILURE') {
      echo 'This will run on FAILURE'
    }
    else if  (currentBuild.result == 'UNSTABLE') {
      echo 'This will run on UNSTABLE'
    }

    if (currentBuild.previousBuild?.result != null && currentBuild.previousBuild?.result != currentBuild.result) {
      echo 'This will run only if the state of the Pipeline has changed'
    }
  }
}
