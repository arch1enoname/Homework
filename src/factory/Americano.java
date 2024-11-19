package factory;

public class Americano implements Coffee{
    public void prepareCoffee() {
        System.out.println("Prepare americano coffee");
    }

    @Override
    public String toString() {
        return "adapter.Americano{}";
    }
}
