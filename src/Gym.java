import java.io.*;
import java.time.LocalDate;
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

    ArrayList<Person> customers = new ArrayList<>();

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

    public Person searchForCustomer() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
        String input = scan.nextLine();

        while (true) {
        for (int i = 0; i < customers.size(); i++) {
            Person person = new Person();
            if (customers.get(i).name.equals(input) || (customers.get(i).persoNr.equals(input))) {

                person.latestPaymentDate = customers.get(i).latestPaymentDate;
                person.persoNr = customers.get(i).persoNr;
                person.name = customers.get(i).name;
                return person;
            }
        }
        System.out.println("Kunde tyvärr inte hitta kunden\n" +
                "Vill du försöka igen? (Ja/Nej)");


            input = scan.next().toLowerCase().trim();
            if (input.equals("ja")) {
                System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
            }
            else if (input.equals("nej")) {
                System.out.println("Avslutar utan att hitta en befintlig kund");
                break;
            }
            else//TODO Lös denna
                System.out.println("felaktig inmatning, försök igen\n" +
                        "Vill du göra en ny sökning (Ja/Nej)?");
        }

        return null;
    }



    public void isCustomerActive() throws IOException {
        String input = null;
        Scanner scan = new Scanner(System.in);
        Person person = searchForCustomer();
        LocalDate active = person.latestPaymentDate;
        LocalDate todaysDate = LocalDate.now();
        active.plusYears(1);
        if (active.isAfter(todaysDate)){
            System.out.println("Kunden är aktiv, registrera besök?");
            Person.registerVisits( person);
        }
        else{
            System.out.println("Kunden har inget akvitv medlemskap!\n" +
                    "Ta betalt för engångsbesök (Ja/Nej)?");
            while (true) {


            input = scan.next().toLowerCase().trim();
            if (input.equals("ja")) {
                Person.registerVisits(person);
                break;
            }
            else if (input.equals("nej")) {
                System.out.println("Ingen träning registrerad");
                break;
            }
            else
                System.out.println("felaktig inmatning, försök igen\n" +
                        "Ta betalt för engångsbesök (Ja/Nej)?");
            }
        }


    }


    //TODO: Skapa menysystem för gymmet
    //      Ska finnas inloggning för personal (om de finns tid)
    //      Ska finnas en sökfunktion om de är medlemmar:
    //      Ska finnas en sökfunktion som visar medlemmarnas träningstillfällen (för pt)
    //      Ska finnas förnya medlemskap
    //      Ska finnas stäng systemet
    //      Ska finnas Förnya medlemskap
    //      Dubbelkolla alla metoder så det finns exceptions


}

