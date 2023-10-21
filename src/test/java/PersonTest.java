import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

class PersonTest {
    static Person person;

    @BeforeAll
    static void initPerson(){
        person = new Person("Oliver", "Martin");
    }

    @Test
    void standardAssertions(){
        Person newer = new Person("who","stranger");
        newer.setAge(37);
        assertNotEquals(newer.getLastName(), person.getLastName());
        assertNotNull(newer.getFirstName());
        assertFalse(newer.getAge() > 40);

    }

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->{int num = 3/0 ;});
        assertEquals("/ by zero", exception.getMessage());
    }


    @Test
    void groupAssertions() {
        person.setAge(32);
        assertAll("person",
                () -> assertEquals("Oliver", person.getFirstName()),
                () -> assertTrue(person.getAge() < 30),
                () -> assertEquals("OliverMartin", person.getFullName())
        );
    }


    @Test
    void dependantAssertions(){
        assertAll("properties",
                () -> {
                        String firstName = person.getFirstName();
                        assertNotNull(firstName);

                        assertAll("first name",
                                ()->assertTrue(firstName.startsWith("O")),
                                ()->assertTrue(firstName.endsWith("r"))
                        );
                      },
                () -> {
                        String lastName = person.getLastName();
                        assertNotNull(lastName);

                        assertAll("last name",
                            ()->assertTrue(lastName.startsWith("M")),
                            ()->assertTrue(lastName.endsWith("n"))
                        );
                     }
        );
    }


}
