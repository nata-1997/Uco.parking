package co.edu.uco.ucoparking.application.usecase.specification;

import co.edu.uco.ucoparking.crossscutting.specification.Specification;
import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;

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
