package showmeyourspoon.spoon;

import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TreePrinter implements SpoonElementVisitor {
	final @NotNull TreeView<String> tree;
	@Nullable TreeItem<String> currItem;
	int currLevel;
	final int levelsToIgnore;

	public TreePrinter(final @NotNull TreeView<String> tree, final int levelsToIgnore) {
		super();
		this.levelsToIgnore = levelsToIgnore;
		this.tree = tree;
		this.tree.setRoot(null);
		currItem = null;
	}

	@Override
	public void accept(final int level, final @NotNull String label, final @NotNull List<Integer> lines) {
		if(level <= levelsToIgnore) {
			return;
		}

		final TreeItem<String> item = new TreeItem<>(label + ", lines: " + lines);
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
