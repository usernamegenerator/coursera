/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.ElementarySorts;

public class Date implements Comparable<Date> {
    private final int day;
    private final int month;
    private final int year;

    public Date(int m, int d, int y) {
        day = d;
        month = m;
        year = y;
    }

    // practice 1.2.19
    public Date(String date) {
        String[] fields = date.split("/");
        month = Integer.parseInt(fields[0]);
        day = Integer.parseInt(fields[1]);
        year = Integer.parseInt(fields[2]);
    }

    public int day() {
        return day;
    }

    public int month() {
        return month;
    }

    public int year() {
        return year;
    }

    public int compareTo(Date that) {
        if (this.year > that.year) return +1;
        if (this.year < that.year) return -1;

        if (this.month > that.month) return +1;
        if (this.month < that.month) return -1;

        if (this.day > that.day) return +1;
        if (this.day < that.day) return -1;

        return 0;
    }

    public String toString() {
        return month + "/" + day + "/" + year;
    }

    public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null) return false;

        if (this.getClass() != x.getClass()) return false;
        Date that = (Date) x;
        if (this.day != that.day) return false;
        if (this.month != that.month) return false;
        if (this.year != that.year) return false;
        return true;
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + month;
        hash = 31 * hash + day;
        hash = 31 * hash + year;
        return hash;
    }


    public static void main(String[] args) {

    }
}
