package showmeyourspoon.spoon;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ConsolePrinter implements SpoonElementVisitor {
	@Override
	public void accept(final int level, final @NotNull String label, final @NotNull List<Integer> lines) {
		final String spaceChars = String.format("%" + level + "s", "");
		System.out.print(spaceChars);
		System.out.println(label);
	}
}
