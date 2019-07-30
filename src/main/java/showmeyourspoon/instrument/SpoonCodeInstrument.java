package showmeyourspoon.instrument;

import io.interacto.jfx.instrument.JfxInstrument;
import io.interacto.jfx.interaction.library.Click;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import showmeyourspoon.command.SelectCodeText;
import showmeyourspoon.command.UpdateSpoonTree;

public class SpoonCodeInstrument extends JfxInstrument implements Initializable {
	@FXML TextArea spoonCode;
	@FXML TreeView<String> spoonAST;
	@FXML CheckBox hideImplicit;


	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() {
		textInputBinder(i -> new UpdateSpoonTree(spoonAST, hideImplicit.isSelected(), ""))
			.on(spoonCode)
			.then((i, c) -> c.setCode(i.getWidget().getText()))
			.bind();

		checkboxBinder(i -> new UpdateSpoonTree(spoonAST, hideImplicit.isSelected(), spoonCode.getText()))
			.on(hideImplicit)
			.bind();

		nodeBinder(new Click(), i -> new SelectCodeText(spoonCode,
				i.getSrcObject().filter(o -> o instanceof Text).map(o -> ((Text) o).getText()).orElse(null)))
			.on(spoonAST)
			.bind();
	}
}
