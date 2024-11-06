import java.util.Scanner;

//class iphone20 {
//    // charactertic or dATA member  or attribute
//    int a = 10;
//
//    void fly() {
//        System.out.println("iphone20 is flying");
//    }
//}
//class test {
//    public static void main(String[] args) {
//     iphone20 object1 = new iphone20();
//     object1.fly();
//    }
//}
class calculator {
    float number1;
    float number2;

    calculator(int a, int b) {
        number1 = a;
        number2 = b;
    }

    void add() {
        System.out.println(number1 + number2);
        divide();
    }

    void subtract() {
        System.out.println(number1 - number2);
    }

    void divide() {
        System.out.println(number1 / number2);
    }

    void multiply() {
        System.out.println(number1 * number2);
    }
}

class test {
    public static void main(String[] args) {
        calculator object1 = new calculator(10, 20);
//        object1.add();
//        object1.subtract();
//        object1.divide();
//        object1.multiply();
        Scanner object5 = new Scanner(System.in);
        int input;
        while (1 + 1 == 2) {
            System.out.println("Write something over here");
            input = object5.nextInt();
            System.out.println("You entered the number " + input);
        }
    }
}