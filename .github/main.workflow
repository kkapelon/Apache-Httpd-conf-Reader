workflow "Run Maven" {
  on = "push"
  resolves = ["List files"]
}

action "Run maven" {
  uses = "docker://maven:3.6.1-jdk-8"
  runs = "mvn package"
}

action "List files" {
  uses = "docker://alpine:3.8"
  needs = ["Run maven"]
  runs = "ls -l "
}
