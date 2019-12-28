package br.com.marlon.shoppingcart.domain.util;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        test("1");
        test("2");
        test("1");

    }

    private static void test(String value) throws InterruptedException {

        synchronized (value) {

            if (value.equals("1")) {
                //Thread.sleep(5000);
            }

            System.out.println(value);
        }

    }
}
