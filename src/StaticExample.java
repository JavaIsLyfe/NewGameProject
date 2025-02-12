public class StaticExample {
    static int count = 0;
    int test = 5;

    StaticExample() {
        count++;
    }

    public static void main(String[] args) {
        StaticExample object1 = new StaticExample();
        StaticExample object2 = new StaticExample();
        StaticExample object3 = new StaticExample();
        System.out.println(object1.count);
        System.out.println(object2.count);
        System.out.println(object3.count);
    }

}

