import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.*;

public class AssumptionsTest {
    
    @ParameterizedTest
    @ValueSource(strings={"지수", "제니", "로제", "리사"})
    void JennieTest(String name){
        Assumptions.assumeTrue(name.equals("제니") || name.equals("지수"));
        System.out.println(name);
    }
    
    @Test
    void testCallJennieTest(){
        String name = "지수";
        Assumptions.assumeTrue(name.equals("제니") || name.equals("지수"));
        System.out.println(name);
        assertTrue("제니".equals(name));

//        Assumptions.assumeTrue(name.equals("제니"));
        System.out.println(name);
        assertEquals("제니", name);

    }
    
    private final Person person = new Person("Harris","Ford");
    @Test
    void testAssumeTrueFalse() {
        assumeTrue(person.getAge() == 0);
        assumeTrue((4 == 4),"Fail : assumeTrue Test!!!");
        assumeFalse((person.getLastName() == "Ford"),"Fail : assumeFalse Test!!!");

    }

    @Test
    void testOnlyOnDeveloperWorkstation() {
        assumeTrue("WINOWS".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
    }

    @Test
    void testInAllEnvironments() {

        assumingThat("WINDOWS".equals("WINDOWS"),
                () -> {
                    System.out.println("WiNDOWS : " + person.getFullName().toString());
                    assertEquals(11, person.getFullName().length());
                });
        assertEquals(11, person.getFullName().length());
        assumingThat("WINDOWS".equals("WDOWS"),
                () -> {
                    System.out.println("WDOWS : " + person.getFullName().toString());
                });

        System.out.println(person.getFirstName().toString());
        System.out.println(person.getLastName().toString());

    }
}
