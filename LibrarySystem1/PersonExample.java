public class PersonExample {
    public static void main(String[] args) {
        // Create a new Person instance
        Person person = new Person("Alice", "123-456-7890", "1990-05-15");

        // Access and modify fields
        System.out.println("Name: " + person.getName());
        person.setTel("098-765-4321");
        System.out.println("Updated Tel: " + person.getTel());

        // Print all details using toString
        System.out.println(person);
    }
}
