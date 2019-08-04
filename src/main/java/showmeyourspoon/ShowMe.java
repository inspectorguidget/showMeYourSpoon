package showmeyourspoon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import showmeyourspoon.command.TreeLevel;
import showmeyourspoon.command.UpdateSpoonTree;

public class ShowMe extends Application {
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
		final Parent root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		// Can have an argument: a java file to load
		final Parameters params = getParameters();
		if(!params.getUnnamed().isEmpty()) {
			final List<String> code = Files.readAllLines(Paths.get(new File(params.getUnnamed().get(0)).toURI()));
			final Node area = root.lookup("#spoonCode");
			final Node tree = root.lookup("#spoonAST");
			if(area instanceof TextArea && tree instanceof TreeView) {
				final TextArea spoonCode = (TextArea) area;
				spoonCode.setText(String.join(System.getProperty("line.separator"), code));
				new UpdateSpoonTree((TreeView<String>) tree, true, spoonCode.getText(), TreeLevel.AUTO).doIt();
			}
		}
	}
}