package co.edu.uco.ucoparking.application.usecase.specification;

import co.edu.uco.ucoparking.crossscutting.helper.EmailHelper;
import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.specification.Specification;

public class EmailFormatSpecification extends Specification<String> {

    @Override
    public boolean isSatisfiedBy(final String email) {
        return !TextHelper.isNullOrWhiteSpace(email) && EmailHelper.isValidFormat(email);
    }
}
