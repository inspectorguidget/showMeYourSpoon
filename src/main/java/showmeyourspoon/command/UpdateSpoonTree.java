package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import showmeyourspoon.spoon.SpoonTreeScaner;
import showmeyourspoon.spoon.TreePrinter;
import spoon.Launcher;
import spoon.compiler.Environment;
import spoon.support.compiler.VirtualFile;

public class UpdateSpoonTree extends CommandImpl {
	@Nullable String code;
	final @NotNull TreeView<String> spoonAST;
	final boolean hideImplicit;

	public UpdateSpoonTree(final @NotNull TreeView<String> spoonAST, final boolean hideImplicit, final @NotNull String code) {
		super();
		this.spoonAST = spoonAST;
		this.hideImplicit = hideImplicit;
		this.code = code;
	}

	@Override
	protected void doCmdBody() {
		final Launcher launcher = new Launcher();
		final Environment env = launcher.getEnvironment();

		launcher.addInputResource(new VirtualFile(code, "chunk.java"));
		env.setNoClasspath(true);
		env.setAutoImports(true);
		env.disableConsistencyChecks();
		env.setLevel("OFF");
		env.setComplianceLevel(11);

		launcher.buildModel().getRootPackage().accept(new SpoonTreeScaner(new TreePrinter(spoonAST), hideImplicit));
	}

	public void setCode(final @NotNull String code) {
		this.code = code;
	}
}
