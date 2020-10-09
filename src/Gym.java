import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Patrik Melander
 * Date: 2020-10-08
 * Time: 15:06
 * Project: Gym
 * Copyright: MIT
 */
public class Gym {
    boolean test = false;

    ArrayList <Person> customers = new ArrayList<>();

    public void createListFromFile(String fileName) {

        try (Scanner in = new Scanner(new FileReader(fileName)).useDelimiter(", | \n")) {
            while (in.hasNextLine()) {
                String personId = in.next().trim();
                String name = in.next().trim();
                String dateString = in.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                this.customers.add(new Person(name, personId, date));
            }
            System.out.println("Fil skapad");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LocalDate searchForPersonNrOrName() {
        Scanner scan = new Scanner(System.in);

        if (this.test) {

            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).name.equals("Test Person1"))
                    return customers.get(i).latestPaymentDate;

                else if (customers.get(i).persoNr.equals("1111111111"))
                    return customers.get(i).latestPaymentDate;

            }
            System.out.println("Kunde tyvärr inte hitta kunden");
            return null;

        }
        else {
            System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
            String input = scan.nextLine();

            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).name.equals(input))
                    return customers.get(i).latestPaymentDate;

                else if (customers.get(i).persoNr.equals(input))
                    return customers.get(i).latestPaymentDate;

            }
            System.out.println("Kunde tyvärr inte hitta kunden");
            return null;
        }


    }
    public void isCustomerActive(){
        LocalDate active = searchForPersonNrOrName();
        LocalDate todaysDate = LocalDate.now();
        active.plusYears(1);
        if (active.isAfter(todaysDate)){
            System.out.println("Kunden är aktiv, registrera besök?");
            //TODO Metod för att registrera kund
        }
        else{
            System.out.println("Kunden har inget akvitv medlemskap!\n" +
                    "Ta betalt för engångsbesök (Ja/Nej)?");
            //TODO Metod för att registrera kund
        }


    }
    public static void registerVisits(Person person) throws IOException {
            PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("CustomerVisits.txt", true)));

            StringBuilder stringBuilder = new StringBuilder();
            LocalDate today = LocalDate.now();
            stringBuilder.append(person.name).append("\n").append(person.persoNr).append("\n").append(today);
            print.println(stringBuilder);

            print.close();
    }


    //TODO: Skapa menysystem för gymmet
    //      Ska finnas inloggning för personal (om de finns tid)
    //      Ska finnas en sökfunktion om de är medlemmar
    //      Ska finnas en sökfunktion som visar medlemmarnas träningstillfällen (för pt)
    //      Ska finnas förnya medlemskap
    //      Ska finnas stäng systemet
    //

    public static void checkLastPaymentDate() {


    }

    public static void main(String[] args) {


        checkLastPaymentDate();

    }

}

