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
                + "  idc INTEGER REFERENCES category(idc) DEFAULT 1,"
                + "  name TEXT UNIQUE NOT NULL,"
                + "  path TEXT NOT NULL,"
                + "  descri TEXT,"
                + "  mini TEXT DEFAULT '/minia/default.png')"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS category ("
                + "  idc INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  name TEXT UNIQUE NOT NULL)"
            );

            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS authorization ("
                + "  idu INTEGER REFERENCES user(idu) ON DELETE CASCADE NOT NULL,"
                + "  idc INTEGER REFERENCES video(idc) ON DELETE CASCADE NOT NULL,"
                + "  UNIQUE (idu, idc))"
            );

            statement.executeUpdate(
                "INSERT INTO category(name) VALUES ('Default')"
            );

            statement.executeUpdate(
                "INSERT INTO category(name) VALUES ('Concert')"
            );

            statement.executeUpdate(
                "INSERT INTO category(name) VALUES ('Animation')"
            );

            statement.executeUpdate(
                "INSERT INTO category(name) VALUES ('Cours Physique')"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, descri, mini) VALUES (2, 'Boca [Live]', 'src/video/Boca.mp4', 'Concert Dreamcatcher Boca', '/minia/Boca.png')"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, descri, mini) VALUES (4, 'Cours Physique 17.3.20', 'src/video/20200317CoursPhys103a_Chap5_part0_1_2_zoom_0.mp4', 'Cours physique 17 mars 2020', '/minia/cours1.png')"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, descri, mini) VALUES (4, 'TD Physique 21.3.20', 'src/video/20200321_TD_Phys103a_Chap5_rapide_OBS_hdole.mp4', 'TD physique du 21 mars 2020', '/minia/cours2.png')"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, descri, mini) VALUES (3, 'Stark Raven Mad', 'src/video/Stark Raven Mad _ Ever_After_High.mp4', 'Ever After High, saison 1, épisode 1, en anglais', '/minia/eah.png')"
            );

            statement.executeUpdate(
                "INSERT INTO video(idc, name, path, descri, mini) VALUES (3, 'True Reflections', 'src/video/True Reflections _ Ever After High.mp4', 'Ever After High, saison 1, épisode 2, en anglais', '/minia/eah.png')"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Anna', 'hello', '/avatar/default.png', 0)"
            );

            statement.executeUpdate(
                "INSERT INTO compte(name, password, photo, status) VALUES ('Claude', 'coucou', '/avatar/default.png', 0)"
            );

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

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (5, 3)"
            );

            statement.executeUpdate(
                "INSERT INTO authorization(idu, idc) VALUES  (4, 4)"
            );


            statement.close();
        }
    }
    

    public static void test() throws SQLException{
        /*String QUERY = "SELECT idu, name, password, photo, status FROM compte";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);		      
         while(rs.next()){
            System.out.print("ID: " + rs.getInt("idu"));
            System.out.print(", Nom: " + rs.getString("name"));
            System.out.print(", PW: " + rs.getString("password"));
            System.out.println((", Photo: " + rs.getString("photo")));
            System.out.println(", Status: " + rs.getString("status"));
         }*/
         String QUERY = "SELECT idc, name FROM category";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);		      
         while(rs.next()){
            System.out.print("ID: " + rs.getInt("idc"));
            System.out.println(", Nom: " + rs.getString("name"));
         }
    }
}



