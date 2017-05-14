package de.thbrunzendorf.payment.value;

/**
 * Created by Thorsten on 14.05.2017.
 */
public class User {

    private String name;
    private Money limit;
    private User supervisor;

    private User(String name, Money limit, User supervisor) {
        this.name = name;
        this.limit = limit;
        this.supervisor = supervisor;
    }

    public static User withNameAndLimit(String name, Money limit) {
        return new User(name, limit, null);
    }

    public static User withNameAndLimitAndSupervisor(String name, Money limit, User supervisor) {
        return new User(name, limit, supervisor);
    }

    public String getName() {
        return name;
    }

    public Money getLimit() {
        return limit;
    }

    public User getSupervisor() {
        return supervisor;
    }
}
