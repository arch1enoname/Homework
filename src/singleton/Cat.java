package singleton;

public class Cat {
    String name;
    LogClass logClass = LogClass.getInstance();

    public Cat(String name) {
        this.name = name;
    }

    public void mew() {
        System.out.println("Mew");
        logClass.classLogg(this, "method - mew");
    }
}
