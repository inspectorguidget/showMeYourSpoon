package showmeyourspoon.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import showmeyourspoon.spoon.ConsolePrinter;
import showmeyourspoon.spoon.SpoonElementVisitor;

public class SaveTreeText extends SpoonTreeCmdBase {
	private final @Nullable File file;
	private PrintStream printer;

	public SaveTreeText(final @Nullable File file, final boolean hideImplicit, final @NotNull String code,
			final @NotNull TreeLevel treeLevel) {
		super(hideImplicit, code, treeLevel);
		this.file = file;
	}

	@Override
	SpoonElementVisitor createSpoonVisitor(final int levelsToIgnore) {
		try {
			printer = new PrintStream(file);
			return new ConsolePrinter(printer, levelsToIgnore);
		}catch(final FileNotFoundException | SecurityException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	protected void doCmdBody() {
		super.doCmdBody();
		if(printer != null) {
			printer.close();
		}
	}

	@Override
	public boolean canDo() {
		return file != null && super.canDo();
	}
}
