import java.util.Scanner;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private Roles role;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static User register(Management management) {
        User newUser = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя: ");
        newUser.setFirstName(scanner.nextLine());
        System.out.print("Фамилия: ");
        newUser.setLastName(scanner.nextLine());
        System.out.print("Введите номер телефона: ");
        while (true) {
            String num = scanner.nextLine();
            if (num.length() == 10 && num.matches("\\d+")) {
                newUser.setPhoneNumber(num);
                break;
            } else {
                System.out.println("Введите правильный номер телефона!");
            }

        }
        System.out.print("Введите пол (male/female): ");
        while (true) {
            String gender = scanner.nextLine();
            if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) {
                newUser.setGender(gender);
                break;
            } else {
                System.out.println("Введите правильный гендер!");
            }

        }
        System.out.print("Введите email: ");
        newUser.setEmail(scanner.nextLine());
        boolean foundUserEmail = false;
        for (User user : management.users) {
            if (user.getEmail().equals(newUser.getEmail())) {
                foundUserEmail = true;
                break;
            }
        }
        if (newUser.getFirstName().isEmpty() || newUser.getLastName().isEmpty() || newUser.getEmail().isEmpty()) {
            System.out.println("Поля не должны быть пустыми!❌");
        } else if (!newUser.getEmail().contains("@gmail.com")) {
            System.out.println("Не корректый адрес эл.почты!❌");
        } else if (foundUserEmail) {
            System.out.println("Пользователь с таким email уже существует!❌");
        } else {
            System.out.print("Введите пароль: ");
            newUser.setPassword(scanner.nextLine());
            if (newUser.getPassword().length() < 4) {
                System.out.println("Пароль долден быть не менее 4 символа!❌");
                return null;

            } else {
                newUser.setRole(Roles.CLIENT);
                System.out.println("Аккаунт успешно создан!✅");
                return newUser;
            }
        }
        return null;


    }

    public static User login(User[] users) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                System.out.println("Вы успешно вошли в аккаунт!✅");
                return user;
            }
        }
        System.out.println("Не верный пароль или логин❌");
        return null;

    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String gender, Roles role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
    }

    public User() {
    }
}
