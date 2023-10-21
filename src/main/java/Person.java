
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    final int ADULT_AGE = 19;

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }

    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return age;
    }

    public boolean isAdult(int age){
        return age >= ADULT_AGE;
    }

}