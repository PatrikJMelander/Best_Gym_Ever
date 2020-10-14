import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        Person testPerson2 = new Person("2222222222", "Test Person2", LocalDate.parse("2002-02-02"));
        Person testPerson3 = new Person("3333333333", "Test person med flera namn3", LocalDate.parse("2020-03-03"));
        testList1.add(testPerson1);
        testList1.add(testPerson2);
        testList1.add(testPerson3);
        return testList1;
    }
    @Test
    public void printFileToListTEST() {
        gym.createListFromFile("Test.txt", customers, "CustomerTEST.ser");

        for (int i = 0; i < customers.size(); i++) {
            assertEquals(customers.get(i).socialSecurityNumber, testList1.get(i).socialSecurityNumber);
            assertEquals(customers.get(i).name, testList1.get(i).name);
            assertEquals(customers.get(i).latestPaymentDate, testList1.get(i).latestPaymentDate);
        }
    }

    @Test
    public void serializeAndDeSerializeTEST(){
        addToTestList();
        IOUtil.serialize("customersTEST.ser", testList1);
        IOUtil.deSerialize("customersTEST.ser");

        for (int i = 0; i < customers.size(); i++) {
            assertEquals(customers.get(i).socialSecurityNumber, testList1.get(i).socialSecurityNumber);
            assertEquals(customers.get(i).name, testList1.get(i).name);
            assertEquals(customers.get(i).latestPaymentDate, testList1.get(i).latestPaymentDate);
            assertNotEquals(customers.get(i).socialSecurityNumber, testList1.get(i+1).socialSecurityNumber);
            assertNotEquals(customers.get(i).name, testList1.get(i+1).name);
            assertNotEquals(customers.get(i).latestPaymentDate, testList1.get(i+1).latestPaymentDate);
        }
    }

    @Test
    public void searchForCustomerTEST(){
        Gym.setTest(true);
        assertEquals(gym.searchForMember(testList1), testList1.get(0));
        assertNotEquals(gym.searchForMember(testList1), testList1.get(1));
    }

    @Test
    public void createNewMemberTEST(){
        Gym.setTest(true);
        customers.addAll(testList1);
        gym.createNewMember("CustomerTEST.ser", testList1);
        assertNotEquals(testList1.size(), customers.size());
        assertEquals(testList1.size()-1, customers.size());
    }

}
