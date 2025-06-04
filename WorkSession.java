import java.time.LocalDate;

public class WorkSession {
    private LocalDate date;
    private Double hoursWorked;

    public WorkSession(LocalDate date, Double hoursWorked){
        this.date = date;
        this.hoursWorked = hoursWorked;
    }

    //Getter Methods
    public LocalDate getDate(){
        return date;
    }

    public Double gethoursWorked(){
        return hoursWorked;
    }

    public String toString() {
        return "Date: " + date + ", Hours: " + hoursWorked;
    }
}
