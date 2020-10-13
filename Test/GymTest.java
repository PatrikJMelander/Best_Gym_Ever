import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Patrik Melander
 * Date: 2020-10-08
 * Time: 13:47
 * Project: Gym
 * Copyright: MIT
 */
public class GymTest {
    Gym gym = new Gym();
    ArrayList<Person> testList1 = addToTestList();
    ArrayList<Person> customers = new ArrayList<>();


    public ArrayList <Person> addToTestList() {
        ArrayList<Person> testList1 = new ArrayList<>();
        Person testPerson1 = new Person("1111111111", "Test Person1", LocalDate.parse("2020-01-01"));
        Person testPerson2 = new Person("2222222222", "Test Person2", LocalDate.parse("2000-02-02"));
        Person testPerson3 = new Person("3333333333", "Test person med flera namn3", LocalDate.parse("2020-03-03"));
        testList1.add(testPerson1);
        testList1.add(testPerson2);
        testList1.add(testPerson3);
        return testList1;
    }
    @Test
    public void printFileToListTest() {


        gym.createListFromFile("Test.txt", customers);


        for (int i = 0; i < customers.size(); i++) {
            assertEquals(customers.get(i).socialSecurityNumber, testList1.get(i).socialSecurityNumber);
            assertEquals(customers.get(i).name, testList1.get(i).name);
            assertEquals(customers.get(i).latestPaymentDate, testList1.get(i).latestPaymentDate);
        }
    }
   /* @Test
        public void searchForPersonNrOrNameTest(){
        assertEquals(gym.searchForPersonNrOrName(), testList1.get(0).latestPaymentDate);
        assertEquals(gym.searchForPersonNrOrName(), testList1.get(0).latestPaymentDate);
        assertEquals(gym.searchForPersonNrOrName(), null);
    }

    */
}
