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
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.CtScanner;

public class BasicVisitor extends CtScanner {
	final boolean hideImplicit;
	final BiConsumer<Integer, String> proc;
	int level;

	public BasicVisitor(final BiConsumer<Integer, String> proc, final boolean hideImplicit) {
		super();
		this.hideImplicit = hideImplicit;
		this.proc = proc;
		level = 0;
	}

	@Override
	public void scan(final CtElement element) {
		// Filtering out implicit elements;
		if(element != null && hideImplicit && element.isImplicit()) {
			return;
		}
		super.scan(element);
	}


	@Override
	protected void enter(final CtElement elt) {
		level++;

		final String label = elt.getClass().getSimpleName().replaceAll("Impl$", "") +
			(elt.isImplicit() ? " (implicit)" : "");

		if(elt instanceof CtType<?>) {
			proc.accept(level, label + ": " + ((CtType<?>) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtNamedElement) {
			proc.accept(level, label + ": " + ((CtNamedElement) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtReference) {
			proc.accept(level, label + ": " + ((CtReference) elt).getSimpleName());
			return;
		}

		if(elt instanceof CtVariableAccess<?>) {
			final CtVariableAccess<?> varaccess = (CtVariableAccess<?>) elt;
			final String txt = ": " + ((varaccess.getVariable() != null) ? varaccess.getVariable().getSimpleName() : "(null)");
			proc.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtTypeAccess<?>) {
			final CtTypeAccess<?> typeaccess = (CtTypeAccess<?>) elt;
			final String txt = ": " + ((typeaccess.getAccessedType() != null) ? typeaccess.getAccessedType().getSimpleName() : "(null)");
			proc.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtLiteral<?>) {
			proc.accept(level, label + ": " + ((CtLiteral<?>) elt).getValue());
			return;
		}

		if(elt instanceof CtAbstractInvocation<?>) {
			final CtAbstractInvocation<?> invoc = (CtAbstractInvocation<?>) elt;
			final String txt = ": " + ((invoc.getExecutable() != null) ? invoc.getExecutable().getSimpleName() : "(null)");
			proc.accept(level, label + txt);
			return;
		}

		if(elt instanceof CtAnnotation<?>) {
			final CtAnnotation<?> annot = (CtAnnotation<?>) elt;
			final String txt = ": " + ((annot.getAnnotationType() != null) ? annot.getAnnotationType().getSimpleName() : "(null)");
			proc.accept(level, label + txt);
			return;
		}

		proc.accept(level, label);
	}

	@Override
	protected void exit(final CtElement e) {
		level--;
		super.exit(e);
	}
}
