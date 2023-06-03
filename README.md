# Projet-GenieLogicielAvance-2022-2023

Ce projet a été réalisé pour le cours de Génie Logiciel Avancé de M1 Informatique 2022-2023 de l'Université Paris Cité.

Project information : [WikiPage](https://github.com/wehddn/Projet-GenieLogicielAvance-2022-2023/wiki)

## Configuration

**Configure git hooks**

git config core.hooksPath .githooks

# Unix-based systems :

## Build the project

./gradlew build

## Run the project

./gradlew run

## Run the tests

./gradlew test

## Generate javadoc in build/docs/javadoc

./gradlew javadoc

## Apply Spotless code formatter

./gradlew spotlessApply

# Windows :

## Install Gradle

You need to download Gradle here : https://gradle.org/releases/. After that, unpack to any directory and add "bin" folder to PATH.

## Build the project

gradle build

## Run the project

gradle run

## Run the tests

gradle test

## Generate javadoc in build/docs/javadoc

gradle javadoc

## Apply Spotless code formatter

gradle spotlessApply
