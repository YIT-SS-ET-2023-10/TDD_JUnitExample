import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

class CombineAssertionTest{
    private Person person;
    private static final String FIRST_NAME = "마음";
    private static final String LAST_NAME = "고";

    @BeforeEach
    void createPerson(){
        person = new Person(FIRST_NAME, LAST_NAME);

    }

    @Test
    @DisplayName("Should have correct first name or last name")
    void shouldHaveCorrectFirstNameOrLastName(){
        assertThat(String.format("Neither first name %s, nor last name %s is wrong!",
                FIRST_NAME, LAST_NAME),
                person, anyOf(
                        hasProperty("firstName", is(FIRST_NAME)),
                        hasProperty("full Name", is(LAST_NAME))

                ));
    }

    @Test
    @DisplayName("Should have correct full name")
    void shouldHaveCorrectName(){
        assertThat(String.format("Neither first name %s, nor last name %s is wrong!",
                FIRST_NAME, LAST_NAME),
                person, allOf(
                        hasProperty("firstName", is(FIRST_NAME)),
                        hasProperty("lastName", is(LAST_NAME))

                ));
    }
}
