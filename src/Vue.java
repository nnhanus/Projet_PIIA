public enum Vue {

    COMPTE("compteslist.fxml"),
    ACCUEIL("accueil.fxml"),
    MP("med2.fxml"),
    PASSWORD("password.fxml"),
    ACCOUNT("account.fxml"),
    CREATION("ajoutcompte.fxml"),
    CREAT_ENF("ajoutEnfant.fxml");

    private String fileName;

    Vue(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
