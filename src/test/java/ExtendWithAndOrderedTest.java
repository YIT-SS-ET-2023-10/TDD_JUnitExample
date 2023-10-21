import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.*;

@ExtendWith(bExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExtendWithAndOrderedTest {

    @Test
    @Order(4)
    void nullValues(){
        System.out.println("nullValues() - order 4");
    }

    @Test
    @Order(2)
    void emptyValues(){
        System.out.println("emptyValues() - order 2");
    }


    @Test
    @Order(1)
    void validValues(){
        System.out.println("validValues() - order 1");
    }


    @Test
    public void extendWithTest(){
        System.out.println("ExtendWith Test");
    }

}


class bExtension implements AfterEachCallback, BeforeTestExecutionCallback, AfterAllCallback {

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        System.out.println("ExtendWith Execution");
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("ExtendWith after each Execution");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("ExtendWith after all Execution");
    }
}


class ExtendedWithTest{
    @Test
    public void extendWithTestWithoutExtendedWith(){
        System.out.println("Non ExtendWith Test");
    }

    @Test
    @ExtendWith(bExtension.class)
    public void extendWithTestWithExtendedWith(){
        System.out.println("ExtendWith Test");
    }

}

