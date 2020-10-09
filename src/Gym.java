import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static ArrayList<Person> printFileToList(String fileName) {

        ArrayList<Person> customers = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                Person p = new Person();
                p.persoNr = temp.substring(0,10);
                p.name = temp.substring(12);
                p.latestPaymentDate = bufferedReader.readLine();
                customers.add(p);
            }
            bufferedReader.close();
        } catch (NoSuchElementException | IOException e){
            e.printStackTrace();
        }

        return customers;

    }

    public static String searchForPersonNrOrName(String input, String fileName){
        ArrayList<Person> customers = printFileToList(fileName);

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).name.equals(input))
                return customers.get(i).latestPaymentDate;

            else if (customers.get(i).persoNr.equals(input))
                return customers.get(i).latestPaymentDate;

        }
        return null;
    }

    //TODO: Skapa menysystem för gymmet
    //      Ska finnas inloggning för personal (om de finns tid)
    //      Ska finnas en sökfunktion om de är medlemmar
    //      Ska finnas en sökfunktion som visar medlemmarnas träningstillfällen (för pt)
    //      Ska finnas förnya medlemskap
    //      Ska finnas stäng systemet
    //

    public static void checkLastPaymentDate(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Ange personnr eller Fullständigt namn på personen du vill söka på: ");
        String input = scan.nextLine();
        System.out.print("Skriv in filnamnet på filen du vill söka i: ");
        String inputFilename = scan.nextLine();

        String lastDateOfPayment = searchForPersonNrOrName(input, inputFilename);


        System.out.println("Datumet är " + lastDateOfPayment);
        LocalDate date =null ;
        date = LocalDate.parse(lastDateOfPayment);
        System.out.println("Datumet är " + date);

    }

    public static void main(String[] args) {
       /* Date date = null;
        DateFormat dateFormat = DateFormat.getDateInstance();
        String tempdate = "2020-10-09";
        tempdate.replace('-', '/').trim();
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(tempdate);
        }catch (ParseException e){
            System.out.println("Kunde inte läsa in rätt datum");
        }
        System.out.println("Datumet är: " + date);

        */



        checkLastPaymentDate();

    }

}

