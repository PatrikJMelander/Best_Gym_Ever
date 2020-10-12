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
    Scanner scan = new Scanner(System.in);

    public void createListFromFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new FileReader(fileName)).useDelimiter(", | \n")) {
            while (fileScanner.hasNextLine()) {
                String personId = fileScanner.next().trim();
                String name = fileScanner.next().trim();
                String dateString = fileScanner.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                this.customers.add(new Person(name, personId, date));
            }
            System.out.println("Fil skapad");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember() {
        System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
        String input = scan.nextLine();
        //TODO Felhantera input
        outerloop:
        while (true) {
            for (int i = 0; i < customers.size(); i++) {
                Person person = new Person();
                if (customers.get(i).name.equals(input) || (customers.get(i).socialSecurityNumber.equals(input))) {

                    person.latestPaymentDate = customers.get(i).latestPaymentDate;
                    person.socialSecurityNumber = customers.get(i).socialSecurityNumber;
                    person.name = customers.get(i).name;
                    return person;
                }
            }

            System.out.println("Kunde tyvärr inte hitta kunden\n" +
                    "Vill du försöka igen? (Ja/Nej)");
            input = scan.next().toLowerCase().trim();
            while (true)
                if (input.equals("ja")) {
                    System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
                    break;
                } else if (input.equals("nej")) {
                    System.out.println("Avslutar utan att hitta en befintlig kund");
                    break outerloop;
                } else {
                    System.out.println("felaktig inmatning, försök igen\n" +
                            "Vill du göra en ny sökning (Ja/Nej)?");
                    input = scan.next().toLowerCase().trim();
                }
        }
        return null;
    }

    public void isCustomerActive() throws IOException {
        String input = null;
        Person person = searchForMember();
        LocalDate active = person.latestPaymentDate;
        LocalDate todaysDate = LocalDate.now();
        active.plusYears(1);
        if (active.isAfter(todaysDate)) {
            System.out.println("Kunden är aktiv, registrera besök?");
            Person.registerVisits(person);
        } else {
            System.out.println("Kunden har inget akvitv medlemskap!\n" +
                    "Ta betalt för engångsbesök (Ja/Nej)?");

            while (true) {
                input = scan.next().toLowerCase().trim();
                if (input.equals("ja")) {
                    Person.registerVisits(person);
                    break;
                } else if (input.equals("nej")) {
                    System.out.println("Ingen träning registrerad");
                    break;
                } else
                    System.out.println("felaktig inmatning, försök igen\n" +
                            "Ta betalt för engångsbesök (Ja/Nej)?");
            }
        }
    }

    public void mainMenu() {
        String input;
        boolean running = true;
        while (running) {
            System.out.println("\n\nVälj vad du vill göra:\n\n" +
                    "1. Medlemmar\n" +
                    "2. PT\n" +
                    "3. Avsluta\n" +
                    "4. Spara alla ändringar");

            input = scan.nextLine();

            switch (input) {
                case "1" -> menmberMenu();
                case "2" -> ptMenu();
                case "3" -> running = exitMenu();
                default -> System.out.println("Ange ett giltigt val! (1-3)");
            }
        }
    }


    public void menmberMenu() {
        String input;

        while (true) {
            System.out.println("\nVälj vad du vill göra:\n" +
                    "1. Sök efter medlem\n" +
                    "2. Förnya medlemskap\n" +
                    "3. Ta bort medlem\n" +
                    "4. Skapa nytt medlemskap\n" +
                    "5. Skriv ut lista på alla medlemmar\n" +
                    "6. Återgå till huvudmenyn");

            input = scan.nextLine();

            switch (input) {
                case "1" -> searchForMember();
                case "2" -> updateMembership();
                case "3" -> deleteMember();
                case "4" -> createNewMember();
                case "5" -> printListofMembers();
                case "6" -> {
                    return;
                }
                default -> System.out.println("Ange ett giltigt val! (1-6)");
            }
        }
    }

    public void ptMenu(){
        String input;

        while (true) {
            System.out.println("\nVälj vad du vill göra:\n" +
                    "1. Skriv ut lista på alla träningstillfällen\n" +
                    "2. Skriv ut lista på en persons träningstillfällen\n" +
                    "3. Återgå till huvudmenyn");

            input = scan.nextLine();

            switch (input) {
                case "1" -> printListOfAllMembersExercise();
                case "2" -> printOneMemberExercise();
                case "3" -> {
                    return;
                }
                default -> System.out.println("Ange ett giltigt val! (1-3)");
            }
        }
    }

    public boolean exitMenu() {
        System.out.println("Är du säker på att du vill avsluta? (Ja/Nej) ");
        String input = scan.nextLine();
        while (true) {
            if (input.equalsIgnoreCase("ja")) {
                System.out.println("Tack för besöket! Välkommen åter!");
                return false;
            } else if (input.equalsIgnoreCase("nej")) {
                System.out.println("Stanna så längen du vill");
                return true;
            } else
                System.out.print("Nu blev de fel, testa igen: ");
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
//      Se över alla variabelnamn och metodnamn


}

