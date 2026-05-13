package co.edu.uco.ucoparking.infrastructure.persistence.entity;

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

    public StudentEntity(
            final UUID id,
            final UUID academicProgramEntity,
            final UUID idTypeEntity,
            final String name,
            final String lastName,
            final String idNumber,
            final String eMail,
            final String mobileNumber) {
        this.id = id;
        this.academicProgramEntity = academicProgramEntity;
        this.idTypeEntity = idTypeEntity;
        this.name = name;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.email = eMail;
        this.mobileNumber = mobileNumber;
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
        this.id = id;
    }

    public void setAcademicProgramEntity(final UUID academicProgramEntity) {
        this.academicProgramEntity = academicProgramEntity;
    }

    public void setIdTypeEntity(final UUID idTypeEntity) {
        this.idTypeEntity = idTypeEntity;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setIdNumber(final String idNumber) {
        this.idNumber = idNumber;
    }

    public void seteMail(final String eMail) {
        this.email = eMail;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
