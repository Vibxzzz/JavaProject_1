import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String payment_type;
    private Double hourly_payment;

    private List<WorkSession> sessions = new ArrayList<>();


    public User(String name, String payment_type, Double hourly_payment){
        this.name = name;
        this.payment_type = payment_type;
        this.hourly_payment = hourly_payment;
    }

    //Sessions Methods
    public void addSession(WorkSession session){
        sessions.add(session);
    }

    public List<WorkSession> getSessions(){
        return sessions;
    }
    
    public void setSessions(List<WorkSession> list){
        this.sessions = list;
    }

    //User Getter and Setter methods
    public String getName(){
        return name;
    }

    public String getPayment_type(){
        return payment_type;
    }

    public Double getHourly_payment(){
        return hourly_payment;
    }

    public int getID(){
        return id;
    }

    //Setter methods
    public void setName(String name){
        this.name = name;
    }

    public void setPayment_type(String payment_type){
        this.payment_type = payment_type;
    }

    public void setHourly_payment(Double hourly_payment){
        this.hourly_payment = hourly_payment;
    }

    public void setID(int id){
        this.id = id;
    }

    //toString
    public String toString(){
        return "User " + name + " is getting payed " + payment_type;
    }
}
