package application.usecase.specification;

import crossscutting.specification.Specification;
import crossscutting.helper.TextHelper;

public class StringFormatValuelsValidSpecification extends Specification<String> {

    private String pattern;
    private boolean mustApplyTrim;

    public StringFormatValuelsValidSpecification(String pattern, boolean mustApplyTrim) {
        super();
        this.pattern = pattern;
        this.mustApplyTrim = mustApplyTrim;
    }

    @Override
    public boolean isSatisfiedBy(String string) {
        return TextHelper.formatIsValid(string, pattern, mustApplyTrim);
    }

}
