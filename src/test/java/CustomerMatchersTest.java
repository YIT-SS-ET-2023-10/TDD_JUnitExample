import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

class CustomerMatchersTest{

    @Test
    void givenAnEvenInteger_whenDivisibleByTwo_thenCorrect(){
        Integer ten = 10;
        Integer five = 5;

        assertThat(ten, is(IsDivisibleBy.divisibleBy(five)));
    }

    @Test
    void givenAnOddInteger_whenNotDivisibleByTwo_thenCorrect(){
        Integer eleven = 11;
        Integer five = 5;

        assertThat(eleven, is(not(IsDivisibleBy.divisibleBy(five))));
    }
}

class IsDivisibleBy extends TypeSafeMatcher<Integer> {
    int value;

    @Override
    protected boolean matchesSafely(Integer item) {
        return item % value == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Can not divided by " + value);

    }

    static Matcher<Integer> divisibleBy(Integer divider) {
        IsDivisibleBy matcher = new IsDivisibleBy();
        matcher.value = divider;
        return matcher;
    }
}