package Sorting.PriorityQueues;

import edu.princeton.cs.algs4.Date;

public class Transaction implements Comparable<Transaction> {
    private String who;
    private Date when;
    private final double amount;

    public Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = new Date(when.toString());
        this.amount = amount;
    }

    // practice 1.2.19
    public Transaction(String transaction) {
        String[] fields = transaction.split(" ");
        this.who = fields[0];
        this.when = new Date(fields[1]);
        this.amount = Double.parseDouble(fields[2]);
    }

    public String who() {
        return this.who;
    }

    public Date when() {
        return this.when;
    }

    public double amount() {
        return this.amount;
    }

    public String toString() {
        return this.who + "\t" + this.when + "\t" + this.amount;
    }

    // practice 1.2.14
    public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;

        Transaction that = (Transaction) x;
        if (!this.who.equals(that.who)) return false;
        if (!this.when.equals(that.when)) return false;
        if (Double.compare(this.amount, that.amount) != 0) return false;
        return true;
    }

    // practice 2.1.21
    public int compareTo(Transaction that) {
        int cmp = Double.compare(this.amount, that.amount);
        if (cmp > 0) return +1;
        if (cmp < 0) return -1;
        return 0;
    }

    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
        // return Objects.hash(who, when, amount);
    }

    public static void main(String[] args) {

    }
}
