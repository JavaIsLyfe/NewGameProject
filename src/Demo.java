import java.util.Arrays;

public class Demo {

    public static void main(String[] args) {
//        for(int abc=1; abc <= 20; abc++){
//            System.out.println(10 + " x " + abc + " = " + abc * 10);
//        }
//       float w = 5.5f;
//        float h = 8.5f;
//        float area = h * w;
//        float perimeter = (w + h) * 2;
//        System.out.println("area = " + area);
//        System.out.println("perimeter = " + perimeter);
//
//        int a = 10;
//        int b = 20;
//        int temp = b;
//        b = a;
//        a = temp;
//        System.out.println(a);
//        System.out.println(b);
//
//        int a = 50;
//        int b = 10;
//        int c = 700;
//        if (a > b && a > c)
//            System.out.println(a);
//        else if (b > c) {
//            System.out.println(b);
//
//        } else {
//            System.out.println(c);
//        }

//        System.out.println(!(10 > 5 || 5 < 2 && 5 < 1 || 5 > 2));
//        int number = 3;
//        int number2 = number * 2;
//        int a;
//        int c = 0;
//        for (a = 1; a <= number2; a = a + 2) {
//            c = c + a;
//        }
//        System.out.println(c);

        // Right angled triangle
//        for (int b = 0; b < 10; b++) {
//            System.out.print(b + ":\t");
//            for (int j = 0; j <= b; j++) {
//                System.out.print(j + 1);
//            }
//            System.out.println();
//        }
//
//
//        int i = 10;
//        int a = 10 + ++i;
//
//        System.out.println(a);
// WAR OF NUMBERS:
//        String[] b = {"T", "H", "E", " ", "Q", "U", "I", "C", "K", " ", "B", "R", "O"};
//        int odd = 0;
//        int even = 0;
//        int[] warofnumbers = {5, 9, 45, 6, 2, 7, 34, 8, 6, 90, 5, 243};
//        for (int i = 0; i <= warofnumbers.length - 1; i++) {
//            if (warofnumbers[i] % 2 == 1) {
//                odd = odd + warofnumbers[i];
//            } else {
//                even = even + warofnumbers[i];
//            }
//        }
//        if (odd > even) {
//            System.out.println(odd - even);
//        } else {
//            System.out.println(even - odd);
//        }


        int[][] array1 = {
                {1, 2, 3}, {4, 5, 6}, {7, 8, 9}
        };

        for (int i = 0; i <= 2; i++) {
            for (int x = 0; x <= 2; x++) {
                System.out.print(array1[x][i] + " ");
                array1[x][i] = array1[i][x];
            }
            System.out.println();
        }

        System.out.println();
        // printing the 2D array
        for(int i=0;i<=2; i++) {
            for(int j=0;j<=2;j++) {
                System.out.print(array1[i][j] + " ");
            }
            System.out.println();
        }
    }
}