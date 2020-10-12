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
    String socialSecurityNumber;
    String name;
    LocalDate latestPaymentDate;



    public Person() {
    }

    public Person(String socialSecurityNumber, String name, LocalDate latestPaymentDate) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.latestPaymentDate = latestPaymentDate;
    }

    public static void registerVisits(Person person) {
           try {
               PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("CustomerVisits.txt", true)));

               StringBuilder stringBuilder = new StringBuilder();
               LocalDate today = LocalDate.now();
               stringBuilder.append(person.name).append("\n").append(person.socialSecurityNumber).append("\n").append(today);
               print.println(stringBuilder);

               print.close();
           }catch (IOException e){
               System.out.println("Problem med att skriva till fil");
           }
    }

    @Override
    public String toString() {
        return socialSecurityNumber + ", " + name + "\n" + latestPaymentDate;
    }
}
