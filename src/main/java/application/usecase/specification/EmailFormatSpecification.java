package application.usecase.specification;

import crossscutting.helper.EmailHelper;
import crossscutting.helper.TextHelper;
import crossscutting.specification.Specification;

public class EmailFormatSpecification extends Specification<String> {

    @Override
    public boolean isSatisfiedBy(final String email) {
        return !TextHelper.isNullOrWhiteSpace(email) && EmailHelper.isValidFormat(email);
    }
}
