package singleton;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogClass {

    private static LogClass instance;

    private LogClass(){}

    public static LogClass getInstance() {
        if (instance == null) {
            instance = new LogClass();
        }
        return instance;
    }

    public void classLogg(Object object, String info) {
        if (object != null) {
            String className = object.getClass().getSimpleName();
            System.out.println("Class: " + className
                    + "\nInfo: " + info
                    + "\nDate: " + LocalDateTime.now());
        } else{
            System.out.println("Class: null");
        }
    }
}
