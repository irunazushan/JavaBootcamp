# ImageReader

This program convert the image to ascii characters and outputs them to the console

## Compile:
    javac -d target src/java/edu/school21/printer/*/*.java

## Copy resources to the target:
    cp -r src/resources target/

## Creating jar archive:
    jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .


* We should apply 2 arguments for running  app:
    1) Character for white pixels;
    2) Character for black pixels;

## Run:
    java -classpath target edu.school21.printer.app.Program FIRST_ARG SECOND_ARG

Example: java -classpath target edu.school21.printer.app.Program . 0



