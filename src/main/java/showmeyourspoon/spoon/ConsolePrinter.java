package showmeyourspoon.spoon;

import java.io.PrintStream;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ConsolePrinter extends SpoonElementVisitor {
	final @NotNull PrintStream printer;

	public ConsolePrinter(final @NotNull PrintStream printer, final int levelsToIgnore) {
		super(levelsToIgnore);
		this.printer = printer;
	}

	@Override
	public void accept(final int level, final @NotNull String label, final @NotNull List<Integer> lines) {
		if(level <= levelsToIgnore) {
			return;
		}

		final String spaceChars = String.format("%" + level + "s", "");
		printer.print(spaceChars);
		printer.println(label);
	}
}
