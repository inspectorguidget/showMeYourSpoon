package showmeyourspoon.spoon;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class SpoonElementVisitor {
	final int levelsToIgnore;

	SpoonElementVisitor(final int levelsToIgnore) {
		super();
		this.levelsToIgnore = levelsToIgnore;
	}

	public abstract void accept(final int level, final @NotNull String label, final @NotNull List<Integer> linesPosition);
}
