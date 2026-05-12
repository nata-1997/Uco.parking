package application.usecase.specification;

import crossscutting.specification.Specification;

public class IsEmailValuePresentSpecification extends Specification<String> {

    private final EmailFormatSpecification emailFormatSpecification = new EmailFormatSpecification();

    @Override
    public boolean isSatisfiedBy(final String email) {
        return emailFormatSpecification.isSatisfiedBy(email);
    }
}
