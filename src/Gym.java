import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public static boolean checkLastPaymentDate(){
        String dateToCheck = searchForPersonNrOrName();
    }

    public static void main(String[] args) {

        ArrayList<Person> customers = printFileToList("Customer.txt");

        for (var c : customers){
            System.out.println(c.name + " " + c.persoNr + " " + c.latestPaymentDate);
        }
    }

}

