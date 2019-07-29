package showmeyourspoon.instrument;

import io.interacto.jfx.instrument.JfxInstrument;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import showmeyourspoon.command.UpdateSpoonTree;

public class SpoonCodeInstrument extends JfxInstrument implements Initializable {
	@FXML TextArea spoonCode;
	@FXML TreeView<String> spoonAST;


	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() {
		textInputBinder(i -> new UpdateSpoonTree(spoonAST))
			.on(spoonCode)
			.then((i, c) -> c.setCode(i.getWidget().getText()))
			.bind();
	}
}
