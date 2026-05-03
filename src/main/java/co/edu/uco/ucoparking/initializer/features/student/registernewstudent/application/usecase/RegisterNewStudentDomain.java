package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase;

import java.util.UUID;

public class RegisterNewStudentDomain {
    private UUID id;
    private UUID academicProgram;
    private UUID idType;
    private String name;
    private String lastName;
    private String idNumber;
    private String email;
    private String mobileNumber;

    public RegisterNewStudentDomain(UUID academicProgram, UUID idType, String name, String lastName, String idNumber, String email, String mobileNumber) {
        generateId();
        setAcademicProgram(academicProgram);
        setIdType(idType);
        setName(name);
        setLastName(lastName);
        setIdNumber(idNumber);
        setEmail(email);
        setMobileNumber(mobileNumber);

        //¿cómo garantizar que el objeto de dominio se cree de forma integral validado a nivel de tipo de dato, longitud, obligatoriedad, formato, rango, sobre cada uno de los atributos involucrados//
        //selfvalidation//
        //Pistas//
        //Validation pattern//
        //Rule patern//
        //Especification patteern//<--The best//
        //Custom exceptions//

        //Clean code//
    }

    public UUID getId() {
        return id;
    }

    public UUID getAcademicProgram() {
        return academicProgram;
    }

    public UUID getIdType() {
        return idType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    private void generateId() {
        this.id = UUID.randomUUID();
    }

    private void setAcademicProgram(UUID academicProgram) {
        this.academicProgram = academicProgram;
    }

    private void setIdType(UUID idType) {
        this.idType = idType;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
