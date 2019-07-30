package showmeyourspoon.command;

public enum TreeLevel {
	AUTO {
		@Override
		public String toString() {
			return "Auto";
		}
	},
	CLASS_ELEMENT {
		@Override
		public String toString() {
			return "Class element";
		}
	},
	STATEMENT {
		@Override
		public String toString() {
			return "Statement";
		}
	},
	EXPRESSION {
		@Override
		public String toString() {
			return "Expression";
		}
	}
}
