import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class Video {
    protected static PreparedStatement getVideos; //Récupérer toutes les vidéos
    protected static PreparedStatement getPath; //Récupérer le chemin d'une vidéo
    protected static PreparedStatement getCategories; //Récupérer toutes les catégories
    protected static PreparedStatement getVidForCat; //Récupérer les vidéos d'une catégorie
    protected static PreparedStatement getNomForIDC; //Récupérer le nom à partir de l'id de catégorie
    protected static PreparedStatement getIDCForNom; //Récupérer l'id à partir du nom de catégorie
    protected static PreparedStatement getAuthor; //Récupérer les comptes autoriser à une catégorie
    protected static PreparedStatement getNomForIDU; //Récupérer le nom à partir de l'id d'un compte
    protected static PreparedStatement isAuthor;//Récupérer les autorisations d'un compte
    protected static PreparedStatement getIDUFromNom; //Récupérer l'ID à partir du nom d'un compte
    protected static PreparedStatement supprAuthor; //Supprimer une authorisation
    protected static PreparedStatement ajoutAuthor; //Ajouter une authorisation
    protected static PreparedStatement ajoutVid; //Ajouter une vidéo
    protected static PreparedStatement supprVid; //Supprimer une vidéo
    protected static PreparedStatement ajoutVidCat; //Ajouter une vidéo dans une catégorie
    protected static PreparedStatement ajoutCat; //AJouter une catégorie

    /**Initialisation des requêtes */
    static {
        try {
            getVideos = Database.prepareStatement("SELECT name FROM video");
            getPath = Database.prepareStatement("SELECT path FROM video WHERE name = ?");
			getCategories = Database.prepareStatement("SELECT * FROM category");
			getVidForCat = Database.prepareStatement("SELECT * FROM video WHERE idc = ?");
            getNomForIDC = Database.prepareStatement("SELECT name FROM category WHERE idc = ?");
            getIDCForNom = Database.prepareStatement("SELECT idc FROM category WHERE name = ?");
            getAuthor = Database.prepareStatement("SELECT idu FROM authorization WHERE idc = ?");
            getNomForIDU = Database.prepareStatement("SELECT name FROM compte WHERE idu = ?");
            isAuthor = Database.prepareStatement("SELECT idc FROM authorization WHERE idu = ?");
            getIDUFromNom = Database.prepareStatement("SELECT idu FROM compte WHERE name = ?");
            supprAuthor = Database.prepareStatement("DELETE FROM authorization WHERE idu = ? AND idc = ?");
            ajoutAuthor = Database.prepareStatement("INSERT INTO authorization VALUES (?, ?)");
            supprVid = Database.prepareStatement("DELETE FROM video WHERE name = ?");
            ajoutVid = Database.prepareStatement("INSERT INTO video(name, path, descri) VALUES (?, ?, ?)");
            ajoutVidCat = Database.prepareStatement("INSERT INTO video(idc, name, path, descri) VALUES (?, ?, ?, ?)");
            ajoutCat = Database.prepareStatement("INSERT INTO category(name) VALUES (?)");

        } catch (SQLException e){
            System.out.println("Erreur" + e.getMessage() + e.getCause());
        }
    }

    public static void ajoutVid(String n, String p, String d){
        try {
            ajoutVid.setString(1, n);
            ajoutVid.setString(2, p);
            ajoutVid.setString(3, d);
            ajoutVid.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void ajoutVidCat(String n, String p, String d){
        try {
            ajoutVidCat.setInt(1, getIDCForNom(CatInfoControl.categorie));
            ajoutVidCat.setString(2, n);
            ajoutVidCat.setString(3, p);
            ajoutVidCat.setString(4, d);
            ajoutVidCat.executeUpdate();
        } catch (SQLException e) { System.err.println(e.getMessage()); }
    }

    public static void suppVid(String n){
        try {
            supprVid.setString(1, n);
            supprVid.executeQuery();
        } catch (SQLException e) {}
        
    }

    /**
     * Ajouter une authorisation à un compte
     * @param name le nom du compte
     */
    public static void ajoutAuthor(String name){
        try {
            int idu = getNomForIDU(name); //récupération de l'id du compte
            int idc = getIDCForNom(CatInfoControl.categorie); //récupération de la catégorie du compte
            //complétion de la requête
            ajoutAuthor.setInt(1, idu); 
            ajoutAuthor.setInt(2, idc);
            ajoutAuthor.executeQuery(); //excecution de la requête
        } catch (SQLException e) {}
    }

    /**
     * Supprimer l'authorisation d'un compte
     * @param name le nom du compte
     */
    public static void supprAuthor(String name){
        try {
            int idu = getNomForIDU(name); //récupération de l'id du compte
            int idc = getIDCForNom(CatInfoControl.categorie); //récupération de la catégorie du compte
            //complétion de la requête
            supprAuthor.setInt(1, idu);
            supprAuthor.setInt(2, idc);
            supprAuthor.executeQuery(); //excecution de la requête
        } catch (SQLException e) {}
        

    }

    public static boolean isAuthor(String name, String cat){
        int idu = getNomForIDU(name);
        int idc = getIDCForNom(cat);
        try {
            if (Comptes.getType(name) == 0){
                return true;
            }
            isAuthor.setInt(1, idu);
            ResultSet rs = isAuthor.executeQuery();
            while (rs.next()){
                if(rs.getInt("idc") == idc){
                    return true;
                }
            }  
        } catch (SQLException e) {}
        return false;
    }

    public static ArrayList<String> getCatForUser(){
        ArrayList<String> res = new ArrayList<>();
        try {
            if (Comptes.getType(CompteControl.id) == 0){ //les parents ont accès à toutes les catégories
                return getCategories();
            } else {
                ArrayList<String> cat = getCategories();
                for (String s : cat){
                    if (isAuthor(CompteControl.id, s)){
                        res.add(s);
                    }
                }
            }
        } catch (SQLException e){}
        return res;

    }

    /**
     * Récupération des comptes non-autorisés à une catégorie
     * @param cat la catégorie
     * @return les comptes non-autorisés
     */
    public static ArrayList<String> getNonAuthor(String cat){
        ArrayList<String> res = new ArrayList<>();
        try {
            ArrayList<String> comptes = Comptes.getUsers(); //récupérer les comptes
            for (int i = 0; i < comptes.size(); i++){ //parcours des comptes
                String nom = comptes.get(i); 
                if (!(isAuthor(nom, cat)) && !(Comptes.getType(nom)==0)){ //vérification de l'autorisations
                    res.add(nom); // si pas d'autorisation, alors ajout du compte au résultat
                }
            }
        } catch (SQLException e){}
        return res;
    }

    /**
     * Récupérer l'id' à partir d'un id de catégorie
     * @param name le nom de la catégorie
     * @return l'id de la catégorie
     */
    public static int getIDCForNom(String name){
        int res = -1;
        try {
            getIDCForNom.setString(1, name); //completion de la requête
            ResultSet rs = getIDCForNom.executeQuery(); //excécution et récupération des résultats de la requête
            res = rs.getInt("idc"); // récuperation de l'idc
        } catch (SQLException e) {}
        return res;
    }

    public static String getNomForIDC(int id){
        String res = "";
        try {
            getNomForIDC.setInt(1, id); //completion de la requête
            ResultSet rs = getIDCForNom.executeQuery(); //excécution et récupération des résultats de la requête
            res = rs.getString("name"); // récuperation de l'idc
        } catch (SQLException e) {}
        return res;
    }

    /**
     * Récupérer le nom à partir de l'id d'un compte
     * @param name le nom du compte
     * @return l'id du compte
     */
    public static int getNomForIDU(String name){
        int res = -1;
        try {
            getIDUFromNom.setString(1, name); //completion de la requête
            ResultSet rs = getIDUFromNom.executeQuery(); //excécution et récupération des résultats de la requête
            res = rs.getInt("idu"); //récuperation de l'idu
        } catch (SQLException e) {}
        return res;
    }

    /**
     * Récupération des comptes autorisés à la catégorie
     * @param name de la catégorie
     * @return la liste des utilisateurs autorisés
     */
    public static ArrayList<String> getUsersAutho(String name){
        int idc = getIDCForNom(name); //récupération du nom de la catégorie 
        ArrayList<String> res = new ArrayList<>();
        try{
            //Récupérer les comptes autoriser à une catégorie
            getAuthor.setInt(1, idc); //complétion de la requête
            ResultSet rs = getAuthor.executeQuery(); //excécution de la requête
            while(rs.next()){ 
                //Récupération du nom des utilisateurs
                getNomForIDU.setInt(1, rs.getInt("idu")); //complétion de la requête
                ResultSet  nom = getNomForIDU.executeQuery(); //excécution de la requête
                while (nom.next()){
                    res.add(nom.getString("name")); //Ajout du nom au résultat
                }
            } 
        } catch (SQLException e){}

        return res;
    }

   /**
    * Récupérer les vidéos d'une catégorie
    * @param s le nom de la catégorie
    * @return les vidéos appartenant à la catégorie
    */
    public static ArrayList<String> getVidFroCat(String s){
        ArrayList<String> res = new ArrayList<>();
        try{
            //Récupération de l'id de la catégorie
            getIDCForNom.setString(1, s);
            ResultSet idc = getIDCForNom.executeQuery();
            //Récupération des vidéos de la catégorie
            getVidForCat.setInt(1, idc.getInt("idc"));
            ResultSet rs = getVidForCat.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name")); //ajout au résultat
            }
        } catch (SQLException e){}
        return res;  
    }

    public static ArrayList<String> getCategories(){
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = getCategories.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
        } catch (SQLException e){}
        return res;  
    }

    public static ArrayList<String> getVideos(){
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = getVideos.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
        } catch (SQLException e){}
        return res;
    }

    /**
     * Récupération du chemin d'une vidéo
     * @param nom de la vidéo
     * @return le chemin
     */
    public static String getPath(String nom) {
        try{
            getPath.setString(1, nom); //complétion de la requête
            ResultSet rs = getPath.executeQuery(); //excécution de la requête
            return rs.getString("path");
        } catch (SQLException e){
            return "";
        }
    }
    
}
