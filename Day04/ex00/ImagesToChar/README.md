# ImageReader

This program convert the image to ascii characters and outputs them to the console

## Compile:
    javac -d target src/java/edu/school21/printer/*/*.java  

* We should apply 3 arguments for running  app:
    FIRST_ARG: Character for white pixels;
    SECOND_ARG: Character for black pixels;
    THIRD_ARG Path to the image we want to read;

## Run:
    java -classpath target edu.school21.printer.app.Program FIRST_ARG SECOND_ARG THIRD_ARG

Example: java -classpath target edu.school21.printer.app.Program . 0 ../it.bmp




