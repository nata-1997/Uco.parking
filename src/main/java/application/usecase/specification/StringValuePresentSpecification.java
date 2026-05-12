package application.usecase.specification;

import crossscutting.helper.TextHelper;
import crossscutting.specification.Specification;

public class StringValuePresentSpecification extends Specification<String> {

    @Override
    public boolean isSatisfiedBy(final String value) {
        return !TextHelper.isNullOrWhiteSpace(value);
    }
}
