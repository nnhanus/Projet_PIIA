
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    private static Connection con = null;
    private static PreparedStatement tableExistsStatement;
    private static String path = "src/database.db";

    public static boolean open() {
        return open(path);
    }

    public static boolean open(String databaseFile) {
        try {
            if (isValid()) {
                con.close();
            }

            con = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);

            if (tableExistsStatement == null) {
                tableExistsStatement = con
                        .prepareStatement("SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?");
            }

            initialize();

            return true;
        } catch (SQLException e) {
            System.err.printf("ERROR: failed to open database '%s' (%s)\n", databaseFile, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void close() {
        try {
            con.close();
            System.out.println("close");
        } catch (SQLException e) {

        }
    }

    public static boolean isValid() {
        try {
            return con != null && con.isValid(0);
        } catch (SQLException e) {
            // n'est jamais atteint car timeout >= 0
            return false;
        }
    }


    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return con.prepareStatement(sql);
    }

    /**
     * voir {@link java.sql.Connection#createStatement()}
     * 
     * @return
     * @throws SQLException
     */
    public static Statement createStatement() throws SQLException {
        return con.createStatement();
    }

    /**
     * Vérifie qu'une table existe dans la base de données
     * 
     * @param table
     *            le nom de la table à chercher
     * @return true si la table existe, false sinon
     * @throws SQLException
     */
    private static boolean checkTable(String table) throws SQLException {
        tableExistsStatement.setString(1, table);
        ResultSet rs = tableExistsStatement.executeQuery();

        boolean exists = rs.next();
        rs.close();
        tableExistsStatement.clearParameters();

        return exists;
    }

    private static void initialize() throws SQLException {
        // si la table authorization n'existe pas, on considère que la base de donnée n'a pas été initialisée
       if (!checkTable("authorization")) {
            Statement statement = con.createStatement();

            // @formatter:off

            statement.executeUpdate(
                  "CREATE TABLE IF NOT EXISTS compte ("
                + "  idu INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  password TEXT NOT NULL,"
                + "  status INTEGER NOT NULL CHECK (0 <= status AND status <= 3),"
                + "  CHECK (password NOT LIKE '' OR status = 3))"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS video ("
                + "  idv INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  idc INTEGER REFERENCES category(idc) NOT NULL,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  path TEXT NOT NULL)"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS category ("
                + "  idc INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  status INTEGER NOT NULL CHECK (0 <= status AND status <= 3))"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS authorization ("
                + "  idu INTEGER REFERENCES user(idu) ON DELETE CASCADE NOT NULL,"
                + "  idv INTEGER REFERENCES video(idv) ON DELETE CASCADE NOT NULL,"
                + "  UNIQUE (idu, idv))"
            );
            
            // @formatter:on

            statement.close();
        }
    }
    

    public static void test() throws SQLException{
        Statement stmt = con.createStatement();		      
         // Execute a query
         System.out.println("Inserting records into the table...");          
         String sql = "INSERT INTO compte(name, password, status) VALUES ('Mathéo', ' ', 2)";
         stmt.executeUpdate(sql);
        /* String QUERY = "SELECT idu, name, password, status FROM compte";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);		      
         while(rs.next()){
            //Display values
            System.out.print("ID: " + rs.getInt("idu"));
            System.out.print(", Nom: " + rs.getString("name"));
            System.out.print(", PW: " + rs.getString("password"));
            System.out.println(", Status: " + rs.getString("status"));
         }*/
    }
}



