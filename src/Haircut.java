public class Haircut {
    String name;
    int price;

    public Haircut() {
    }

    public Haircut(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Стрижка " + name +
                ", цена: " + price;
    }
}
