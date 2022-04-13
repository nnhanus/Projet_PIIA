public enum Vue {

    COMPTE("fxml/compteslist.fxml"),
    ACCUEIL("fxml/accueil.fxml"),
    MP("fxml/med2.fxml"),
    PASSWORD("fxml/password.fxml"),
    ACCOUNT("fxml/account.fxml"),
    CREATION("fxml/ajoutcompte.fxml"),
    CREAT_ENF("fxml/ajoutEnfant.fxml");

    private String fileName;

    Vue(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
