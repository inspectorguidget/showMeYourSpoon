package showmeyourspoon.instrument;

import io.interacto.jfx.instrument.JfxInstrument;
import io.interacto.jfx.interaction.library.Click;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import showmeyourspoon.command.SelectCodeText;
import showmeyourspoon.command.TreeLevel;
import showmeyourspoon.command.UpdateSpoonTree;

public class SpoonCodeInstrument extends JfxInstrument implements Initializable {
	@FXML private TextArea spoonCode;
	@FXML private TreeView<String> spoonAST;
	@FXML private CheckBox hideImplicit;
	@FXML private ComboBox<TreeLevel> treeLevel;


	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		setActivated(true);
		treeLevel.getItems().addAll(TreeLevel.values());
		treeLevel.getSelectionModel().select(TreeLevel.AUTO);
	}

	@Override
	protected void configureBindings() {
		textInputBinder(i -> new UpdateSpoonTree(spoonAST, hideImplicit.isSelected(), "", treeLevel.getValue()))
			.on(spoonCode)
			.then((i, c) -> c.setCode(i.getWidget().getText()))
			.bind();

		final Supplier<UpdateSpoonTree> cmdsupplier =
			() -> new UpdateSpoonTree(spoonAST, hideImplicit.isSelected(), spoonCode.getText(), treeLevel.getValue());

		checkboxBinder(cmdsupplier)
			.on(hideImplicit)
			.bind();

		comboboxBinder(cmdsupplier)
			.on(treeLevel)
			.bind();

		nodeBinder(new Click(), i -> new SelectCodeText(spoonCode,
				i.getSrcObject().filter(o -> o instanceof Text).map(o -> ((Text) o).getText()).orElse(null)))
			.on(spoonAST)
			.bind();
	}
}
