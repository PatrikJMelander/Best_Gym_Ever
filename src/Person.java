import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

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
    LocalDate latestPaymentDate;


    public Person() {
    }

    public Person(String persoNr, String name, LocalDate latestPaymentDate) {
        this.persoNr = persoNr;
        this.name = name;
        this.latestPaymentDate = latestPaymentDate;
    }


    public static void registerVisits(Person person) throws IOException {
            PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("CustomerVisits.txt", true)));

            StringBuilder stringBuilder = new StringBuilder();
            LocalDate today = LocalDate.now();
            stringBuilder.append(person.name).append("\n").append(person.persoNr).append("\n").append(today);
            print.println(stringBuilder);

            print.close();
    }

}
