/**
 * Created by Patrik Melander
 * Date: 2020-10-09
 * Time: 15:19
 * Project: Gym
 * Copyright: MIT
 */
public class GymDemo {
    GymDemo (){
        Gym gym = new Gym();
        gym.createListFromFile("Customer.txt", gym.customers);
        MenuSystem.mainMenu();

    }
    public static void main(String[] args) {
        GymDemo gymDemo = new GymDemo();

    }
}
