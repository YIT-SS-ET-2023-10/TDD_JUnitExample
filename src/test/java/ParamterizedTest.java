import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
//

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.converter.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;


class ParamterizedTest {

    @ParameterizedTest
    @ValueSource(strings={"진","슈가", "제이홉", "RM", "지민","뷔","정국"})
    void bts(String bts){
        System.out.println(bts);
    }

    @ParameterizedTest
    @CsvSource({
            "apple,1",
            "banana, 2",
            "'lemon, lime', 0xF1"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    @ParameterizedTest
    @MethodSource("range")
    void testWithRangeMethodSource(int argument){
        assertEquals(9, argument);
    }

    static IntStream range(){
        return IntStream.range(0,20).skip(10);
    }

    @ParameterizedTest
    @MethodSource
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >=1 && num <=2);
        assertEquals(2, list.size());
    }

    static Stream<Arguments> testWithMultiArgMethodSource() {
        return Stream.of(
                arguments("apple", 1, Arrays.asList("a", "b")),
                arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings={"01.01.2017", "12.06.2020"})
    void testWithExplicitJavaTimeConverter(
            @JavaTimeConversionPattern("dd.MM.yyyy") LocalDate argument){
        assertEquals(2020, argument.getYear());
    }

    @DisplayName("Pass converted Message objects to our test method")
    @ParameterizedTest(name ="{index} => actual = {0}, expected={1}")
    @CsvSource({"Hello,Hello", "Hi,Hi",})
    void shouldPassMessage(@ConvertWith(ToMessageConverter.class) Message actual,
                           @ConvertWith(ToMessageConverter.class) Message expected){
        assertEquals(expected.getMessage(), actual.getMessage());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/resources/two-column.txt",numLinesToSkip = 1)  //파일 저장위치 :  target\test-classes\resources\
    void testWithCsvFileSource(String country, int reference){
        assertNotNull(country);
        assertNotEquals(0, reference);
    }
}

class Message{
    String messageString;
    Message(String messageStr){
        messageString = messageStr;
    }

    public String getMessage(){
        return messageString;
    }
}
