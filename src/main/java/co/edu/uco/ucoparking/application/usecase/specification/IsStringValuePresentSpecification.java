package co.edu.uco.ucoparking.application.usecase.specification;

import co.edu.uco.ucoparking.crossscutting.specification.Specification;
import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;

public class IsStringValuePresentSpecification extends Specification<Object> {
    @Override
    public boolean isSatisfiedBy(Object object) {
        return !ObjectHelper.isNull(object);
    }
}
