package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import showmeyourspoon.spoon.SpoonTreeItem;

public class SelectCodeItem extends CommandImpl {
	final int caretPosition;
	final TreeView<String> spoonTree;

	public SelectCodeItem(final int caretPosition, final @NotNull TreeView<String> spoonTree) {
		super();
		this.caretPosition = caretPosition;
		this.spoonTree = spoonTree;
	}

	@Override
	protected void doCmdBody() {
		int row = 0;
		SpoonTreeItem item = (SpoonTreeItem) spoonTree.getTreeItem(row);
		final List<SpoonTreeItem> foundItems = new ArrayList<>();

		while(item != null) {
			// Collecting all the matching items
			if(caretPosition > item.startPosition && caretPosition <= item.endPosition) {
				foundItems.add(item);
			}

			row++;
			item = (SpoonTreeItem) spoonTree.getTreeItem(row);
		}

		if(!foundItems.isEmpty()) {
			// Selecting the last item (deepest item) as it may be the most precise location
			Platform.runLater(() -> spoonTree.getSelectionModel().select(foundItems.get(foundItems.size() - 1)));
		}
	}
}
