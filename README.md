# ShowMeYourSpoon

A JavaFX app for visualising the Spoon tree of Java code (Java 11 supported).

## Feature: selection highlighting and implicit elements masking

Put the code in the text area on the left.
The Spoon tree should then appear (if not maybe the code is not ok).

You can click on a tree element on the right to select the corresponding code (lines) on the left.
This does not work when the clicked tree item refers to a code element that does not have line position.

The checkbox in the toolbar masks or shows the Java implicit elements (printed with `'(implicit)'` in the tree view).

![features](doc/appFeat.png)



## Feature: analysis level

Users can set the analysis level: `auto`, `class element`, `statement`, `expression`.

The `auto` level tries to detect the level automatically by starting parsing the code as a Java class (then class element, etc.):

![auto](doc/appAuto.png)

The `auto` level may fails, in particular with statements or expressions.
So, users can define a specific level.

The `class element` level corresponds to fields, methods, etc. of a class:

![auto](doc/appClassElt.png)

The `statement` level corresponds to Java code statements:

![auto](doc/appStatement.png)

The `expression` level corresponds to any Java expression:

![auto](doc/appExp.png)