package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input;

import infrastructure.persistence.crossscutting.Helper.ObjectHelper;
import infrastructure.persistence.crossscutting.Helper.TextHelper;
import infrastructure.persistence.crossscutting.Helper.UUIDHelper;


import java.util.UUID;

public class RegisterNewStudentInputTO {
    private UUID id;
    private UUID academicProgram;
    private UUID idType;
    private String name;
    private String lastName;
    private String idNumber;
    private String email;
    private String mobileNumber;


    public RegisterNewStudentInputTO(){
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setAcademicProgram(academicProgram, new RegisterNewAcademicProgramInputTO());
        setIdType(idType, new RegisterNewIdTypeInputTo());
        setName(TextHelper.getDefaultWithTrim(name));
        setLastName(TextHelper.getDefaultWithTrim(lastName));
        setIdNumber(TextHelper.getDefaultWithTrim(idNumber));
        setEmail(TextHelper.getDefaultWithTrim(email));
        setMobileNumber(TextHelper.getDefaultWithTrim(mobileNumber));

    }

    public RegisterNewStudentInputTO(final UUID){
        setId(id);
        setAcademicProgram(academicProgram, new RegisterNewAcademicProgramInputTO();
        setIdType(idType, new RegisterNewIdTypeInputTo());
        setName(TextHelper.getDefaultWithTrim(name));
        setLastName(TextHelper.getDefaultWithTrim(lastName));
        setIdNumber(TextHelper.getDefaultWithTrim(idNumber));
        setEmail(TextHelper.getDefaultWithTrim(email));
        setMobileNumber(TextHelper.getDefaultWithTrim(mobileNumber));
    }

    public RegisterNewStudentInputTO(final UUID id, final UUID academicProgram, final UUID idType, final String lastName, final String name, final String idNumber, final String email, final String mobileNumber) {
        super();
        setId(id);
        setAcademicProgram(academicProgram, new RegisterNewAcademicProgramInputTO());
        setIdType(idType, new RegisterNewIdTypeInputTo());
        setName(TextHelper.getDefaultWithTrim(name));
        setLastName(TextHelper.getDefaultWithTrim(lastName));
        setIdNumber(TextHelper.getDefaultWithTrim(idNumber));
        setEmail(TextHelper.getDefaultWithTrim(email));
        setMobileNumber(TextHelper.getDefaultWithTrim(mobileNumber));
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

    private void setId(final UUID id)| {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private  void setAcademicProgram(final UUID academicProgram) {
        this.academicProgram = ObjectHelper.getDefault(academicProgram, new RegisterNewAcademicProgramInputTO());
    }

    private  void setIdType(final UUID idType) {
        this.idType =  ObjectHelper.getDefault(idType, new RegisterNewIdTypeInputTo());
    }
|
    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }

    private void setLastName(final String lastName) {
        this.lastName = TextHelper.getDefaultWithTrim(lastName);
    }

    private void setIdNumber(final String idNumber) {
        this.idNumber = TextHelper.getDefaultWithTrim(idNumber);
    }

    private void setEmail(final String email) {
        this.email = TextHelper.getDefaultWithTrim(email);
    }

    private void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = TextHelper.getDefaultWithTrim(mobileNumber);
    }
}
