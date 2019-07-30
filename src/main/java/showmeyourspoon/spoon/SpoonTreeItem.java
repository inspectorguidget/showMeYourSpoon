package showmeyourspoon.spoon;

import javafx.scene.control.TreeItem;

public class SpoonTreeItem extends TreeItem<String> {
	public final int startPosition;
	public final int endPosition;

	public SpoonTreeItem(final String text, final int startPosition, final int endPosition) {
		super(text);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}
}
