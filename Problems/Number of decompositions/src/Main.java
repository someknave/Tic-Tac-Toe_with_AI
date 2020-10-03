import java.util.ArrayList;

import static java.lang.Integer.min;

class Main {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int input = sc.nextInt();
        ArrayList<String> out = decompose(input, input);
        for (String s : out) {
            System.out.println(s);
        }

    }
    public static ArrayList<String> decompose(int n, int max) {
        ArrayList<String> list = new ArrayList<>();
        if (n == 1) {
            list.add("1");
            return list;
        }
        for (int i = 1; i <= max; i++) {
            if (i == n) {
                list.add(String.valueOf(i));
                return list;
            }
            ArrayList<String> iStrings = decompose(n - i, min(n - i, i));
            for (String s : iStrings) {
                list.add(i + " " + s);
            }
        }
        return list;
    }
}
