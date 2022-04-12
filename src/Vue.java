public enum Vue {

    COMPTE("compte.fxml"),
    ACCUEIL("accueil.fxml"),
    MP("med2.fxml"),
    PASSWORD("password.fxml");

    private String fileName;

    Vue(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
