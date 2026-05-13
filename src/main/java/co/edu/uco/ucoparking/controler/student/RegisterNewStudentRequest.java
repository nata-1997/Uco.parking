package co.edu.uco.ucoparking.controler.student;

import java.util.UUID;

public class RegisterNewStudentRequest {

    private UUID academicProgram;
    private UUID idType;
    private String name;
    private String lastName;
    private String idNumber;
    private String email;
    private String mobileNumber;

    public RegisterNewStudentRequest() {
    }

    public UUID getAcademicProgram() {
        return academicProgram;
    }

    public void setAcademicProgram(final UUID academicProgram) {
        this.academicProgram = academicProgram;
    }

    public UUID getIdType() {
        return idType;
    }

    public void setIdType(final UUID idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(final String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
