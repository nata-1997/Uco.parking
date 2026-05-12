package infrastructure.persistence.sql.entity;

import jakarta.persistence.*;
import crossscutting.helper.EmailHelper;

import java.util.UUID;

@Entity
@Table(name = "Student")
public class StudentJPAEntity {

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

    public StudentJPAEntity() {
        super();
        generateId();

    }

    public StudentJPAEntity(UUID id, AcademicProgramJPAEntity academicProgramEntity, IdTypeJPAEntity idTypeEntity, String name, String lastName, String idNumber, String eMail, String mobileNumber) {
        super();
        generateId();
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
        this.eMail = EmailHelper.getdefaultWithvalidation(eMail);
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
