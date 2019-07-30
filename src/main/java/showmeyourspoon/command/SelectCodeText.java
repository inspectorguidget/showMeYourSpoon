package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;

public class SelectCodeText extends CommandImpl {
	private final int startPosition;
	private final int endPosition;
	private final @NotNull TextArea spoonCode;

	public SelectCodeText(final @NotNull TextArea spoonCode, final int startPosition, final int endPosition) {
		super();
		this.spoonCode = spoonCode;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	@Override
	protected void doCmdBody() {
		if(startPosition == -1) {
			Platform.runLater(() -> {
				spoonCode.deselect();
				spoonCode.requestFocus();
			});
		}else {
			Platform.runLater(() -> {
				spoonCode.selectRange(startPosition, endPosition + 1);
				spoonCode.requestFocus();
			});
		}
	}
}
