import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Created by Patrik Melander
 * Date: 2020-10-08
 * Time: 15:06
 * Project: Gym
 * Copyright: MIT
 */
public class Gym implements Serializable{
    public static List<Person> customers = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public static void serialize() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("customers.ser", false));
            out.writeObject(customers);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("customers.ser"));
            customers = (List<Person>) in.readObject();
            in.close();
            return customers;
        } catch (Exception e) {
            System.out.println("Hittar ej listan på kunder");
        }
        return null;
    }

    public void createListFromFile(String fileName, List <Person> list) {
        try (Scanner fileScanner = new Scanner(new FileReader(fileName)).useDelimiter(",|\n")) {
            while (fileScanner.hasNext()) {
                String socialSecurityNumber = fileScanner.next().trim();
                String name = fileScanner.next().trim();
                String dateString = fileScanner.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                list.add(new Person(socialSecurityNumber, name, date));
            }
            serialize();
            System.out.println("Listan uppdaterad");

        } catch (FileNotFoundException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember() {
        customers = deSerialize();

        System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");


        outerloop:
        while (true) {
            String input = scan.nextLine().trim();
            for (var person : customers){
                if (person.getName().equals(input) || person.getSocialSecurityNumber().equals(input)) {

                    System.out.println("Du har hittat: " + person.name + " " + person.socialSecurityNumber +
                            " hen betalade sitt medlemskap " + person.latestPaymentDate);
                    return person;
                }
            }
            System.out.println("Kunde tyvärr inte hitta kunden\n" +
                    "Vill du försöka igen? (Ja/Nej)");

            input = scan.next().toLowerCase().trim();
            while (true)
                if (input.equals("ja")) {
                    scan.reset();
                    System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
                    input = scan.nextLine().trim();
                    break;
                } else if (input.equals("nej")) {
                    System.out.println("Avslutar utan att hitta en befintlig kund");
                    scan.reset();
                    break outerloop;
                } else {
                    scan.reset();
                    System.out.println("felaktig inmatning, försök igen\n" +
                            "Vill du göra en ny sökning (Ja/Nej)?");
                    input = scan.next().toLowerCase().trim();
                }
        }
        return null;
    }

    public void isCustomerActive() {
        customers = deSerialize();
        String input = null;
        try {
            Person person = searchForMember();
            LocalDate active = person.getLatestPaymentDate().plusYears(1);;
            LocalDate todayDate = LocalDate.now();
            if (active.isAfter(todayDate)) {
                System.out.println("Kunden är aktiv, registrerar besök");
                Person.registerVisits(person);
            } else {
                System.out.println("Kunden har inget akvitv medlemskap!\n" +
                        "Förnya medlemskap? (Ja/Nej)?");

                while (true) {
                    input = scan.next().toLowerCase().trim();
                    if (input.equals("ja")) {
                        updateMembership(person);
                        break;
                    } else if (input.equals("nej")) {
                        System.out.println("Ingen träning registrerad");
                        break;
                    } else
                        System.out.println("felaktig inmatning, försök igen\n" +
                                "Ta betalt för engångsbesök (Ja/Nej)?");
                }
            }
        }catch (NullPointerException e){
            System.out.println("Ingen medlem hittades, återgår till huvudmenyn");
        }

    }

    public void updateMembership(){
        customers = deSerialize();
        Person temp;
        temp = searchForMember();
        temp.setLatestPaymentDate(LocalDate.now());
        System.out.println(temp + " Medlemskap uppdaterat");
        updateCustomerFile();
        serialize();
    }

    public void updateMembership(Person temp){
        deSerialize();
        temp.setLatestPaymentDate(LocalDate.now());
        System.out.println(temp + " har nu uppdaterat sitt medlemskap");
        updateCustomerFile();
        serialize();
    }

    public void deleteMember(){
        deSerialize();
        Person temp;
        temp = searchForMember();
        System.out.println(temp + " är nu borttagen ifrån systemet");
        customers.remove(temp);
        updateCustomerFile();
        serialize();
    }

    public void createNewMember(){
        deSerialize();
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
        serialize();
    }
    /*public void createNewMember(Person person) {
        deSerialize();
        LocalDate todayDate = LocalDate.now();
        person.latestPaymentDate = todayDate;
        customers.add(person);
        updateCustomerFile();
        serialize();
    }

     */

    public void printListofMembers(){
        deSerialize();
        for (var member : customers){
            System.out.println(member);
        }
    }

    public void printListOfAllMembersExercise(){
        ArrayList <Person> exerciseList= new ArrayList<>();
        createListFromFile("CustomerVisits.txt", exerciseList);



    }
    public void countOneMembersExercise(){

    }

    public void updateCustomerFile(){
            deSerialize();
            try {
                PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("Customer.txt", false)));

                StringBuilder stringBuilder = new StringBuilder();
                LocalDate today = LocalDate.now();
                for (var person : customers) {
                    stringBuilder.append(person.socialSecurityNumber).append(", ").append(person.name).append("\n").append(today).append("\n");
                    print.println(stringBuilder);
                }

                print.close();
            }catch (IOException e){
                System.out.println("Problem med att skriva till fil");
            }
            serialize();

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
//      Kolla så att alla scanners och liknande ligger i try (Try with Resorces)




