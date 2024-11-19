package factory;

public class Espresso implements Coffee{

    @Override
    public void prepareCoffee() {
        System.out.println("Preparing espresso coffee");
    }

    @Override
    public String toString() {
        return "adapter.Espresso{}";
    }
}
