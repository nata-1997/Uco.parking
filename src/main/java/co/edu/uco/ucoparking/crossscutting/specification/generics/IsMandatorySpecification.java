package co.edu.uco.ucoparking.crossscutting.specification.generics;

import co.edu.uco.ucoparking.crossscutting.specification.Specification;
import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;

public class IsMandatorySpecification extends Specification<Object> {
    @Override
    public boolean isSatisfiedBy(Object object) {
        return !ObjectHelper.isNull(object);
    }
}
