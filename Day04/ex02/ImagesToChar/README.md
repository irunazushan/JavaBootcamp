# ImageReader

This program convert the image to ascii characters and outputs them to the console

## Compile:
    javac -d target -cp "lib/*" src/java/edu/school21/printer/*/*.java

## Copy resources to the target:
    cp -r src/resources target/

## Extract libraries:
    cd target
    jar xf ../lib/JCDP-4.0.0.jar
    jar xf ../lib/jcommander-1.82.jar
    cd ..

## Creating jar archive:
    jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

* We should choose 2 colors for running  app:
  1) --white=*Color*
  2) --black=*Color*

## Run:
    java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN





