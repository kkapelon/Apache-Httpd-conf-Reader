workflow "Run Maven" {
  on = "push"
  resolves = ["Re-download same image"]
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

action "Re-download same image" {
  uses = "docker://alpine:3.8"
  needs = ["List files"]
  runs = "pwd"
}
