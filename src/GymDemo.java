import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Patrik Melander
 * Date: 2020-10-09
 * Time: 15:19
 * Project: Gym
 * Copyright: MIT
 */
public class GymDemo implements Serializable {
    GymDemo (){
        Gym gym = new Gym();
        Gym.deSerialize();
        gym.createListFromFile("Customer.txt", Gym.customers);
        MenuSystem.mainMenu();

    }
    public static void main(String[] args) {
        GymDemo gymDemo = new GymDemo();

    }
}
