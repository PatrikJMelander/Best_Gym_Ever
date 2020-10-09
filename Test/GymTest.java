import org.junit.jupiter.api.Test;

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

    public ArrayList <Person> addToTestList() {
        ArrayList<Person> testList1 = new ArrayList<>();
        Person testPerson1 = new Person("1111111111", "Test Person1", "2020-01-01");
        Person testPerson2 = new Person("2222222222", "Test Person2", "2002-02-02");
        Person testPerson3 = new Person("3333333333", "Test person med flera namn3", "2020-03-03");
        testList1.add(testPerson1);
        testList1.add(testPerson2);
        testList1.add(testPerson3);
        return testList1;
    }
    @Test
    public void printFileToListTest() {


        ArrayList<Person> test2 = Gym.printFileToList("Test.txt");


        for (int i = 0; i < test2.size(); i++) {
            assertEquals(test2.get(i).persoNr, testList1.get(i).persoNr);
            assertEquals(test2.get(i).name, testList1.get(i).name);
            assertEquals(test2.get(i).latestPaymentDate, testList1.get(i).latestPaymentDate);
        }
    }
    @Test
        public void searchForPersonNrOrNameTest(){
        assertEquals(Gym.searchForPersonNrOrName("Test Person1", "Test.txt"), testList1.get(0).latestPaymentDate);
        assertEquals(Gym.searchForPersonNrOrName("1111111111", "Test.txt"), testList1.get(0).latestPaymentDate);
        assertEquals(Gym.searchForPersonNrOrName("5555555555", "Test.txt"), null);
        assertEquals(Gym.searchForPersonNrOrName("NamnSomInteFinnsIListan", "Test.txt"), null);

    }
}
