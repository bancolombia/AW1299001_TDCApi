package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model;

public class EnrollmentRQ {
    private String idSesion;
    private String typeNumber;
    private String idNumber;

    public EnrollmentRQ() {
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
