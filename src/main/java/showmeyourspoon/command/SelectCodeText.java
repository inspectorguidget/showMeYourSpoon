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
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;

public class SelectCodeText extends CommandImpl {
	private final int startPosition;
	private final int endPosition;
	private final @NotNull TextArea spoonCode;

	public SelectCodeText(final @NotNull TextArea spoonCode, final int startPosition, final int endPosition) {
		super();
		this.spoonCode = spoonCode;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	@Override
	protected void doCmdBody() {
		if(startPosition == -1) {
			Platform.runLater(() -> {
				spoonCode.deselect();
				spoonCode.requestFocus();
			});
		}else {
			Platform.runLater(() -> {
				spoonCode.selectRange(startPosition, endPosition + 1);
				spoonCode.requestFocus();
			});
		}
	}
}
