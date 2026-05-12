package application.usecase.Specification;

import crossscutting.Specification.Specification;
import crossscutting.helper.TextHelper;

public class StringLengthSpecification  extends Specification<String> {

    private final int min;
    private final int max;
    private final Boolean mustApplyTrim;

    public StringLengthSpecification(int min, int max, Boolean mustApplyTrim) {
        super();
        this.min = min;
        this.max = max;
        this.mustApplyTrim = mustApplyTrim;
    }
    @Override
    public boolean isSatisfiedBy(final String string) {
        return TextHelper.lengthIsValid(string, min, max, mustApplyTrim);
    }
}
