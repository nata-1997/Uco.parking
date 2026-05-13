package co.edu.uco.ucoparking.application.usecase.specification;

import co.edu.uco.ucoparking.crossscutting.specification.Specification;

public class IsEmailValuePresentSpecification extends Specification<String> {

    private final EmailFormatSpecification emailFormatSpecification = new EmailFormatSpecification();

    @Override
    public boolean isSatisfiedBy(final String email) {
        return emailFormatSpecification.isSatisfiedBy(email);
    }
}
