/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
