/**
 * Created by Patrik Melander
 * Date: 2020-10-08
 * Time: 13:48
 * Project: Gym
 * Copyright: MIT
 */
public class Person {
    String persoNr;
    String name;
    String latestPaymentDate;

    public Person() {
    }

    public Person(String persoNr, String name, String latestPaymentDate) {
        this.persoNr = persoNr;
        this.name = name;
        this.latestPaymentDate = latestPaymentDate;
    }
}
