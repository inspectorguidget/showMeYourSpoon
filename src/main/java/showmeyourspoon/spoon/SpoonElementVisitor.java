package showmeyourspoon.spoon;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface SpoonElementVisitor {
	void accept(final int level, final @NotNull String label, final @NotNull List<Integer> linesPosition);
}
