package application.usecase.specification;

import crossscutting.specification.Specification;
import crossscutting.helper.ObjectHelper;

public class IsMandatorySpecification extends Specification<Object> {
    @Override
    public boolean isSatisfiedBy(Object object) {
        return !ObjectHelper.isNull(object);
    }
}
