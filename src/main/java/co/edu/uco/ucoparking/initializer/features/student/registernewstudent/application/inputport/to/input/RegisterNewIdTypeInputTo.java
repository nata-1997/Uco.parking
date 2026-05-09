package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input;

import infrastructure.persistence.crossscutting.Helper.TextHelper;
import infrastructure.persistence.crossscutting.Helper.UUIDHelper;

import java.util.UUID;

public class RegisterNewIdTypeInputTo {
    private UUID id;
    private String name;

    public RegisterNewIdTypeInputTo(final UUID id, final String name) {
        setId(id);
        setName(name);
    }

    public RegisterNewIdTypeInputTo(final UUID id){
        setId(id);
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
