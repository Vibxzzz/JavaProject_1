import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class UserUtils {
    public static void sortUsersByID(List<User> userList) {
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u1.getID(), u2.getID());
            }
        });
    }

    public static int firstAvailableId(ArrayList<User> user_list){
        int num = 1;

        if (user_list.isEmpty()){
            return num;
        }else{
            for (User user : user_list) {
                if (user.getID() == num){
                    num++;
                }else if (num < user.getID()){
                    return num;
                }
            }
            return num;
        }
    }


}