import java.util.Arrays;

public class Management {
    User[] users = {new User("Barber", "Barberov", "barber@gmail.com", "barber123", "0707109090", "male", Roles.BARBER)};
    Haircut[] maleHaircuts = {new Haircut("Канадка", 300),new Haircut("Горшок", 250),new Haircut("Налысо", 50),new Haircut("Бокс", 500),new Haircut("Топ кнот", 800),new Haircut("Стрелец", 800),new Haircut("Цезарь", 450)};
    Haircut[] femaleHaircuts = {new Haircut("Пикси", 1300),new Haircut("Гаврош", 1400),new Haircut("Налысо красива", 600),new Haircut("Каре", 700),new Haircut("Сессон", 900),new Haircut("Томбой", 1100),new Haircut("Ежик", 300)};
    Reservation[] reservations = new Reservation[0];
    public void addClient(User registerUser) {
        User[] newArray = Arrays.copyOf(users, users.length + 1);
        newArray[newArray.length - 1] = registerUser;
        users = newArray;
    }
    public void addReservations(Reservation reservation) {
        Reservation[] newArray = Arrays.copyOf(reservations, reservations.length + 1);
        newArray[newArray.length - 1] = reservation;
        reservations = newArray;
    }
    public void deleteReservation(String email) {
        Reservation[] newReservations = new Reservation[10000];
        int newIndex = 0;

        for (Reservation reservation : newReservations) {
            if (reservation != null && !(reservation.booker.getEmail().equals(email))) {
                newReservations[newIndex++] = reservation;
            }
        }

        reservations = Arrays.copyOf(newReservations, reservations.length - 1);
    }

    public void addMaleHaircut(Haircut maleHaircut) {
        Haircut[] newArray = Arrays.copyOf(maleHaircuts, maleHaircuts.length + 1);
        newArray[newArray.length - 1] = maleHaircut;
        maleHaircuts = newArray;
    }

    public void addFemaleHaircut(Haircut femaleHaircut) {
        Haircut[] newArray = Arrays.copyOf(femaleHaircuts, femaleHaircuts.length + 1);
        newArray[newArray.length - 1] = femaleHaircut;
        femaleHaircuts = newArray;
    }
}
