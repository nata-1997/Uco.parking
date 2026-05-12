package infrastructure.persistence.entity;

import crossscutting.helper.EmailHelper;
import crossscutting.helper.TextHelper;
import crossscutting.helper.UUIDHelper;

import java.util.UUID;

public class StudentEntity {
    private UUID id;
    private UUID academicProgramEntity;
    private UUID idTypeEntity;
    private String name;
    private String lastName;
    private String idNumber;
    private String email;
    private String mobileNumber;

    public StudentEntity(final UUID id, final UUID academicProgramEntity, final UUID idTypeEntity, final String name, final String lastName, final String idNumber, final String eMail, final String mobileNumber) {
        super();
        setId(id);
        setAcademicProgramEntity(academicProgramEntity);
        setIdTypeEntity(idTypeEntity);
        setName(name);
        setLastName(lastName);
        setIdNumber(idNumber);
        seteMail(eMail);
        setMobileNumber(mobileNumber);
    }

    public UUID getId() {
        return id;
    }

    public UUID getAcademicProgramEntity() {
        return academicProgramEntity;
    }

    public UUID getIdTypeEntity() {
        return idTypeEntity;
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

    public String geteMail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setId(final UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    public void setAcademicProgramEntity(final UUID academicProgramEntity) {
        this.academicProgramEntity = academicProgramEntity;
    }

    public void setIdTypeEntity(final UUID idTypeEntity) {
        this.idTypeEntity =  idTypeEntity;
    }

    public void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }

    public void setLastName(final String lastName) {
        this.lastName = TextHelper.getDefaultWithTrim(lastName);
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = TextHelper.getDefaultWithTrim(idNumber);
    }

    public void seteMail(String eMail) {
        this.email = EmailHelper.getdefaultWithvalidation(eMail);
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = TextHelper.getDefaultWithTrim(mobileNumber);
    }

}
