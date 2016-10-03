#!/bin/bash

./gradlew clean

./gradlew fatJar


# java -jar ./build/libs/gap-all*.jar -f ./src/test/resources/ionosphere.arff -i 10 -k 5
java -jar ./build/libs/gap-all*.jar -f ./src/test/resources/count_dataset_labtests.arff -i 10 -k 5
