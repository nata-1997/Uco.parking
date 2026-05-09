package infrastructure.persistence.entity;

import crossscutting.helper.ObjectHelper;
import crossscutting.helper.EmailHelper;
import crossscutting.helper.TextHelper;
import crossscutting.helper.UUIDHelper;

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

    public  StudentEntity() {
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setAcademicProgramEntity(new AcademicProgramEntity());
        setIdTypeEntity(new IdTypeEntity());
        setName(TextHelper.getDefault());
        setLastName(TextHelper.getDefault());
        setIdNumber(TextHelper.getDefault());
        seteMail(TextHelper.getDefault());
        setMobileNumber(TextHelper.getDefault());
    }

    public StudentEntity(final UUID id) {
        setId(id);
        setAcademicProgramEntity(new AcademicProgramEntity());
        setIdTypeEntity(new IdTypeEntity());
        setName(TextHelper.getDefault());
        setLastName(TextHelper.getDefault());
        setIdNumber(TextHelper.getDefault());
        seteMail(TextHelper.getDefault());
        setMobileNumber(TextHelper.getDefault());

    }
    public StudentEntity(final UUID id, final AcademicProgramEntity academicProgramEntity, final IdTypeEntity idTypeEntity, final String name, final String lastName, final String idNumber, final String eMail, final String mobileNumber) {
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

    private void setId(final UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setAcademicProgramEntity(final AcademicProgramEntity academicProgramEntity) {
        this.academicProgramEntity = ObjectHelper.getDefault(academicProgramEntity, new AcademicProgramEntity());
    }

    private void setIdTypeEntity(final IdTypeEntity idTypeEntity) {
        this.idTypeEntity = ObjectHelper.getDefault(idTypeEntity, new IdTypeEntity());
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }

    private void setLastName(final String lastName) {
        this.lastName = TextHelper.getDefaultWithTrim(lastName);
    }

    private void setIdNumber(String idNumber) {
        this.idNumber = TextHelper.getDefaultWithTrim(idNumber);
    }

    private void seteMail(String eMail) {
        this.eMail = EmailHelper.getdefaultWithvalidation(eMail);
    }

    private void setMobileNumber(String mobileNumber) {
        this.mobileNumber = TextHelper.getDefaultWithTrim(mobileNumber);
    }

}
