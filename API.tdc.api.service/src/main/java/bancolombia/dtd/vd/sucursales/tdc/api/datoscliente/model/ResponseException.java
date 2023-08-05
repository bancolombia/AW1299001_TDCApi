package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model;

public class ResponseException {
    private String codError;
    private String descError;
    private String descFuncional;
    private String codFuncional;
    private String idSession;
    private String tokenActual;
    private String servicio;
    private String operacion;

    public ResponseException() {
    }

    public String getCodError() {
        return codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }

    public String getDescError() {
        return descError;
    }

    public void setDescError(String descError) {
        this.descError = descError;
    }

    public String getDescFuncional() {
        return descFuncional;
    }

    public void setDescFuncional(String descFuncional) {
        this.descFuncional = descFuncional;
    }

    public String getCodFuncional() {
        return codFuncional;
    }

    public void setCodFuncional(String codFuncional) {
        this.codFuncional = codFuncional;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getTokenActual() {
        return tokenActual;
    }

    public void setTokenActual(String tokenActual) {
        this.tokenActual = tokenActual;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}
