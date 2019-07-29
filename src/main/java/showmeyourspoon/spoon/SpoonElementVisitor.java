package showmeyourspoon.spoon;

import java.util.List;

public interface SpoonElementVisitor {
	void accept(final int level, final String label, final List<Integer> linesPosition);
}
