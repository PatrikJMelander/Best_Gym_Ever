import java.io.Serializable;

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
        gym.createListFromFile("Customer.txt", Gym.customers, "Customer.ser");
        Gym.customers= IOUtil.deSerialize("Customer.ser");
        MenuSystem.mainMenu();

    }
    public static void main(String[] args) {
        GymDemo gymDemo = new GymDemo();

    }
}
