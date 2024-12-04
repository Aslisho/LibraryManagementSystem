public class Person {
    private String name;
    private String tel;
    private String birthDate;

    public Person(String name, String tel, String birthDate) {
        this.name = name;
        this.tel = tel;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', tel='" + tel + "', birthDate='" + birthDate + "'}";
    }
}