package application.usecase.specification;

import crossscutting.specification.Specification;
import crossscutting.helper.EmailHelper;

public class IsEmailValuePresentSpecification extends Specification<String> {

    public IsEmailValuePresentSpecification() {
        super();
    }

    @Override
    public boolean isSatisfiedBy(String email) {
        return !EmailHelper.getdefaultWithvalidation(email).equals(EmailHelper.getDefault());
    }
}
