package showmeyourspoon.spoon;

import java.util.function.BiConsumer;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreePrinter implements BiConsumer<Integer, String> {
	final TreeView<String> tree;
	TreeItem<String> currItem;
	int currLevel;

	public TreePrinter(final TreeView<String> tree) {
		super();
		this.tree = tree;
		this.tree.setRoot(null);
		currItem = null;
		tree.setShowRoot(false);
	}

	@Override
	public void accept(final Integer level, final String label) {
		final TreeItem<String> item = new TreeItem<>(label);
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
				parent.getChildren().add(item);
			}
		}

		currLevel = level;
		currItem = item;
	}
}
