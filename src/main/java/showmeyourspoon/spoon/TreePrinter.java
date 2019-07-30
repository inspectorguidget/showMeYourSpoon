package showmeyourspoon.spoon;

import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TreePrinter extends SpoonElementVisitor {
	private final @NotNull TreeView<String> tree;
	private @Nullable TreeItem<String> currItem;
	private int currLevel;

	public TreePrinter(final @NotNull TreeView<String> tree, final int levelsToIgnore) {
		super(levelsToIgnore);
		this.tree = tree;
		this.tree.setRoot(null);
		this.tree.setShowRoot(false);
		currItem = null;
	}

	@Override
	public void accept(final int level, final @NotNull String label, final @NotNull List<Integer> lines) {
		// level > 1 because the root element must be created to be then masked as several real tree roots may exist
		// Example: three statements with the statement level.
		// level <= levelsToIgnore: depending on the analysis level, some root elements must be hidden
		if(level > 1 && level <= levelsToIgnore) {
			return;
		}

		final int startPosition = lines.isEmpty() ? -1 : lines.get(0);
		final int endPosition = lines.isEmpty() ? -1 : lines.get(1);
		final SpoonTreeItem item = new SpoonTreeItem(label, startPosition, endPosition);
		item.setExpanded(true);

		if(currItem == null) {
			tree.setRoot(item);
		}else {
			if(currLevel < level) {
				currItem.getChildren().add(item);
			}else {
				TreeItem<String> parent = currItem.getParent();

				while(currLevel > level) {
					currLevel--;
					parent = parent.getParent();
				}

				if(parent != null) {
					parent.getChildren().add(item);
				}
			}
		}

		currLevel = level;
		currItem = item;
	}
}
