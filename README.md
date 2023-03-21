# mental-health-app
Group Coursework

JIRA: https://webgroupproject.atlassian.net/jira/software/projects/MHC/boards/1/backlog   
DOCKER REPO: https://hub.docker.com/repository/docker/alexwilliams2001/group13-docker-repo/general

# Git Branches

- Before starting a new task, make sure you have the latest main
- A new branch should be made for each task
  - New branches should be made either from main or from another task branch if it requires changes from said branch
  - The branch should be called 'feature/JIRA_CODE/DESCRIPTION' e.g. feature/MCH-6/create-appointments-database
- When pushing commits to the feature, a github actions workflow will start making sure the build and all tests pass
- When creating a pull request for a feature branch, the PR cannot be closed and merged to main until atleast 1 person has approved the PR and all builds steps have passed.
- When merging a PR with main, the image will be uploaded to the docker repo and replace the latest.
