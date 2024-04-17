#!Groovy Pipeline Script

properties([parameters(
        [
                choice(
                        choices: ['6.0.4', '6.0.3', '6.0.0'].join("\n"),
                        description: 'Build Version',
                        name: 'buildVersion'
                ),
                string(
                        defaultValue: "develop",
                        description: "Git Branch",
                        name: "gitBranch"
                ),
                string(
                        defaultValue: "https://github.com/sonurepos91/kafka-Producer.git",
                        description: "Git URL",
                        name: "gitUrl"
                ),
                booleanParam(
                        defaultValue: false,
                        description: "Git Poll Enabled",
                        name: "gitPoll"
                ),
                booleanParam(
                        defaultValue: false,
                        description: "Git ChangeLog Enabled",
                        name: "changeLog"
                )

        ]
)])
pipeline {

    agent any

    stages {
        stage("scmCheckout") {
            steps {
                script {
                    echo "Git  Checkout Started...... "
                    String gitBranch = params.gitBranch
                    String gitUrl = params.gitUrl
                    Boolean gitPoll = params.gitPoll
                    Boolean changeLog = params.changeLog
                    scmCheckout(gitBranch, gitUrl, gitPoll, changeLog)
                    echo "Git  Checkout Completed...... "
                }
            }
        }
        stage("Build") {
            steps {
                echo "Maven Build Started...... "
                echo "Maven Build Completed...... "
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploy Started...... "
                echo "Deploy Completed...... "
            }
        }
    }
}

def scmCheckout(String gitBranch, String gitUrl, Boolean gitPoll, Boolean changeLog) {
    echo "Git CLone start with following config : GitBranch : " + gitBranch +" GitUrl : " + gitUrl + " gitPoll : " + gitPoll + " changeLog : " + changeLog

}
