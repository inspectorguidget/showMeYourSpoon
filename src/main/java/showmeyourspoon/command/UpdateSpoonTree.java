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
	/** The Java code to analyse. */
	@Nullable String code;
	/** The tree widget that shows the Spoon tree. */
	final @NotNull TreeView<String> spoonAST;
	/** Hides or not the implicit Spoon elements. */
	final boolean hideImplicit;
	/** The analysis level to consider. */
	final @NotNull TreeLevel treeLevel;

	public UpdateSpoonTree(final @NotNull TreeView<String> spoonAST, final boolean hideImplicit, final @NotNull String code,
		final @NotNull TreeLevel treeLevel) {
		super();
		this.spoonAST = spoonAST;
		this.hideImplicit = hideImplicit;
		this.treeLevel = treeLevel;
		this.code = code;
	}

	@Override
	protected void doCmdBody() {
		switch(treeLevel) {
			case AUTO:
				buildClassLevel();
				break;
			case CLASS_ELEMENT:
				buildClassElementLevel();
				break;
			case STATEMENT:
				buildStatementLevel();
				break;
			case EXPRESSION:
				buildExpressionLevel();
				break;
		}
	}

	/**
	 * Tries to build the Spoon model by considering the given code as a Java expression.
	 */
	private void buildExpressionLevel() {
		buildCode("public class ShowMeYourSpoonCapsule { public Object showmeyourspoonmethod() { return "
				+ code + ";}}", 5);
	}

	/**
	 * Tries to build the Spoon model by considering the given code as a Java statement.
	 */
	private @NotNull CtModel buildStatementLevel() {
		return buildCode("public class ShowMeYourSpoonCapsule { public void showmeyourspoonmethod() {" + code + "}}", 4);
	}

	/**
	 * Tries to build the Spoon model by considering the given code as Java class elements.
	 */
	private @NotNull CtModel buildClassElementLevel() {
		return buildCode("public class ShowMeYourSpoonCapsule { " + code + "}", 2);
	}

	/**
	 * Tries to build the Spoon model by considering the given code as a Java class.
	 */
	private void buildClassLevel() {
		CtModel model = buildCode(code, 1);

		if(model.getRootPackage().getTypes().isEmpty()) {
			model = buildClassElementLevel();
		}

		if(model.getRootPackage().getTypes().isEmpty()) {
			model = buildStatementLevel();
		}

		if(model.getRootPackage().getTypes().isEmpty()) {
			buildExpressionLevel();
		}
	}

	/**
	 * Tries to build a Spoon model.
	 * @param theCode The code to analyse.
	 * @param levelsToIgnore The number of levels not to display (depends on the analysis level).
	 * @return The Spoon model.
	 */
	private @NotNull CtModel buildCode(final String theCode, final int levelsToIgnore) {
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
