package org.example.calcettomanagmentsystem.navigation;

public enum SQLSchemaNavigator {

	SETUP("/org/example/calcettomanagmentsystem/schemas/setup.sql"),
	TEST_DB("/org/example/calcettomanagmentsystem/schemas/testDB.sql");

    private final String schemaPath;

	SQLSchemaNavigator(String schemaPath) {
		this.schemaPath = schemaPath;
	}

	public String getPath() {
		return schemaPath;
	}

    @Override
    public String toString() {
        return "SQLSchemaNavigation{" +
                "schemaPath='" + schemaPath + '\'' +
                '}';
    }
}
