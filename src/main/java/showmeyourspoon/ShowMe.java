package showmeyourspoon;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShowMe extends Application {
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"))));
		primaryStage.show();
	}
}