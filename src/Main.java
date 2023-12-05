import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Management management = new Management();
    private static User currentUser = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        boolean isLoggedIn = false;
        boolean exitUser = false;
        while (!exit) {
            while (!exitUser) {
                System.out.println("""
                        1. Регистрация
                        2. Вход
                        3. Выход""");
                System.out.print("Выберите команду: ");
                String num = scanner.nextLine();
                switch (num) {
                    case "1" -> {
                        User registerUser = User.register(management);
                        if (registerUser != null) {
                            management.addClient(registerUser);
                        }
                    }
                    case "2" -> {
                        currentUser = User.login(management.users);
                        if (currentUser != null) {
                            isLoggedIn = true;
                            exitUser = true;
                        }
                    }
                    case "3" -> {
                        System.out.println("Вы вышли из программы!✅");
                        exitUser = true;
                        exit = true;
                    }
                }
            }
            if (isLoggedIn) {
                while (isLoggedIn) {
                    if (currentUser.getGender().equals("male")) {
                        System.out.println("Стрижки мужские:");
                        for (int i = 0; i < management.maleHaircuts.length; i++) {
                            System.out.println((i + 1) + ". " + management.maleHaircuts[i]);
                        }
                        System.out.print("Выберите стрижку по названию (0 Выйти): ");
                        String haircut = scanner.nextLine();
                        if (haircut.equals("0")) {
                            System.out.println("Вы успешно вышли!");
                            isLoggedIn = false;
                        }
                        boolean found = false;
                        for (Haircut maleHaircut : management.maleHaircuts) {
                            if (maleHaircut.name.equals(haircut)) {
                                found = true;
                                break;
                            }
                        }
                        if(found){
                            while (true){
                                System.out.print("Введите время в формате ЧЧ:ММ: ");
                                String timeInput = scanner.nextLine();
                                try {
                                    LocalTime enteredTime = LocalTime.parse(timeInput);
                                    System.out.println(enteredTime.getHour());
                                } catch (DateTimeParseException e) {
                                    System.out.println("Неверный формат времени.");
                                }
                            }
                        } else {
                            System.out.println("Неправильно введенная стрижка!");
                        }
                    }
                }


            }
        }

    }
}