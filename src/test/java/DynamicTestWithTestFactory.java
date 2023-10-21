import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DynamicTestWithTestFactory {

    //This will result in a JUnitException
    @TestFactory
    List<String> dynamicTestsWithInvalidReturnType(){
        return Arrays.asList("Hello");
    }

    @TestFactory
    DynamicTest singleDynamicTest() {
        return dynamicTest("Single dynamic test",
                () -> {
            double number = Math.random();
            assertTrue(number > 0);
                });
    }

    @TestFactory
    DynamicContainer dynamicTestsFromStream() {
        return dynamicContainer("DynamicContainer",
                Stream.of(
                        dynamicTest("1st container test",
                                () -> assertTrue(Math.abs(-13)==13)),
                        dynamicTest("2nd container test",
                                () -> assertEquals(5, Math.floorDiv(25,5)))
                        ));
    }

    @TestFactory
    Stream<DynamicNode> dynamicTestsFromCollection() {
        return Stream.of(7, 13)
                .map(number -> dynamicContainer("greater than zero or Odd Test"+number,
                        Stream.of(
                                dynamicTest("is number "+number+" greater than zero?",
                                        () -> assertTrue(Math.max(number, 0) == number)),
                                dynamicTest("is number "+number+" odd?",
                                        () -> assertFalse((number ^ 1) == 1)
                                ))));
    }


    @TestFactory
    Collection<DynamicTest> dynamicTestsFromCollectionUsingParameters() {
        List<DynamicTest> dynamicTests = new ArrayList<DynamicTest>();

        Arrays.asList(7, 13, 17)
                .forEach(number -> {
                    dynamicTests.add(
                            dynamicTest("is number "+number+" greater than one?",
                                    () -> assertTrue(number > 1)));

                    dynamicTests.add(
                            dynamicTest("is number "+number+" even?",
                                    () -> assertFalse((number ^ 1) == 1)));
                });

        return dynamicTests;
    }


    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(
                dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),
                dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }

    @Test
    void testMath(){
        assertAll(
                () -> assertEquals(2, Math.addExact(1, 1)),
                () -> assertEquals(4, Math.multiplyExact(2, 2))
        );

    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromIntStream() {
        return IntStream.iterate(0, n -> n + 2).limit(10)
                .mapToObj(n -> dynamicTest("test" + n,
                        () -> assertTrue(n % 2 == 0)));
    }

    @ParameterizedTest
    @MethodSource
    void testEven(int number){
        assertTrue(number % 2 == 0);
    }

    IntStream testEven(){
        return IntStream.iterate(0, n -> n + 2).limit(10);
    }

    @ParameterizedTest
    @ValueSource(ints={0,1,2,3,4})
    void islessThanFive(int number){
        assertTrue(number<5);
    }

    @TestFactory
    Stream<DynamicTest> isLessThanUsingDynamicTest(){
        List<Integer> numbers = getNumbersFromDAO();
        return numbers.stream()
                .map(num-> dynamicTest(num + " is less than 5",
                            () -> assertTrue(num < 5)
                ));
    }

    private List<Integer> getNumbersFromDAO() {
        return Arrays.asList(0,1,2,3,4);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsForEmployeeWorkflows() {
        List<Employee> inputList = Arrays.asList(
                new Employee(1, "Fred"), new Employee(2), new Employee(3, "John"));

        EmployeeDao dao = new EmployeeDao();
        Stream<DynamicTest> saveEmployeeStream = inputList.stream()
                .map(employee -> dynamicTest(
                        "save employee: " + employee.getId(),
                        () -> {
                            Employee returned = dao.save(employee.getId());
                            assertEquals(returned.getId(), employee.getId());
                        }
                ));

        Stream<DynamicTest> saveEmployeeWithFirstNameStream
                = inputList.stream()
                .filter(employee -> employee.getName()!= null)
                .map(employee -> dynamicTest(
                        "save employee with name" + employee.getId(),
                        () -> {
                            Employee returned = dao.save(employee.getId(), employee.getName());
                            assertEquals(returned.getId(), employee.getId());
                            assertEquals(returned.getName(), employee.getName());
                        }));

        return Stream.concat(saveEmployeeStream,
                saveEmployeeWithFirstNameStream);
    }

    class EmployeeDao {
        public Employee save(int id, String name) {
            //save Employee(id, name) into databese
            //if succeed in saving, return saved Employee
            return new Employee(id, name);
        }
        public Employee save(int id) {
            //save Employee(id) into databese
            //if succeed in saving, return saved Employee
            return new Employee(id);
        }
    }

    class Employee {
        int employeeId;
        String employeeName;

        public Employee(int id, String name) {
            employeeId = id;
            employeeName = name;
        }
        public Employee(int id) {
            employeeId = id;
        }

        public int getId() {
            return employeeId;
        }

        public String getName() {
            return employeeName;
        }
    }
}

class DynamicTestLifecycle {
    private Person person;

    @BeforeEach
    void beforeEach(TestInfo info) {
        System.out.println("Before execute "+info.getTestMethod().get().getName());
        person = new Person("Luther", "Benjamin");
        person.setAge(33);
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestFromCollection(){
        return Arrays.asList(
                dynamicTest("FirstName is started with L ",
                        ()->{
                    System.out.println("dynamicTestFromCollection : 1");
                    assertTrue(person.getFirstName().startsWith("L"));
                        }),
                dynamicTest("Age is equal to 33 ",
                        ()->{
                    System.out.println("dynamicTestFromCollection : 2");
                    assertEquals(33,person.getAge());
                })
        );
    }

    @TestFactory
    Iterable<DynamicTest> dynamicTestFromIterable(){
        List<DynamicTest> dynamicTests =  Arrays.asList(
                dynamicTest("LastName is end with n : ",
                        ()->{
                    System.out.println("dynamicTestFromIterable : 3");
                    assertTrue(person.getLastName().endsWith("n"));
                }),
                dynamicTest("Age is greater than 30.",
                        ()->{
                    System.out.println("dynamicTestFromIterable : 4");
                    assertTrue(30<=person.getAge());
                })
        );
        dynamicTests.sort((DynamicTest d1, DynamicTest d2) ->
                d1.getDisplayName().compareTo(d2.getDisplayName()));
        return dynamicTests;
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestFromStream(){

        return Stream.of( new Person("Juliette", "Scott"),
                new Person("Leonardo", "Curie"))
                .map(tt->dynamicTest(
                        "test " + tt.getFullName(),
                        ()->{
                            System.out.println("dynamicTestFromStream : test "+ tt.getFullName());
                            assertEquals(8,tt.getFirstName().length());
                        }
                ));
    }

    @AfterEach
    void afterEach(TestInfo info) {
        System.out.println("After execute "+info.getTestMethod().get().getName());
    }
}