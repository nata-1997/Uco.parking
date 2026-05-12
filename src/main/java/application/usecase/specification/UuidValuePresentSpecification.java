package application.usecase.specification;

import crossscutting.helper.ObjectHelper;
import crossscutting.helper.UUIDHelper;
import crossscutting.specification.Specification;

import java.util.UUID;

public class UuidValuePresentSpecification extends Specification<UUID> {

    @Override
    public boolean isSatisfiedBy(final UUID value) {
        return !ObjectHelper.isNull(value) && !UUIDHelper.getUUIDHelper().isDefaultUUID(value);
    }
}
