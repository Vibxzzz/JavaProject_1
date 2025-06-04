import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ans = 1000;
        boolean ok = false;
        
        //Load users from txt file
        ArrayList<User> user_list = loadUsersFromFile("users.txt");

        while (ans != 0) { 
            
            UserUtils.sortUsersByID(user_list);
            updateFile(user_list);
            DisplayMenu();
            ans = 1000;
            ok = false;

            //input validation
            do { 
                String temp_ans = scanner.nextLine();

                if (!temp_ans.isEmpty()){
                    try {
                        ans = Integer.parseInt(temp_ans);
                        ok = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter only numbers!");
                    }
                }else{
                    System.out.println("Please enter a number!");
                }
            } while (!ok);
            
            if (ans == 1){
                //Take user's info
                String name;
                String pt;
                Double pr;
                
                do{
                    System.out.print("User Name: ");
                    name = scanner.nextLine();
                    if (name.isEmpty()){
                        System.out.println("Please enter a valid name!");
                    }
                }while(name.trim().isEmpty());

                do{ 
                    System.out.print("User payment type: ");
                    pt = scanner.nextLine();
                    if (pt.isEmpty()){
                        System.out.println("Please enter a valid payment type!");
                    }
                }while (pt.trim().isEmpty());

                do { 
                    System.out.print("User pay rate: ");
                    try {
                        pr = Double.parseDouble(scanner.nextLine());
                    } catch (NullPointerException | NumberFormatException e ) {
                        pr = 0.0;
                        System.out.println("Please enter a number!");
                    }
                } while (pr <= 0);
                
                //Create user
                User user = new User(name, pt, pr);
                System.out.println("User created successfully!");

                //Find first available id
                user.setID(UserUtils.firstAvailableId(user_list));

                //Add user to the users list
                user_list.add(user);

                //Call the update file method
                updateFile(user_list);
            }else if(ans == 2){
                int i = 1;
                System.out.println("-------------------------------------------------");
                System.out.println("List of all users:");

                for (User user : user_list) {
                    System.out.println(i + ": " + user.getName());
                    i++;
                }
                System.out.println("-------------------------------------------------");
            }else if(ans == 3){
                Boolean found = false;
                int id = 0;

                do { 
                    System.out.print("Provide the user's id to show info: "); 
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number!");
                    }                   
                } while (id <= 0);

                for (User user : user_list) {
                    if (user.getID() == id){
                        System.out.println("User name: " + user.getName());
                        System.out.println("User id: " + user.getID());
                        System.out.println("User payment type: " + user.getPayment_type());
                        System.out.println("User pay rate: " + user.getHourly_payment());
                        System.out.println("User sessions: ");
                        for (WorkSession session : user.getSessions()) {
                            System.out.println("\t" + session.getDate().toString() + " - " + session.gethoursWorked() + " hours worked\n");
                        }
                        found = true;
                        break;
                    }
                }
                if (!found){
                    System.out.println("User with id: " + id + " does not exist");
                }
            }else if(ans == 4){
                Boolean found = false;
                int id = 0;
                
                do { 
                    try {
                        System.out.print("Provide the user's id to add session: ");
                        id = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException e) {
                        System.out.println("Please enter a number!");
                    }
                } while (id <= 0);

                for (User user : user_list) {
                    if (user.getID() == id){
                        found = true;

                        LocalDate newDate = null;
                        while (newDate == null){
                            System.out.print("Enter session date: ");
                            String tempDate = scanner.nextLine();

                            try {
                                newDate = LocalDate.parse(tempDate);
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date or format!");
                                System.out.println("Please enter date in YYYY-MM-dd format");
                            }

                        }

                        System.out.print("Enter the hours that user worked: ");
                        Double hours_worked = Double.parseDouble(scanner.nextLine());

                        WorkSession new_session = new WorkSession(newDate, hours_worked);
                        user.addSession(new_session);
                        System.out.println("Session added successfully!");
                        updateFile(user_list);
                    }
                }
                if (!found){
                    System.out.println("User with id " + id + " does not exist");
                }
            }else if(ans == 5){
                Boolean found = false;
                int index = 0;
                int id = 0;

                do { 
                    try {
                        System.out.print("Provide the user's id to add session: ");
                        id = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException e) {
                        System.out.println("Please enter a number!");
                    }
                } while (id <= 0);

                for (User user : user_list) {
                    if (user.getID() == id){
                        found = true;
                        user_list.remove(index);
                        updateFile(user_list);
                        System.out.println("User deleted successfully");
                        break;
                    }
                    index++;
                }
                if (!found){
                    System.out.println("User with id " + id + " does not exist");
                }
            }else if(ans == 6){
                Boolean found = false;
                int index = 0;
                int id = 0;

                do { 
                    try {
                        System.out.print("Provide the user's id to add session: ");
                        id = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException e) {
                        System.out.println("Please enter a number!");
                    }
                } while (id <= 0);

                for (User user : user_list) {
                    if (user.getID() == id){
                        found = true;
                        System.out.println("Edit name, payment type or payment rate?");
                        System.out.println("a. Name\nb. Payment type\nc. Pay Rate");
                        String edit = scanner.nextLine();
                        if(edit.equals("a")){
                            System.out.println("Current name: " + user.getName());
                            System.out.print("New name: ");
                            user.setName(scanner.nextLine());
                        }else if(edit.equals("b")){
                            System.out.println("Current payment type: " + user.getPayment_type());
                            System.out.print("New payment type: ");
                            user.setPayment_type(scanner.nextLine());
                        }else if(edit.equals("c")){
                            System.out.println("Current hourly rate: " + user.getHourly_payment());
                            System.out.print("New hourly rate: ");
                            user.setHourly_payment(Double.parseDouble(scanner.nextLine()));
                        }

                        updateFile(user_list);
                        System.out.println("User updated successfully");
                        break;
                    }
                    index++;
                }
                if (!found){
                    System.out.println("User with id " + id + " does not exist");
                } 
            }else if(ans == 7){
                String name;
                System.out.print("Enter the name to search for: ");
                do {
                    name = scanner.nextLine();
                    if(name.isEmpty()){
                        System.out.print("Please enter a valid name: ");
                    }
                } while (name.isEmpty());

                boolean found = false;
                
                for (User user : user_list) {
                    if(user.getName().toLowerCase().contains(name.toLowerCase())){
                        found = true;
                        System.out.println("ID: " + user.getID() + " | Full name: " + user.getName());
                    }
                }
                if(!found){
                    System.out.println("No user containing " + name + " was found!");
                }
            }else if(ans == 8){
                Boolean found = false;
                int index = 0;
                int id = 0;

                do { 
                    try {
                        System.out.print("Provide the user's id to show sessions info: ");
                        id = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException e) {
                        System.out.println("Please enter a number!");
                    }
                } while (id <= 0);

                for (User user : user_list) {
                    if (user.getID() == id){
                        found = true;
                        System.out.println("Here are all the session of " + user.getName());
                        for (WorkSession session : user.getSessions()) {
                            System.out.println(session.toString());
                        }
                        break;
                    }
                    index++;
                }
                if (!found){
                    System.out.println("User with id " + id + " does not exist");
                }
            }
        }
    }

    public static void DisplayMenu(){
        System.out.println("0. Exit");
        System.out.println("1. Create User");
        System.out.println("2. View all users");
        System.out.println("3. View specific user's info");
        System.out.println("4. Add session to user");
        System.out.println("5. Delete user");
        System.out.println("6. Edit User's info");
        System.out.println("7. Search user by name");
        System.out.println("8. Show full info for a user's sessions");
    }

    public static void updateFile (ArrayList<User> user_list){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : user_list) {
                writer.write("User name: " + user.getName() + "\n");
                writer.write("User id: " + user.getID() + "\n");
                writer.write(user.getName() + "'s payment type: " + user.getPayment_type() + "\n");
                writer.write(user.getName() + "'s pay rate: " + user.getHourly_payment() + "\n");
                writer.write(user.getName() + "'s Sessions:");
                for (WorkSession session : user.getSessions()){
                    writer.write("\n\t" + session.getDate().toString() + " - " + session.gethoursWorked() + " hours worked");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsersFromFile(String FileName){
        ArrayList<User> UserList = new ArrayList<User>();
        ArrayList<WorkSession> sess_list = new ArrayList<WorkSession>();
        String name = " ";
        String pt = " ";
        Double pr = 0.0;
        int id = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line = reader.readLine();

            while ((line!= null)) {
                if(line.startsWith("User name")){
                    //Store previous (if any) user
                    if (!name.trim().isEmpty()) {
                        User newUser = new User(name, pt, pr);
                        newUser.setID(id);
                        newUser.setSessions(sess_list);
                        UserList.add(newUser);
                        sess_list = new ArrayList<>(); // Reset for next user
                    }

                    //Create new user
                    name = line.substring("User name: ".length() - 1).trim();
                }else if (line.startsWith("User id: ")){
                    String temp2[] = line.split(" ");
                    id = Integer.parseInt(temp2[2]);
                }else if(line.contains("payment type")){
                    String[] temp2 = line.trim().split(" ");
                    pt = temp2[temp2.length - 1];
                }else if(line.contains("pay rate")){
                    String[] temp2 = line.trim().split(" ");
                    pr = Double.parseDouble(temp2[temp2.length - 1]);
                }else if (line.trim().matches("^\\d{4}-\\d{2}-\\d{2}.*")) {
                    // Line starts with a date
                    String[] temp2 = line.trim().split(" ");
                    LocalDate date = LocalDate.parse(temp2[0]);
                    Double hoursWorked = Double.parseDouble(temp2[2]);

                    WorkSession session = new WorkSession(date, hoursWorked);
                    sess_list.add(session);
                }
                line = reader.readLine();
            }
            //last user after the loop ends
            if (!name.equals(" ")) {
                User lastUser = new User(name, pt, pr);
                lastUser.setID(id);
                lastUser.setSessions(sess_list);
                UserList.add(lastUser);
            }               
        } catch (IOException e) {
            e.printStackTrace();
        }

        return UserList;
    }

}