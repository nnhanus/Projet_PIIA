import java.nio.file.attribute.UserPrincipal;
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

            statement.executeUpdate(
                  "CREATE TABLE IF NOT EXISTS compte ("
                + "  idu INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  password TEXT NOT NULL,"
                + "  photo TEXT DEFAULT '/avatar/default.png',"
                + "  status INTEGER NOT NULL CHECK (0 <= status AND status <= 3),"
                + "  CHECK (password NOT LIKE '' OR status = 3))"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS video ("
                + "  idv INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  idc INTEGER REFERENCES category(idc) DEFAULT 0,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  path TEXT NOT NULL,"
                + "  mini TEXT DEFAULT '/minia/default.png')"
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
                + "  idc INTEGER REFERENCES video(idc) ON DELETE CASCADE NOT NULL,"
                + "  UNIQUE (idu, idc))"
            );

            statement.executeUpdate(
                "INSERT INTO category(name, status) VALUES ('default', 0)"
            );

            statement.executeUpdate(
                "INSERT INTO category(name, status) VALUES ('concert', 0)"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, mini) VALUES (2, 'Boca', '/Users/noemiehanus/Desktop/forever together/stages/boca.mp4', '/minia/Boca.png')"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Anna', 'hello', '/avatar/default.png', 0)"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Claude', 'coucou', '/avatar/default.png', 0)"
            );
            System.out.println("hello");

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Marie', 'chevre', '/avatar/marie.png',  1)"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Pierre', 'brownie', '/avatar/pierre.png',  1)"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Mathéo', ' ', '/avatar/matheo.png', 2)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (1, 1)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (3, 2)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (2, 1)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (3, 1)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (4, 1)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (5, 1)"
            );


            statement.close();
        }
    }
    

    public static void test() throws SQLException{
        String QUERY = "SELECT idu, name, password, photo, status FROM compte";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);		      
         while(rs.next()){
            System.out.print("ID: " + rs.getInt("idu"));
            System.out.print(", Nom: " + rs.getString("name"));
            System.out.print(", PW: " + rs.getString("password"));
            System.out.println((", Photo: " + rs.getString("photo")));
            System.out.println(", Status: " + rs.getString("status"));
         }
    }
}



