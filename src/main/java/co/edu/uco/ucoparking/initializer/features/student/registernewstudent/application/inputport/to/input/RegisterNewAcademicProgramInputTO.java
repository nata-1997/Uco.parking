package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input;

import infrastructure.persistence.crossscutting.Helper.ObjectHelper;
import infrastructure.persistence.crossscutting.Helper.TextHelper;
import infrastructure.persistence.crossscutting.Helper.UUIDHelper;

import java.util.UUID;

public class RegisterNewAcademicProgramInputTO {

    private UUID id;
    private UUID institute;
    private String name;

    public RegisterNewAcademicProgramInputTO() {
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setInstitute(new RegisterNewInstituteInputTO());
        setName(TextHelper.getDefaultWithTrim(name));
    }

    public RegisterNewAcademicProgramInputTO(final UUID id) {
        setId(id);
        setInstitute(new RegisterNewInstituteInputTO());
        setName(TextHelper.getDefaultWithTrim(name));
    }

    public RegisterNewAcademicProgramInputTO(final UUID id, final  UUID institute, final String name) {
        super();
        setId(id);
        setInstitute(institute, new RegisterNewInstituteInputTO());
        setName(TextHelper.getDefaultWithTrim(name));
    }

    public UUID getId() {
        return id;
    }

    public UUID getInstitute() {
        return institute;
    }

    public String getName() {
        return name;
    }

    private void setId(final UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setInstitute(final UUID institute) {
        this.institute = ObjectHelper.getDefault(institute, new RegisterNewInstituteInputTO());
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name) ;
    }
}
