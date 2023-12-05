import java.time.LocalTime;

public class Reservation {
    User booker;
    Haircut haircut;
    LocalTime hours;

    @Override
    public String toString() {
        return "Бронь, бронировщик: " + booker +
                ", стрижка: " + haircut +
                " на время " + hours.getHour() + ":00 до " + (hours.getHour() + 1) + ":00";
    }
}
