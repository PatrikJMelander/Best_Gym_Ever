import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Patrik Melander
 * Date: 2020-10-13
 * Time: 22:49
 * Project: Gym
 * Copyright: MIT
 */
public class PersonTest {
    Person person = new Person("8502160000", "Test1", LocalDate.now());


    @Test
    public void registerVisitsTest() throws FileNotFoundException {
        Person.registerVisits(person, "CustomerVisitsTEST.txt");
        Person test = new Person();

        Scanner fileScanner = new Scanner(new FileReader("CustomerVisitsTEST.txt")).useDelimiter(",|\n");

        test.setSocialSecurityNumber(fileScanner.next().trim());
        test.setName(fileScanner.next().trim());
        String dateString = fileScanner.next().trim();
        test.setLatestPaymentDate(LocalDate.parse(dateString));

        assertEquals(person.socialSecurityNumber, test.socialSecurityNumber);
        assertEquals(person.name, test.name);
        assertEquals(person.latestPaymentDate, test.latestPaymentDate);
    }
}
