import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTestUsingHelloWorld {
    @BeforeAll
    static void beforeAllPrint(){
        System.out.println("Before All---");
    }

    @BeforeEach
    void beforeEachPrint(){
        System.out.println(" test is started~~~ ");
    }

    @AfterAll
    static void AfterAllPrint(){
        System.out.println(".........All Ends...");
    }

    @AfterEach
    void afterEachPrint(){
        System.out.println(" test ends!");
    }


    @Order(1)
    @Test
    void printNumTest() {
        System.out.println("main test");
        assertEquals("Hello 2", new HelloWorld().printNum(2));
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(ints = {6, 1, 5, 2, 3, 4})
    void printNumTest(int num) {
        HelloWorld hello = new HelloWorld();
        assumeTrue(hello.isGreater(num, 3),num +" is not greater than 3");
        assertTrue(hello.printNum(num).contains("Hello"));
    }

    @Order(6)
    @Test
    void printHelloWorldTest() {
        HelloWorld.printHelloWorld(new String[0]);
    }


    @Order(3)
    @ParameterizedTest
    @ValueSource(ints={1,2,3})
    void test1(int num){
        System.out.println("test 1 : "+ num + "th test!!");
    }

    @Order(5)
    @Test
    void test2(){
        System.out.println("test2!!");
        assertEquals("HelloWorld", new HelloWorld().getClassName());
    }

    @Order(4)
    @Test
    void test3(){
        System.out.println("test3!!");
    }

}

