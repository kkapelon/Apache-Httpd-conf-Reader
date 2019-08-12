name: Java CI

on: [push]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@master
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        version: 1.8
    - name: Build with Maven
      run: mvn package --file pom.xml
