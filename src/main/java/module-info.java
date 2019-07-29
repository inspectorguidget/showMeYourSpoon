module showmeyourspoon {
	requires spoon.core;
	requires interacto.javafx;
	requires interacto.java.api;
	requires javafx.fxml;
	requires javafx.controls;
	requires org.eclipse.jdt.core;

	exports showmeyourspoon to javafx.graphics;
	exports showmeyourspoon.instrument to javafx.fxml;

	opens showmeyourspoon.instrument to javafx.fxml;
	opens showmeyourspoon.command to javafx.fxml;
}