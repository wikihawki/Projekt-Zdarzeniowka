import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseEngine {
	public static final String DATABASE_DRIVER = "org.sqlite.JDBC";
	public static final String DATABASE_FILE = "jdbc:sqlite:src/baza.db";
	protected Connection connection;
	protected Statement statement;

	public DatabaseEngine() {
		loadDriver();
		connectDatabase();
	}

	public Statement getStatement() {
		return statement;
	}

	public Connection getConnection() {
		return connection;
	}

	private void loadDriver() {
		// Uruchomienie sterownika bazy
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC!");
			e.printStackTrace();
		}
	}

	private void connectDatabase() {
		// Próba po³¹czenia siê z baz¹ :)
		try {
			connection = DriverManager.getConnection(DATABASE_FILE);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Problem z otwarciem polaczenia");
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		// Zamykanie polaczenia
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Problem z zamknieciem polaczenia");
			e.printStackTrace();
		}
	}

	public void dispose() {
		closeConnection();
	}

}