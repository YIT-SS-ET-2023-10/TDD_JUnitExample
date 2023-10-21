import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A special test case")
public class DisplayNameTest {
    @Test
    @DisplayName("getFullName에 공백이 있는 지 여부 확인")
    void testWithDisplayNameContainingSpaces(){
        Person john = new Person("John","Smith");
        assertTrue(john.getFullName().contains(" "));
    }

    @Test
    @DisplayName("getFullName() 에 _ 가 있는 경우")
    void testWithDisplayNameContaininSpecialCharcter(){
        Person john = new Person("John","Smith_");
        assertTrue(john.getFullName().contains("_"));
    }
}
