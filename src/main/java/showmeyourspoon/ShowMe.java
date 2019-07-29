package showmeyourspoon;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import showmeyourspoon.spoon.BasicVisitor;
import showmeyourspoon.spoon.ConsolePrinter;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

public class ShowMe extends Application {
	public static void main(final String[] args) {
		launch(args);

		if(args.length > 1) {
			final CtClass<?> elt = Launcher.parseClass(args[1]);
			elt.accept(new BasicVisitor(new ConsolePrinter(), true));
		}
	}

	@Override
	public void start(final Stage primaryStage) {
		try {
			final BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"));
			final Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
}