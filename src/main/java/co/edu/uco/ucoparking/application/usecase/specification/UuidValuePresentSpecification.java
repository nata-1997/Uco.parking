package co.edu.uco.ucoparking.application.usecase.specification;

import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;
import co.edu.uco.ucoparking.crossscutting.helper.UUIDHelper;
import co.edu.uco.ucoparking.crossscutting.specification.Specification;

import java.util.UUID;

public class UuidValuePresentSpecification extends Specification<UUID> {

    @Override
    public boolean isSatisfiedBy(final UUID value) {
        return !ObjectHelper.isNull(value) && !UUIDHelper.getUUIDHelper().isDefaultUUID(value);
    }
}
