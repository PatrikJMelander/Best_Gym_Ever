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
public class Gym implements Serializable {
    public static List<Person> customers = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public static void serialize(String fileName, List<Person> listName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(listName);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> deSerialize(String fileName, List<Person> listName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            listName = (List<Person>) in.readObject();
            in.close();
            return listName;
        } catch (Exception e) {
            System.out.println("Hittar ej listan på medlemmar");
        }
        return null;
    }

    public void createListFromFile(String fileName, List<Person> list, String serializeFileName) {
        try (Scanner fileScanner = new Scanner(new FileReader(fileName)).useDelimiter(",|\n")) {
            while (fileScanner.hasNext()) {
                String socialSecurityNumber = fileScanner.next().trim();
                String name = fileScanner.next().trim();
                String dateString = fileScanner.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                list.add(new Person(socialSecurityNumber, name, date));
            }
            serialize(serializeFileName, list);
            System.out.println("Listan uppdaterad");

        } catch (FileNotFoundException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember(List<Person> list) {

        System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");


        outerloop:
        while (true) {
            String input = scan.nextLine().trim();
            for (var person : list) {
                if (person.getName().equals(input) || person.getSocialSecurityNumber().equals(input)) {

                    System.out.println("Du har hittat: " + person.name + " " + person.socialSecurityNumber +
                            " hen betalade sitt medlemskap " + person.latestPaymentDate);
                    return person;
                }
            }
            System.out.println("Kunde tyvärr inte hitta kunden\n" +
                    "Vill du försöka igen? (Ja/Nej)");

            input = scan.next().toLowerCase().trim();
            while (true) {
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
        }
        return null;
    }

    public void isCustomerActive(String customerFileName, List<Person> list, String customerVisitFileName) {
        list = deSerialize(customerFileName, list);
        String input = null;
        try {
            Person person = searchForMember(list);
            LocalDate active = person.getLatestPaymentDate().plusYears(1);
            ;
            LocalDate todayDate = LocalDate.now();
            if (active.isAfter(todayDate)) {
                System.out.println("Kunden är aktiv, registrerar besök");
                Person.registerVisits(person, customerVisitFileName);
            } else
                System.out.println("Kunden har inget akvitv medlemskap!");


            }catch(NullPointerException e){
                scan.nextLine();
                System.out.println("Ingen medlem hittades, återgår till huvudmenyn");
            }
            serialize(customerFileName, list);

    }

    public void createNewMember(String serializeFileName, List <Person> list){
        list = deSerialize(serializeFileName, list);
        LocalDate todayDate = LocalDate.now();
        Person person = new Person();
        System.out.println("Skriv in medlemens personr");
        person.socialSecurityNumber = scan.next();
        System.out.println("Skriv in medlemens fullständiga namn");
        person.name = scan.next();
        person.latestPaymentDate =  todayDate;
        list.add(person);
        serialize(serializeFileName, list);
    }

    public void printListOfMembers(String serializeFileName, List <Person> list){
        list = deSerialize(serializeFileName, list);
        try {

            for (var member : list) {
                System.out.println(member);
            }
        }catch (NullPointerException e) {
            System.out.println("Listan är tom");
        }
    }

    public void printListOfAllMembersExercise(String fileName, String serializeFileName){
        List <Person> exerciseList= new ArrayList<>();
        createListFromFile(fileName, exerciseList, serializeFileName);

        for (var member : exerciseList){
            System.out.println(member);
        }
    }
    public void countOneMembersExercise(String fileName, String serializeFileName){
        List <Person> exerciseList= new ArrayList<>();
        try {
            createListFromFile(fileName, exerciseList, serializeFileName);
            Person temp = searchForMember(exerciseList);
            int counter = 0;

            for (var member : exerciseList) {
                if (member.getName().equals(temp.getName()) ||
                        member.getSocialSecurityNumber().equals(temp.getSocialSecurityNumber()))
                    counter++;
                else if (exerciseList.isEmpty())
                System.out.println("Personen har inga registrerade träningar");

            }
            System.out.println(temp.getName() + "har tränat " + counter + " gånger");
        }catch (NullPointerException e){
            System.out.println("Personen kunde inte hittas i listan, säker på att hen verkligen tränat?");
        }
    }

    public void updateCustomerFile(String fileName, List <Person> list, String serializeFileName){
            deSerialize(serializeFileName, list);
            try {
                PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter(fileName,  false)));
                printListOfMembers(fileName, list);
                StringBuilder stringBuilder = new StringBuilder();
                for (var person : list) {
                    stringBuilder.append(person.socialSecurityNumber).append(", ").append(person.name).append("\n").append(person.latestPaymentDate).append("\n");
                    print.println(stringBuilder);
                }
                print.close();
            }catch (IOException e){
                System.out.println("Problem med att skriva till fil");
            }
            serialize(serializeFileName, list);
    }
}


//TODO: Dubbelkolla alla metoder så det finns exceptions
//      Se över alla variabelnamn och metodnamn
//      Kolla så att alla scanners och liknande ligger i try (Try with Resorces)




