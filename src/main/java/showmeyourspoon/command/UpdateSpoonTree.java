package showmeyourspoon.command;

import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import showmeyourspoon.spoon.SpoonElementVisitor;
import showmeyourspoon.spoon.TreePrinter;

public class UpdateSpoonTree extends SpoonTreeCmdBase {
	/** The tree widget that shows the Spoon tree. */
	private final @NotNull TreeView<String> spoonAST;

	public UpdateSpoonTree(final @NotNull TreeView<String> spoonAST, final boolean hideImplicit, final @NotNull String code,
		final @NotNull TreeLevel treeLevel) {
		super(hideImplicit, code, treeLevel);
		this.spoonAST = spoonAST;
	}

	@Override
	SpoonElementVisitor createSpoonVisitor(final int levelsToIgnore) {
		return new TreePrinter(spoonAST, levelsToIgnore);
	}
}
