package application.usecase.Specification;

import crossscutting.Specification.Specification;
import crossscutting.helper.ObjectHelper;

public class IsUUIDPresentSpecification extends Specification<Object> {
    @Override
    public boolean isSatisfiedBy(Object object) {
        return !ObjectHelper.isNull(object);
    }
}
