# ShowMeYourSpoon

A JavaFX app for visualising the Spoon tree of Java code (Java 11 supported).

An alternative to the native GUI support of Spoon ASTs with Spoon:

`$ java -cp spoon...-jar-with-dependencies.jar spoon.Launcher -i class.java --gui --noclasspath`

## Contributors

- @arnobl

## How to run

The app requires a JDK 11.
We currently do not provide any packaging of the app (`jlink` cannot package apps having non-modular libraries, such as Spoon).

So, to run the app, run: `mvn clean package`. 
Then go into the `target/modules` folder and launch: `java --module-path . --add-modules=javafx.controls,javafx.fxml,interacto.javafx,interacto.java.api,spoon.core,org.eclipse.jdt.core,annotations -jar showmeyourspoon-1.2.jar`

## Features Summary

- [selection highlighting](#feature-selection-highlighting)
- [implicit elements masking](#feature-implicit-elements-masking)
- [export as text](#feature-export-as-text)
- [analysis level](#feature-analysis-level)
- [one argument to load a Java file](#feature-one-argument-to-load-a-java-file)

## Feature: selection highlighting

Put the code in the text area on the left.
The Spoon tree should then appear (if not, maybe the code is not ok).

You can click on a tree element on the right to select the corresponding code elements on the left.
This does not work when the clicked tree item refers to a code element that does not have a line position.

![features](doc/appFeat.png)

You can also click on a code element in the code area to select the corresponding tree item.
On the next picture, the user clicked on `int`:

![features](doc/appFeat2.png)


## Feature: implicit elements masking

The checkbox in the toolbar masks or shows the Java implicit elements (printed with `'(implicit)'` in the tree view).

## Feature: export as text

The button `Save` exports the tree view of the current Java code in a text file.


## Feature: analysis level

Users can set the analysis level: `auto`, `class element`, `statement`, `expression`.

The `auto` level tries to detect the level automatically by starting parsing the code as a Java class (then class element, etc.):

![auto](doc/appAuto.png)

The `auto` level may fail, in particular with statements or expressions.
So users can define a specific level manually.

The `class element` level corresponds to fields, methods, etc., of a class:

![auto](doc/appClassElt.png)

The `statement` level corresponds to Java code statements:

![auto](doc/appStatement.png)

The `expression` level corresponds to any Java expression:

![auto](doc/appExp.png)


## Feature: one argument to load a Java file

The app can take one argument: the path of a Java file to load.
  