package showmeyourspoon.spoon;

import java.util.List;

public class ConsolePrinter implements SpoonElementVisitor {
	@Override
	public void accept(final int level, final String label, final List<Integer> lines) {
		final String spaceChars = String.format("%" + level + "s", "");
		System.out.print(spaceChars);
		System.out.println(label);
	}
}
