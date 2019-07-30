package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectCodeText extends CommandImpl {
	private static final Pattern PATTERN = Pattern.compile(".*, position: \\[(\\d+), (\\d+)\\]$");

	private final @Nullable String item;
	private final @NotNull TextArea spoonCode;

	public SelectCodeText(final @NotNull TextArea spoonCode, final @Nullable String item) {
		super();
		this.spoonCode = spoonCode;
		this.item = item;
	}

	@Override
	protected void doCmdBody() {
		if(item == null) {
			Platform.runLater(() -> {
				spoonCode.deselect();
				spoonCode.requestFocus();
			});
		}else {
			final Matcher matcher = PATTERN.matcher(item);

			if(matcher.matches()) {
				final int start = Integer.parseInt(matcher.group(1));
				final int end = Integer.parseInt(matcher.group(2)) + 1;

				Platform.runLater(() -> {
					spoonCode.selectRange(start, end);
					spoonCode.requestFocus();
				});
			}
		}
	}
}
