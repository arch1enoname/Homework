import adapter.Card;
import adapter.Computer;
import adapter.UsbAdapter;
import factory.Coffee;
import factory.CoffeeFactory;
import proxy.Database;
import proxy.ProxyDB;
import singleton.Cat;

public class Main {
    public static void main(String[] args) {
        //singleton
        Cat cat1 = new Cat("John");
        cat1.mew();


        //factory
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        Coffee espresso = coffeeFactory.createCoffee("espresso");
        Coffee americano = coffeeFactory.createCoffee("americano");

        System.out.println(espresso);
        System.out.println(americano);

        //adapter
        Computer computer = new Computer();
        Card card = new Card("Some info");
        UsbAdapter usbAdapter = new UsbAdapter(card);
        computer.readFromUsb(usbAdapter.getUsb());

        //proxy
        Database proxyDB = new ProxyDB("postgres");
        proxyDB.connect();


    }
}