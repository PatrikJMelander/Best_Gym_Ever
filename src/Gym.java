import java.io.*;
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

    public static void serialize(String fileName, List <Person> customers) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(customers);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> deSerialize(String fileName, List <Person> customers) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            customers = (List<Person>) in.readObject();
            in.close();
            return customers;
        } catch (Exception e) {
            System.out.println("Hittar ej listan på medlemmar");
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
            serialize("customers.ser", customers);
            System.out.println("Listan uppdaterad");

        } catch (FileNotFoundException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember() {
        customers = deSerialize("customers.ser", customers);

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
                    scan.nextLine();
                    System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
                    break;
                } else if (input.equals("nej")) {
                    scan.nextLine();
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

    public void isCustomerActive() {
        customers = deSerialize("customers.ser", customers);
        String input = null;
        try {
            Person person = searchForMember();
            LocalDate active = person.getLatestPaymentDate().plusYears(1);;
            LocalDate todayDate = LocalDate.now();
            if (active.isAfter(todayDate)) {
                System.out.println("Kunden är aktiv, registrerar besök");
                Person.registerVisits(person, "CustomerVisits.txt");
            } else {
                System.out.println("Kunden har inget akvitv medlemskap!\n" +
                        "Förnya medlemskap? (Ja/Nej)?");

                while (true) {
                    input = scan.next().toLowerCase().trim();
                    if (input.equals("ja")) {
                        scan.nextLine();
                        updateMembership();
                        break;
                    } else if (input.equals("nej")) {
                        scan.nextLine();
                        System.out.println("Ingen träning registrerad");
                        break;
                    } else {
                        scan.nextLine();
                        System.out.println("felaktig inmatning, försök igen\n");
                    }
                }
            }
        }catch (NullPointerException e){
            scan.nextLine();
            System.out.println("Ingen medlem hittades, återgår till huvudmenyn");
        }
        serialize("customers.ser", customers);
    }

    public void updateMembership(){
        customers = deSerialize("customers.ser", customers);
        Person temp;
        temp = searchForMember();
        temp.setLatestPaymentDate(LocalDate.now());
        System.out.println(temp + " Medlemskap uppdaterat");
        serialize("customers.ser", customers);
    }

    public void deleteMember(){
        customers = deSerialize("customers.ser", customers);
        Person temp;
        temp = searchForMember();
        System.out.println(temp + " är nu borttagen ifrån systemet");
        customers.remove(temp);
        serialize("customers.ser", customers);
    }

    public void createNewMember(){
        customers = deSerialize("customers.ser", customers);
        LocalDate todayDate = LocalDate.now();
        Person person = new Person();
        System.out.println("Skriv in medlemens personr");
        person.socialSecurityNumber = scan.next();
        System.out.println("Skriv in medlemens fullständiga namn");
        person.name = scan.next();
        person.latestPaymentDate =  todayDate;
        customers.add(person);
        serialize("customers.ser", customers);
    }

    public void printListOfMembers(){
        customers = deSerialize("customers.ser", customers);
        try {
            for (var member : customers) {
                System.out.println(member);
            }
        }catch (NullPointerException e) {
            System.out.println("Listan är tom");
        }
    }

    public void printListOfAllMembersExercise(){
        List <Person> exerciseList= new ArrayList<>();
        createListFromFile("CustomerVisits.txt", exerciseList);

        for (var member : exerciseList){
            System.out.println(member);
        }
    }
    public void countOneMembersExercise(){
        List <Person> exerciseList= new ArrayList<>();
        createListFromFile("CustomerVisits.txt", exerciseList);
        Person temp = searchForMember();
        int couter = 0;

        for (var member : exerciseList){
            if (member.getName().equals(temp.getName()) ||
                    member.getSocialSecurityNumber().equals(temp.getSocialSecurityNumber()))
                couter++;

        }
        System.out.println(temp.getName() + "har tränat " + couter + " gånger");
    }

    public void updateCustomerFile(){
            deSerialize("customers.ser",customers);
            try {
                PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter("Customer.txt", false)));
                printListOfMembers();
                StringBuilder stringBuilder = new StringBuilder();
                for (var person : customers) {
                    stringBuilder.append(person.socialSecurityNumber).append(", ").append(person.name).append("\n").append(person.latestPaymentDate).append("\n");
                    print.println(stringBuilder);
                }
                print.close();
            }catch (IOException e){
                System.out.println("Problem med att skriva till fil");
            }
            serialize("customers.ser", customers);
    }
}


//TODO: Dubbelkolla alla metoder så det finns exceptions
//      Se över alla variabelnamn och metodnamn
//      Kolla så att alla scanners och liknande ligger i try (Try with Resorces)




