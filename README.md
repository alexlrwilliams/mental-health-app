# Description

Application consists of Vue frontend and Spring Boot backend. frontend is the frontend service and user-auth-service, appointment-service, api-gateway, chat-service and s3-service are all backend.

# How to run

In the root directory (/mental-health-app) run ```docker-compose up```. 
Frontend is hosted on: http://localhost:8080/

Two of the existing accounts:

- email: doctor@gmail.com password: password
- email: patient@gmail.com password: password

Or use the register page to register a new account.

# Links
Jira: https://webgroupproject.atlassian.net/jira/software/projects/MHC/boards/1/backlog   
Docker Repo: https://hub.docker.com/repository/docker/alexwilliams2001/group13-docker-repo/general  
Report: https://docs.google.com/document/d/13zv69kEkiePmp9hZrLDa_0fTQ86w8X1dXBW76N7et6Y/edit#heading=h.9ais4y65zsp4

# Devs

## Git Branches

- Before starting a new task, make sure you have the latest main
- A new branch should be made for each task
  - New branches should be made either from main or from another task branch if it requires changes from said branch
  - The branch should be called 'feature/JIRA_CODE/DESCRIPTION' e.g. feature/MCH-6/create-appointments-database
- When pushing commits to the feature, a github actions workflow will start making sure the build and all tests pass
- When creating a pull request for a feature branch, the PR cannot be closed and merged to main until atleast 1 person has approved the PR and all builds steps have passed.
- When merging a PR with main, the image will be uploaded to the docker repo and replace the latest.
