package co.edu.uco.ucoparking.infrastructure.persistence.sql.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "Student")
public class StudentJPAEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew = true;

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "AcademicProgram")
    private AcademicProgramJPAEntity academicProgramEntity;

    @ManyToOne
    @JoinColumn(name = "IdType")
    private IdTypeJPAEntity idTypeEntity;

    @Column(name = "Name")
    private String name;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "IdNumber")
    private String idNumber;

    @Column(name = "eMail")
    private String eMail;

    @Column(name = "MobileNumber")
    private String mobileNumber;

    protected StudentJPAEntity() {
    }

    public StudentJPAEntity(AcademicProgramJPAEntity academicProgramEntity, IdTypeJPAEntity idTypeEntity, String name, String lastName, String idNumber, String eMail, String mobileNumber) {
        super();
        setAcademicProgramEntity(academicProgramEntity);
        setIdTypeEntity(idTypeEntity);
        setName(name);
        setLastName(lastName);
        setIdNumber(idNumber);
        seteMail(eMail);
        setMobileNumber(mobileNumber);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    public void markAsNew() {
        this.isNew = true;
    }

    public AcademicProgramJPAEntity getAcademicProgramEntity() {
        return academicProgramEntity;
    }

    public IdTypeJPAEntity getIdTypeEntity() {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void generateId() {
        this.id = UUID.randomUUID();
    }

    public void setAcademicProgramEntity(AcademicProgramJPAEntity academicProgramEntity) {
        this.academicProgramEntity = academicProgramEntity;
    }

    public void setIdTypeEntity(IdTypeJPAEntity idTypeEntity) {
        this.idTypeEntity = idTypeEntity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
