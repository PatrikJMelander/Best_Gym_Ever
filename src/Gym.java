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
                String socialSecurityNumber = fileScanner.next().trim();
                String name = fileScanner.next().trim();
                String dateString = fileScanner.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                this.customers.add(new Person(name, socialSecurityNumber, date));
            }
            System.out.println("Listan uppdaterad");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember() {
        System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
        String input = scan.nextLine();

        outerloop:
        while (true) {
            for (int i = 0; i < customers.size(); i++) {
                Person person = new Person();
                if (customers.get(i).name.equals(input) || (customers.get(i).socialSecurityNumber.equals(input))) {

                    person.latestPaymentDate = customers.get(i).latestPaymentDate;
                    person.socialSecurityNumber = customers.get(i).socialSecurityNumber;
                    person.name = customers.get(i).name;

                    System.out.println("Du har hittat: " + person.name + " " + person.socialSecurityNumber +
                            " hen betalade sitt medlemskap" + person.latestPaymentDate);
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

    public void isCustomerActive(){
        String input = null;
        Person person = searchForMember();
        LocalDate active = person.latestPaymentDate;
        LocalDate todayDate = LocalDate.now();
        active.plusYears(1);
            if (active.isAfter(todayDate)) {
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

    public void updateMembership(){
        Person temp = new Person();
        temp = searchForMember();
        customers.remove(temp);
        createNewMember(temp);
        System.out.println(temp + " har nu uppdaterat sitt medlemskap");
    }

    public void deleteMember(){
        Person temp;
        temp = searchForMember();
        System.out.println(temp + " är nu borttagen ifrån systemet");
        customers.remove(temp);
        updateCustomerFile();
    }

    public void createNewMember(){
        LocalDate todayDate = LocalDate.now();
        Person person = new Person();
        System.out.println("skriv in medlemens personr");
        person.socialSecurityNumber = scan.next();
        System.out.println("skriv in medlemens namn");
        person.name = scan.next();
        System.out.println("skriv in medlemens personr");
        person.latestPaymentDate =  todayDate;
        customers.add(person);
        updateCustomerFile();
    }
    public void createNewMember(Person person) {
        LocalDate todayDate = LocalDate.now();
        person.latestPaymentDate = todayDate;
        customers.add(person);
        updateCustomerFile();
    }

    public void printListofMembers(){
        for (var member : customers){
            System.out.println(member);
        }
    }
    public void printListOfAllMembersExercise(){

    }
    public void printOneMemberExercise(){

    }
    public void updateCustomerFile(){
        {
            try {
                PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("Customer.txt", false)));

                StringBuilder stringBuilder = new StringBuilder();
                LocalDate today = LocalDate.now();
                for (var person : customers) {
                    stringBuilder.append(person.socialSecurityNumber).append(", ").append(person.name).append("\n").append(today);
                    print.println(stringBuilder);
                }

                print.close();
            }catch (IOException e){
                System.out.println("Problem med att skriva till fil");
            }
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




