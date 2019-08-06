workflow "Run Maven" {
  on = "push"
  resolves = ["Run maven"]
}

action "Run maven" {
  uses = "docker://maven:3.6.1-jdk-8"
  runs = "pwd"
}
