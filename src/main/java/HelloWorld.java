import java.util.Random;

public class HelloWorld {
    private Random generator;

    public static void printHelloWorld(String[] arg){
        System.out.println("Hello World!!!!!\nThis is JUNIT Test program.");
    }

    public String printNum(int num){
        return "Hello "+num;
    }

    public boolean isGreater(int num1, int num2){
        return num1>num2;
    }

    public String getClassName(){
        return "HelloWorld";
    }

    public int rollDice(){
        return generator.nextInt(6)+1;
    }
}
