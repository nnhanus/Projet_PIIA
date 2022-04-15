public enum Vue {

    COMPTE("fxml/compte.fxml"),
    ACCUEIL("fxml/accueil.fxml"),
    MP("fxml/med2.fxml"),
    PASSWORD("fxml/password.fxml"),
    ACCOUNT("fxml/account.fxml"),
    CREATION("fxml/ajoutcompte.fxml"),
    CREAT_ENF("fxml/ajoutEnfant.fxml"),
    SUPPRESSION("fxml/suppression.fxml"),
    VIDINFO("fxml/videoInfo.fxml"),
    CAT_INFO("/fxml/catInfo.fxml");

    private String fileName;

    Vue(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
