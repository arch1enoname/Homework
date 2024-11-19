package proxy;

public class RealDB implements Database{
    String name;
    String url;

    public RealDB(String name) {
        this.name = name;
    }

    public void connect() {
        System.out.println("Connecting to db " + name + "...");
    }

}
