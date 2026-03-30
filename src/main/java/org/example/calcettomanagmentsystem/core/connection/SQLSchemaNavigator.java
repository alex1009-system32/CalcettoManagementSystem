package org.example.calcettomanagmentsystem.core.connection;

/**
 * Central registry for SQL schema resource paths.
 * <p>
 * This enumeration identifies the SQL script locations used for 
 * database initialization and testing within the application.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public enum SQLSchemaNavigator {
	/** Primary SQL script for setting up the production database schema. */
	SETUP("/org/example/calcettomanagmentsystem/schemas/setup.sql"),
	/** SQL script for initializing a test database with mock data. */
	TEST_DB("/org/example/calcettomanagmentsystem/schemas/testDB.sql");

	/** The internal resource path to the SQL file. */
	private final String schemaPath;

	/**
	 * Constructs a navigator entry with the specified path.
	 *
	 * @param schemaPath The resource path to the SQL script file.
	 */
	SQLSchemaNavigator(String schemaPath) {
		this.schemaPath = schemaPath;
	}

	/**
	 * Retrieves the resource path for this navigator entry.
	 *
	 * @return The SQL file path as a {@link String}.
	 */
	public String getPath() {
		return schemaPath;
	}

	/**
	 * Provides a string representation of the schema location.
	 *
	 * @return A descriptive string containing the schema path.
	 */
	@Override
	public String toString() {
        return "SQLSchemaNavigation{" +
                "schemaPath='" + schemaPath + '\'' +
                '}';
    }
}
