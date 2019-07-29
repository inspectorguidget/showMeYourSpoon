package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import javafx.scene.control.TreeView;
import showmeyourspoon.spoon.SpoonTreeScaner;
import showmeyourspoon.spoon.TreePrinter;
import spoon.Launcher;
import spoon.compiler.Environment;
import spoon.support.compiler.VirtualFile;

public class UpdateSpoonTree extends CommandImpl {
	String code;
	final TreeView<String> spoonAST;

	public UpdateSpoonTree(final TreeView<String> spoonAST) {
		super();
		this.spoonAST = spoonAST;
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

		launcher.buildModel().getRootPackage().accept(new SpoonTreeScaner(new TreePrinter(spoonAST), true));
	}

	public void setCode(final String code) {
		this.code = code;
	}
}
