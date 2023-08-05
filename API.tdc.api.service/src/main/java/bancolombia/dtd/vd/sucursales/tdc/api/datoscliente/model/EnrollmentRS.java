package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model;

public class EnrollmentRS {
    private String dynamicKeyIndicator;
    private String dynamicKeyMechanism;
    private String enrollmentDate;
    private String lastMechanismUpdateDate;
    private String alertType;
    private String customerMobileNumber;
    private String customerEmail;
    private String pushActive;
    private String lastDataModificationDate;

    public EnrollmentRS() {
    }

    public String getDynamicKeyIndicator() {
        return dynamicKeyIndicator;
    }

    public void setDynamicKeyIndicator(String dynamicKeyIndicator) {
        this.dynamicKeyIndicator = dynamicKeyIndicator;
    }

    public String getDynamicKeyMechanism() {
        return dynamicKeyMechanism;
    }

    public void setDynamicKeyMechanism(String dynamicKeyMechanism) {
        this.dynamicKeyMechanism = dynamicKeyMechanism;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getLastMechanismUpdateDate() {
        return lastMechanismUpdateDate;
    }

    public void setLastMechanismUpdateDate(String lastMechanismUpdateDate) {
        this.lastMechanismUpdateDate = lastMechanismUpdateDate;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPushActive() {
        return pushActive;
    }

    public void setPushActive(String pushActive) {
        this.pushActive = pushActive;
    }

    public String getLastDataModificationDate() {
        return lastDataModificationDate;
    }

    public void setLastDataModificationDate(String lastDataModificationDate) {
        this.lastDataModificationDate = lastDataModificationDate;
    }
}
