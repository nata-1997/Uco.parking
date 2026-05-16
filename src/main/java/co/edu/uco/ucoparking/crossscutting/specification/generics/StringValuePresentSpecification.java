package co.edu.uco.ucoparking.crossscutting.specification.generics;

import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.specification.Specification;

public class StringValuePresentSpecification extends Specification<String> {

    @Override
    public boolean isSatisfiedBy(final String value) {
        return !TextHelper.isNullOrWhiteSpace(value);
    }
}
