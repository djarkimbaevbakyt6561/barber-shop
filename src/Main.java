import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.InputMismatchException;
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
                if (currentUser.getRole() == Roles.CLIENT) {
                    if(LocalTime.now().getHour() < 13 || LocalTime.now().getHour() > 18){
                        System.out.println("Барбер шоп сейчас закрыть приходите завтра в 13:00!");
                        isLoggedIn = false;
                        exit = true;
                    } else {
                        while (isLoggedIn) {
                            try {
                                System.out.println("""
                                    1.Постричься
                                    2.Проверить баланс
                                    3.Добавить депозит
                                    4.Отменить бронь
                                    5.Уйти""");
                                System.out.print("Введите действие:");
                                int num = scanner.nextInt();
                                switch (num) {
                                    case 1 -> {
                                        boolean found = false;
                                        for (Reservation reservation : management.reservations) {
                                            if (reservation.booker.getEmail().equals(currentUser.getEmail())) {
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (!found) {
                                            scanner.nextLine();
                                            Haircut choosedHaircut = null;
                                            if (currentUser.getGender().equals("male")) {
                                                System.out.println("Стрижки мужские:");
                                                for (int i = 0; i < management.maleHaircuts.length; i++) {
                                                    System.out.println((i + 1) + ". " + management.maleHaircuts[i]);
                                                }
                                                System.out.print("Выберите стрижку по названию: ");
                                                String haircut = scanner.nextLine();
                                                for (Haircut maleHaircut : management.maleHaircuts) {
                                                    if (maleHaircut.name.equals(haircut)) {
                                                        choosedHaircut = maleHaircut;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                System.out.println("Стрижки женские:");
                                                for (int i = 0; i < management.femaleHaircuts.length; i++) {
                                                    System.out.println((i + 1) + ". " + management.femaleHaircuts[i]);
                                                }
                                                System.out.print("Выберите стрижку по названию: ");
                                                String haircut = scanner.nextLine();
                                                for (Haircut femaleHaircut : management.femaleHaircuts) {
                                                    if (femaleHaircut.name.equals(haircut)) {
                                                        choosedHaircut = femaleHaircut;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (choosedHaircut != null) {
                                                if (currentUser.getBalance().compareTo(new BigDecimal(choosedHaircut.price)) >= 0) {
                                                    while (true) {
                                                        System.out.print("Введите время (от 13 до 18): ");
                                                        int hours = scanner.nextInt();

                                                        if (hours < 13 || hours > 18) {
                                                            System.out.println("В это время мы не работаем!");
                                                        } else {
                                                            LocalTime enteredTime = LocalTime.of(hours, 0);
                                                            boolean foundReservation = false;
                                                            for (Reservation reservation : management.reservations) {
                                                                if (reservation.hours.getHour() == enteredTime.getHour()) {
                                                                    foundReservation = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (foundReservation) {
                                                                System.out.println("В это время уже забронировали!");
                                                            } else {
                                                                Reservation newReservation = new Reservation();
                                                                newReservation.hours = enteredTime;
                                                                newReservation.haircut = choosedHaircut;
                                                                newReservation.booker = currentUser;
                                                                management.addReservations(newReservation);
                                                                System.out.println("Вы успешно забронировали стрижку " + choosedHaircut.name + " на время " + enteredTime.getHour() + ":00 до " + (enteredTime.getHour() + 1) + ":00");
                                                                break;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Недостаточно средсв!");
                                                }
                                            } else {
                                                System.out.println("Неправильно введенная стрижка!");
                                            }
                                        } else {
                                            System.out.println("Вы уже забронировали стрижку!");
                                        }

                                    }
                                    case 2 -> System.out.println("Ваш баланс составляет: " + currentUser.getBalance());
                                    case 3 -> {
                                        while (true) {
                                            System.out.print("Введите сумму: ");
                                            scanner.nextLine();
                                            if (scanner.hasNextInt()) {
                                                int depositNum = scanner.nextInt();
                                                currentUser.addBalance(depositNum);
                                                System.out.println("Вы успешно добавили " + depositNum + " coма на ваш баланс!");
                                                break;
                                            } else {
                                                System.out.println("Введите число!");
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        Reservation foundReservation = null;
                                        for (Reservation reservation : management.reservations) {
                                            if (reservation.booker.getEmail().equals(currentUser.getEmail())) {
                                                foundReservation = reservation;
                                                break;
                                            }
                                        }
                                        if (foundReservation != null) {
                                            management.deleteReservation(currentUser.getEmail());
                                            currentUser.addBalance(foundReservation.haircut.price);
                                            System.out.println("Бронь успешно отменена!");
                                        } else {
                                            System.out.println("Забронируйте стрижку прежде чем отменять её!");
                                        }
                                    }
                                    case 5 -> {
                                        isLoggedIn = false;
                                        exitUser = false;
                                        System.out.println("Вы успешно вышли");
                                        scanner.nextLine();
                                    }
                                    default -> System.out.println("Введите правильное число!");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Введите число!");
                                scanner.nextLine();
                            }

                        }
                    }

                } else {
                    while (isLoggedIn) {
                        try {
                            System.out.println("""
                                    1.Все брони
                                    2.Отменить бронь
                                    3.Добавить стрижку
                                    4.Выйти""");
                            System.out.print("Введите действие: ");
                            int num = scanner.nextInt();
                            switch (num) {
                                case 1 -> {
                                    if (management.reservations.length == 0) {
                                        System.out.println("Броней нет.");
                                    } else {
                                        System.out.println("Все брони: ");
                                        for (int i = 0; i < management.reservations.length; i++) {
                                            System.out.println((i + 1) + ": " + management.reservations[i]);
                                        }
                                    }

                                }
                                case 2 -> {
                                    if (management.reservations.length == 0) {
                                        System.out.println("Броней нет.");
                                    } else {
                                        scanner.nextLine();
                                        System.out.println("Все брони: ");
                                        for (int i = 0; i < management.reservations.length; i++) {
                                            System.out.println((i + 1) + ": " + management.reservations[i]);
                                        }
                                        System.out.println("Выберите email бронировщика: ");
                                        String email = scanner.nextLine();
                                        boolean found = false;
                                        for (Reservation reservation : management.reservations) {
                                            if (reservation.booker.getEmail().equals(email)) {
                                                management.deleteReservation(email);
                                                found = true;
                                                System.out.println("Бронь успешно удалена!");
                                                break;
                                            }
                                        }
                                        if(!found){
                                            System.out.println("Неправильный email!");
                                        }
                                    }
                                }
                                case 3 -> {
                                    scanner.nextLine();
                                    while (true){
                                        try {
                                            System.out.println("Какую стрижку хотите добавить: (1) Женскую (2) Мужскую");
                                            int choice = scanner.nextInt();
                                            if (choice == 1) {
                                                scanner.nextLine();
                                                Haircut newHaircut = new Haircut();
                                                System.out.print("Введите название стрижки: ");
                                                String name = scanner.nextLine();
                                                if (name.isEmpty()) {
                                                    throw new VoidException();
                                                }
                                                newHaircut.name = name;
                                                System.out.print("Введите цену: ");
                                                newHaircut.price = scanner.nextInt();
                                                management.addFemaleHaircut(newHaircut);
                                                System.out.println("Вы успешно добавили женскую стрижку!");
                                                break;
                                            } else if (choice == 2) {
                                                Haircut newHaircut = new Haircut();
                                                System.out.print("Введите название стрижки: ");
                                                String name = scanner.nextLine();
                                                if (name.isEmpty()) {
                                                    throw new VoidException();
                                                }
                                                newHaircut.name = name;
                                                System.out.println("Введите цену: ");
                                                newHaircut.price = scanner.nextInt();
                                                management.addMaleHaircut(newHaircut);
                                                System.out.println("Вы успешно добавили мужскую стрижку!");
                                                break;
                                            } else {
                                                System.out.println("Введите правильное число!");
                                            }

                                        } catch (InputMismatchException e) {
                                            System.out.println("Введите число!");
                                            scanner.nextLine();
                                        } catch (VoidException e) {
                                            System.out.println("Значение не должно быть пустым!");
                                        }
                                    }

                                }
                                case 4 -> {
                                    isLoggedIn = false;
                                    exitUser = false;
                                    System.out.println("Вы успешно вышли");
                                    scanner.nextLine();
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Введите число!");
                        }
                    }
                }

            }
        }

    }
}