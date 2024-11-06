public class iphone16 {
    int size = 300;
    int ram = 100;

    void fly() {
        System.out.println("iphone16 is flying");
    }

    void swim() {
        System.out.println("iphone16 is swimming");
    }
}
class iphone_test  {
    public static void main(String[] args) {
        iphone16 yuhan = new iphone16();
        yuhan.swim();
        yuhan.fly();
    }

}