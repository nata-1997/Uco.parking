package infrastructure.persistence.entity;

import java.util.UUID;

public class StudentEntity {
    private UUID id;
    private AcademicProgramEntity academicProgramEntity;
    private IdTypeEntity idTypeEntity;
    private String name;
    private String lastName;
    private String idNumber;
    private String eMail;
    private String mobileNumber;

    public StudentEntity(UUID id, AcademicProgramEntity academicProgramEntity, IdTypeEntity idTypeEntity, String name, String lastName, String idNumber, String eMail, String mobileNumber) {
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

    public AcademicProgramEntity getAcademicProgramEntity() {
        return academicProgramEntity;
    }

    public IdTypeEntity getIdTypeEntity() {
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
        return eMail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    private void setAcademicProgramEntity(AcademicProgramEntity academicProgramEntity) {
        this.academicProgramEntity = academicProgramEntity;
    }

    private void setIdTypeEntity(IdTypeEntity idTypeEntity) {
        this.idTypeEntity = idTypeEntity;
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

    private void seteMail(String eMail) {
        this.eMail = eMail;
    }

    private void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
