package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectCodeText extends CommandImpl {
	static final Pattern PATTERN = Pattern.compile(".*, lines: \\[(\\d+), (\\d+)\\]$");

	final @Nullable String item;
	final @NotNull TextArea spoonCode;

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
				final int start = getCharPositionOfLine(Integer.parseInt(matcher.group(1)) - 1);
				final int end = getCharPositionOfLine(Integer.parseInt(matcher.group(2)));

				Platform.runLater(() -> {
					spoonCode.selectRange(start, end);
					spoonCode.requestFocus();
				});
			}
		}
	}

	private int getCharPositionOfLine(final int line) {
		final String eol = System.getProperty("line.separator");
		final int lengtheol = eol.length();
		final String[] lines = spoonCode.getText().split(eol);

		return IntStream.range(0, line)
			.map(i -> lines[i].length() + lengtheol)
			.sum();
	}
}
