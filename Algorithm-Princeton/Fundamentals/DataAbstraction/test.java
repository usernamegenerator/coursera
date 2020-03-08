/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.DataAbstraction;

public class test {
    public static void main(String[] args) {
        Counter a = new Counter("A");
        Counter b = new Counter("B");
        System.out.println(a);
        System.out.println(b);
        a = b;
        System.out.println();
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.getName());

    }
}
