import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.util.Map;

import static org.junit.jupiter.api.condition.OS.*;

public class DisablingTest {
    @Disabled
    @Test
    void testWillBeSkipped(){
        System.out.println("Disabled 메소드.");
    }

    @Test
    void testWillBeExecuted(){
        System.out.println("Enabled 메소드.");
    }

    @Test
    @EnabledOnOs({ LINUX, MAC })
    void onLinuxOrMac() {
        System.out.println("Enabled on 리눅스, 맥.");
    }

    @Test
    @DisabledOnOs(WINDOWS)
    void disabledOnWindows() {
        System.out.println("윈도우즈가 아니군요.");
    }
    
    @Test
    @EnabledOnOs(WINDOWS)
    void enabledOnWindows() {
        System.out.println("Enabled on Windows");
    }

    @Test
    @DisabledOnJre({JRE.JAVA_9,JRE.JAVA_10})
    void notOnJava9_10(){
        System.out.println("자바9, 자바10이 아닙니다.");
    }

    @Test
//    @EnabledIfSystemProperty(named="java.vm.vendor", matches = "Oracle.*")
    @EnabledIfSystemProperty(named="os.arch", matches = ".+64.*")
    void enabledIfSystemPropertyTest(){
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("java.vm.vendor"));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "windir", matches =".+WINDOWS" )
    void envVariableMatches(){
        System.out.println(System.getenv().get("windir"));
        System.out.println("환경 변수 windir 은  WINDOWS 입니다.");
    }


    @Test
    void getEnvMap(){
        Map<String, String> env = System.getenv();

        for(String envName : env.keySet()){
            System.out.format("%s = %s%n", envName, env.get(envName));
        }
    }
        
}

@Disabled
class DisabledClassDemo{
    @Test
    void testWillBeDisabled(){
        System.out.println("Can you see me?\nAbsolutely NOP!");
    }
}
