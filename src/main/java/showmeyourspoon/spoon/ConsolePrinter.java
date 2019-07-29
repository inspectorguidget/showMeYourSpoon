package showmeyourspoon.spoon;

import java.util.function.BiConsumer;

public class ConsolePrinter implements BiConsumer<Integer, String> {
	@Override
	public void accept(final Integer level, final String label) {
		final String spaceChars = String.format("%" + level + "s", "");
		System.out.print(spaceChars);
		System.out.println(label);
	}
}
