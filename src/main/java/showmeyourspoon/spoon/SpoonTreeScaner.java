package showmeyourspoon.spoon;

import java.util.function.BiConsumer;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.CtScanner;

public class SpoonTreeScaner extends CtScanner {
	final boolean hideImplicit;
	final BiConsumer<Integer, String> printer;
	/** The current deepness level. */
	int level;
	/** the role of the current element. May be null. */
	CtRole currRole;

	/**
	 * @param printer The printer that is in charge of showing the Spoon tree.
	 * The first argument (integer), is the deepness of the current element in the tree structure.
	 * The second argument (string), is the label of the current element to display.
	 * @param hideImplicit If true, the scanner will ignore implicit elements
	 */
	public SpoonTreeScaner(final BiConsumer<Integer, String> printer, final boolean hideImplicit) {
		super();
		this.hideImplicit = hideImplicit;
		this.printer = printer;
		level = 0;
	}

	@Override
	public void scan(final CtRole role, final CtElement element) {
		// Filtering out implicit elements;
		if(element != null && hideImplicit && element.isImplicit()) {
			return;
		}

		currRole = role;
		super.scan(role, element);
		currRole = null;
	}


	@Override
	protected void enter(final CtElement elt) {
		level++;

		// Removing the trail Impl. Make the assumption that any implementation class
		// has its interface (same name without the trailing 'Impl'
		String label = elt.getClass().getSimpleName().replaceAll("Impl$", "");

		if(elt.isImplicit()) {
			label += " (implicit)";
		}

		if(currRole != null) {
			label += " (role: " + currRole + ")";
		}

		if(elt instanceof CtType<?>) {
			printer.accept(level, label + ": " + ((CtType<?>) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtNamedElement) {
			printer.accept(level, label + ": " + ((CtNamedElement) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtReference) {
			printer.accept(level, label + ": " + ((CtReference) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtVariableAccess<?>) {
			final CtVariableAccess<?> varaccess = (CtVariableAccess<?>) elt;
			final String txt = ": " + ((varaccess.getVariable() != null) ? varaccess.getVariable().getSimpleName() : "(null)");
			printer.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtTypeAccess<?>) {
			final CtTypeAccess<?> typeaccess = (CtTypeAccess<?>) elt;
			final String txt = ": " + ((typeaccess.getAccessedType() != null) ? typeaccess.getAccessedType().getSimpleName() : "(null)");
			printer.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtLiteral<?>) {
			printer.accept(level, label + ": " + ((CtLiteral<?>) elt).getValue());
			return;
		}

		if(elt instanceof CtAbstractInvocation<?>) {
			final CtAbstractInvocation<?> invoc = (CtAbstractInvocation<?>) elt;
			final String txt = ": " + ((invoc.getExecutable() != null) ? invoc.getExecutable().getSimpleName() : "(null)");
			printer.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtAnnotation<?>) {
			final CtAnnotation<?> annot = (CtAnnotation<?>) elt;
			final String txt = ": " + ((annot.getAnnotationType() != null) ? annot.getAnnotationType().getSimpleName() : "(null)");
			printer.accept(level, label + txt);
			return;
		}

		printer.accept(level, label);
	}

	@Override
	protected void exit(final CtElement e) {
		level--;
		super.exit(e);
	}
}
