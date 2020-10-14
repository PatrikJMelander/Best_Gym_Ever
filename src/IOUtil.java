import java.io.*;
import java.util.List;

/**
 * Created by Patrik Melander
 * Date: 2020-10-13
 * Time: 09:15
 * Project: Gym
 * Copyright: MIT
 */
public class IOUtil {
    public static void serialize(String fileName, List<Person> listName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(listName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> deSerialize(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Person>) in.readObject();
        } catch (Exception e) {
            System.out.println("Hittar ej listan på medlemmar");
        }
        return null;
    }
}
