package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input;

import infrastructure.persistence.crossscutting.Helper.TextHelper;
import infrastructure.persistence.crossscutting.Helper.UUIDHelper;

import java.util.UUID;

public class RegisterNewIdTypeInputTO {
    private UUID id;
    private String name;

    public RegisterNewIdTypeInputTO(final UUID id, final String name) {
        super();
        setId(id);
        setName(name);
    }

    public RegisterNewIdTypeInputTO(final UUID id){
        setId(id);
        setName(TextHelper.getDefault());
    }

    public RegisterNewIdTypeInputTO(){
        setId(UUIDHelper.getUUIDHelper().getDefault());
        setName(TextHelper.getDefault());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setId(final UUID id) {
        this.id = UUIDHelper.getUUIDHelper().getDefault(id);
    }

    private void setName(final String name) {
        this.name = TextHelper.getDefaultWithTrim(name);
    }
}
