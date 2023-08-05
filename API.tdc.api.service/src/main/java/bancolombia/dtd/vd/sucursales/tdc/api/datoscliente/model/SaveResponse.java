package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model;

public class SaveResponse {

    private String code;
    private boolean isSuccess;

    public SaveResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
