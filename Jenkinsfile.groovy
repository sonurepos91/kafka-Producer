#!Groovy Pipeline Script

properties([parameters(
        [
                choice(
                        choices: ['6.0.4','6.0.3', '6.0.0'].join("\n"),
                        description: 'Build Version',
                        name: 'buildVersion'
                ),
                string(
                        defaultValue: "",
                        description: "Git Branch",
                        name: "gitBranch"
                )
        ]
)])
pipeline{

    agent any

    stages{
        stage('Clean'){
            echo "Maven Clean Started...... "
            echo "Maven Clean Started...... "
        }
        stage('Build'){
            echo "Maven Build Started...... "
            echo "Maven Build Started...... "
        }
        stage('Deploy'){
            echo "Deploy Started...... "
            echo "Deploy Done...... "
        }
    }
}
