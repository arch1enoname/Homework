package proxy;

public class ProxyDB implements Database{
    String name;
    String url;
    RealDB realDB;

    public ProxyDB(String name) {
        this.name = name;
        this.url = "localhost:3306/" + name;
    }

    public void connect() {
        if (realDB == null) {
            realDB = new RealDB(this.name);
            realDB.url = this.url;
        }
        realDB.connect();
    }

}
