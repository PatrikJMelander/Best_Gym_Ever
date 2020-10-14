import java.io.*;
import java.time.LocalDate;

/**
 * Created by Patrik Melander
 * Date: 2020-10-08
 * Time: 13:48
 * Project: Gym
 * Copyright: MIT
 */
public class Person implements Serializable{
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


    public static void registerVisits(Person person, String filename) {
           try {
               PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));

               StringBuilder stringBuilder = new StringBuilder();
               LocalDate today = LocalDate.now();
               stringBuilder.append(person.socialSecurityNumber).append(", ").append(person.name).append("\n").append(today).append("\n");
               print.print(stringBuilder);

               print.close();
           }catch (IOException e){
               System.out.println("Problem med att skriva till fil");
           }
    }

    @Override
    public String toString() {
        return socialSecurityNumber + ", " + name + "\n" + latestPaymentDate;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLatestPaymentDate() {
        return latestPaymentDate;
    }

    public void setLatestPaymentDate(LocalDate latestPaymentDate) {
        this.latestPaymentDate = latestPaymentDate;
    }
}
