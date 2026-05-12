package application.usecase.Specification;

import crossscutting.Specification.Specification;
import crossscutting.helper.ObjectHelper;

public class IsStringValuePresentSpecification extends Specification<Object> {
    @Override
    public boolean isSatisfiedBy(Object object) {
        return !ObjectHelper.isNull(object);
    }
}
