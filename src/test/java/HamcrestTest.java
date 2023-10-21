import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import org.hamcrest.collection.IsMapContaining;

class HamcrestTest {

    @Test
    @DisplayName("Writing assertions for String")
    void assertionsForString() {
        String str = "Hamcrest is awesome";

        assertThat(str, startsWith("Ham"));
        assertThat(str, endsWith("some"));
        assertThat(str, containsString("is"));
        assertThat(str, is(equalToIgnoringCase("HAMCREST IS AWESOME")));
        assertThat("Spring", equalToIgnoringCase("spring"));

    }

    @Test
    @DisplayName("Writing assertions for Numbers")
    void assertionsForNumbers() {
        double pi = 3.1415;
        assertThat(pi, is(greaterThan(2.0)));
        assertThat(pi, is(greaterThanOrEqualTo(3.0)));
        assertThat(pi, is(closeTo(3.14, 0.01)));
        assertThat(pi, is(both(greaterThan(1.0)).and(lessThan(4.0))));
        assertThat(pi, is(allOf(greaterThan(1.0), (lessThan(4.0)))));
        assertThat(pi, is(anyOf(greaterThan(2.0), (lessThan(4.0)))));
        assertThat(pi, is(either(greaterThan(2.0)).or(lessThan(4.0))));
    }

    @Test
    @DisplayName("Writing assertions for Collections")
    void assertionsForCollections() {
        Map<String, String> stringMap = new HashMap<String, String>() {{
            put("key1", "ocean");
            put("key2", "nation");
        }};
        assertThat(stringMap, IsMapContaining.hasKey("key1"));
        assertThat(stringMap, IsMapContaining.hasEntry("key1", "ocean"));
        assertThat(stringMap, IsMapContaining.hasValue("nation"));
        assertThat(stringMap.size(), is(2));

        List<String> list1 = Arrays.asList("asia", "europe", "america", "oceania", "africa");
        assertThat(list1, hasItem("asia"));
        assertThat(list1, hasItems("asia", "africa"));
        assertThat(list1, containsInAnyOrder("asia", "africa", "europe", "america", "oceania"));
        assertThat(list1, hasSize(5));

        assertThat(list1, not(IsEmptyCollection.empty()));
        assertThat(new ArrayList<>(), IsEmptyCollection.empty());
    }

    @Test
    @DisplayName("Writing assertions for Object")
    void assertionsForObject() {
        List<Person> heroes = Arrays.asList(new Person("Thor","Odinson"), new Person("Hulk","Banner"), new Person("Widow","Black"), new Person("Strange","Doctor"), new Person("Man","Iron"));
        Person thor = new Person("Thor","Odinson");
        Person godOfThunder = new Person("Thor","Odinson");

        assertThat(thor, instanceOf(Person.class));
        assertThat(thor.getFullName(), equalTo(godOfThunder.getFullName()));
        assertThat(thor, hasProperty("firstName"));

        assertThat(String.format("Neither first name %s, nor last name %s is wrong!",
                thor.getFirstName(), thor.getLastName()),
                thor, anyOf(
                        hasProperty("firstName", is(thor.getFirstName())),
                        hasProperty("full Name", is(thor.getLastName()))
                ));
    }
}
