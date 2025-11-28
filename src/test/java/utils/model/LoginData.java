package utils.model;

public class LoginData {
    private String username;
    private String password;
    private String tipoResultado;
    private String descripcion;
    private String idCaso;
    private int repeticiones;

    public LoginData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(String tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
