package showmeyourspoon.command;

import io.interacto.command.CommandImpl;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import showmeyourspoon.spoon.SpoonTreeScaner;
import showmeyourspoon.spoon.TreePrinter;
import spoon.Launcher;
import spoon.compiler.Environment;
import spoon.reflect.CtModel;
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
		CtModel model = buildCode(code, 1);

		if(model.getRootPackage().getTypes().isEmpty()) {
			model = buildCode("public class ShowMeYourSpoonCapsule { " + code + "}", 2);
		}

		if(model.getRootPackage().getTypes().isEmpty()) {
			buildCode("public class ShowMeYourSpoonCapsule { public void showmeyourspoonmethod() {" + code + "}}", 3);
		}
	}

	private CtModel buildCode(final String theCode, final int levelsToIgnore) {
		final Launcher launcher = new Launcher();
		final Environment env = launcher.getEnvironment();

		launcher.addInputResource(new VirtualFile(theCode, "chunk.java"));
		env.setNoClasspath(true);
		env.setAutoImports(true);
		env.disableConsistencyChecks();
		env.setLevel("OFF");
		env.setComplianceLevel(11);

		launcher.buildModel().getRootPackage().accept(new SpoonTreeScaner(new TreePrinter(spoonAST, levelsToIgnore), hideImplicit));

		return launcher.getModel();
	}

	@Override
	public boolean canDo() {
		return code != null && !code.isEmpty();
	}

	public void setCode(final @NotNull String code) {
		this.code = code;
	}
}
