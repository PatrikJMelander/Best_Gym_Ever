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
    public static boolean test = false;
    public static void setTest(boolean test) {
        Gym.test = test;
    }
    public static List<Person> customers = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public void createListFromFile(String fileName, List<Person> list, String serializeFileName) {
        try (Scanner fileScanner = new Scanner(new FileReader(fileName)).useDelimiter(",|\n")) {
            while (fileScanner.hasNext()) {
                String socialSecurityNumber = fileScanner.next().trim();
                String name = fileScanner.next().trim();
                String dateString = fileScanner.next().trim();
                LocalDate date = LocalDate.parse(dateString);
                list.add(new Person(socialSecurityNumber, name, date));
            }
            IOUtil.serialize(serializeFileName, list);
            System.out.println("Listan uppdaterad");

        } catch (FileNotFoundException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public Person searchForMember(List<Person> list) {

        while (true) {
            String input;
            if (test = true)
                input = "Test Person1";
            else {
                System.out.print("Ange personnr eller fullständigt namn på personen du vill söka på: ");
                input = scan.nextLine().trim();
            }
            for (var person : list) {
                if (person.getName().equalsIgnoreCase(input) ||
                        person.getSocialSecurityNumber().equals(input)) {

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
                    return null;
                } else {
                    System.out.println("felaktig inmatning, försök igen\n" +
                            "Vill du göra en ny sökning (Ja/Nej)?");
                    input = scan.next().toLowerCase().trim();
                }
            }
        }
    }

    public void isCustomerActiveAndRegisterVisit(String customerFileName, List<Person> list, String customerVisitFileName) {

        try {
            Person person = searchForMember(list);
            LocalDate active = person.getLatestPaymentDate().plusYears(1);
            LocalDate todayDate;
            if (test = true)
                todayDate = LocalDate.parse("2020-10-13");
            else
            todayDate = LocalDate.now();
            if (active.isAfter(todayDate)) {
                System.out.println("Kunden är aktiv, registrerar besök");
                Person.registerVisits(person, customerVisitFileName);
            } else
                System.out.println("Kunden har inget akvitv medlemskap!");


            }catch(NullPointerException e){
                scan.nextLine();
                System.out.println("Ingen medlem hittades, återgår till huvudmenyn");
            }
            IOUtil.serialize(customerFileName, list);

    }

    public void createNewMember(String serializeFileName, List <Person> list){

        LocalDate todayDate = LocalDate.now();
        Person person = new Person();
        if (test = true){
            person.socialSecurityNumber = "8502160000";
            person.name = "Patrik Melander";
        }
        else {
            System.out.println("Skriv in medlemens personr");
            person.socialSecurityNumber = scan.next();
            System.out.println("Skriv in medlemens fullständiga namn");
            person.name = scan.next();
        }
        person.latestPaymentDate =  todayDate;
        list.add(person);
        IOUtil.serialize(serializeFileName, list);
    }

    public void printListOfMembers(List <Person> list){
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
}




