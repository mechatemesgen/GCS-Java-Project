import java.util.*;

class User {
    String name;
    String email;
    String id;
    String password;
    List<String> messages = new ArrayList<>();

    public User(String name, String email, String id, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void receiveMessage(String message) {
        messages.add(message);
    }

    public void viewMessages() {
        System.out.println("\n==============================");
        System.out.println(" Messages ");
        System.out.println("==============================");
        for (String message : messages) {
            System.out.println(message);
        }
        if (messages.isEmpty()) {
            System.out.println("No messages.");
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }
}

class Student extends User {
    List<Appointment> appointments = new ArrayList<>();

    public Student(String name, String email, String id, String password) {
        super(name, email, id, password);
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("\n==============================");
        System.out.println(" Appointment Scheduled ");
        System.out.println("==============================");
        System.out.println(appointment);
        System.out.println("\n");
    }

    public void cancelAppointment(Appointment appointment) {
        appointments.remove(appointment);
        System.out.println("\n==============================");
        System.out.println(" Appointment Canceled ");
        System.out.println("==============================");
        System.out.println(appointment);
        System.out.println("\n");
    }

    public void sendMessage(Counselor counselor, String message) {
        counselor.receiveMessage("From: " + this.name + "\nMessage: " + message);
        System.out.println("Message sent.");
    }

    public void receiveNotification(String notification) {
        System.out.println("\n==============================");
        System.out.println(" Notification ");
        System.out.println("==============================");
        System.out.println(notification);
        System.out.println("\n");
    }
}

class Counselor extends User {
    String specialization;
    List<Appointment> schedule = new ArrayList<>();

    public Counselor(String name, String email, String id, String password, String specialization) {
        super(name, email, id, password);
        this.specialization = specialization;
    }

    public void updateAvailability(Appointment appointment) {
        schedule.add(appointment);
    }

    public void manageAppointments() {
        System.out.println("\n==============================");
        System.out.println(" Appointments ");
        System.out.println("==============================");
        for (Appointment a : schedule) {
            System.out.println(a);
        }
        System.out.println("\n");
    }

    public void sendNotification(Student student, String notification) {
        student.receiveMessage(notification);
    }

    public void sendMessage(Admin admin, String message) {
        admin.receiveMessage("From: " + this.name + "\nMessage: " + message);
        System.out.println("Message sent.");
    }
}
class Admin extends User {
    List<Student> students = new ArrayList<>();
    List<Counselor> counselors = new ArrayList<>();

    public Admin() {
        super("admin", "admin@admin.com", "admin", "admin123");
    }

    public void registerUser(User user) {
        if (user instanceof Student) {
            students.add((Student) user);
        } else if (user instanceof Counselor) {
            counselors.add((Counselor) user);
        }
        System.out.println("\n==============================");
        System.out.println(" User Registered ");
        System.out.println("==============================");
        System.out.println(user);
        System.out.println("\n");
    }

    public void verifyCounselor(Counselor counselor) {
        System.out.println("\n==============================");
        System.out.println(" Counselor Verified ");
        System.out.println("==============================");
        System.out.println(counselor);
        System.out.println("\n");
    }

    public void manageUsers() {
        while (true) {
            System.out.println("\n==============================");
            System.out.println(" Manage Users ");
            System.out.println("==============================");
            System.out.println("1. View Students");
            System.out.println("2. View Counselors");
            System.out.println("3. Add User");
            System.out.println("4. Edit User");
            System.out.println("5. Delete User");
            System.out.println("6. Back to Dashboard");
            System.out.println("==============================");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(StudentCounselingSystem.scanner.nextLine());

            switch (choice) {
                case 1:
                    viewStudents();
                    break;
                case 2:
                    viewCounselors();
                    break;
                case 3:
                    addUser();
                    break;
                case 4:
                    editUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewStudents() {
        System.out.println("\n==============================");
        System.out.println(" Student List ");
        System.out.println("==============================");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("\n");
    }

    private void viewCounselors() {
        System.out.println("\n==============================");
        System.out.println(" Counselor List ");
        System.out.println("==============================");
        for (Counselor c : counselors) {
            System.out.println(c);
        }
        System.out.println("\n");
    }

    private void addUser() {
        System.out.println("\n==============================");
        System.out.println(" Add User ");
        System.out.println("==============================");
        System.out.println("1. Add Student");
        System.out.println("2. Add Counselor");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(StudentCounselingSystem.scanner.nextLine());

        switch (choice) {
            case 1:
                StudentCounselingSystem.registerStudent();
                break;
            case 2:
                StudentCounselingSystem.registerCounselor();
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void editUser() {
        System.out.println("\n==============================");
        System.out.println(" Edit User ");
        System.out.println("==============================");
        System.out.println("1. Edit Student");
        System.out.println("2. Edit Counselor");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(StudentCounselingSystem.scanner.nextLine());

        switch (choice) {
            case 1:
                editStudent();
                break;
            case 2:
                editCounselor();
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void editStudent() {
        System.out.println("\n==============================");
        System.out.println(" Student List ");
        System.out.println("==============================");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        System.out.print("Choose a student to edit (enter number): ");
        int studentIndex = Integer.parseInt(StudentCounselingSystem.scanner.nextLine()) - 1;

        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Student student = students.get(studentIndex);
        System.out.print("Enter new name: ");
        String name = StudentCounselingSystem.scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = StudentCounselingSystem.scanner.nextLine();
        student.updateProfile(name, email);
        System.out.println("Student profile updated.");
    }

    private void editCounselor() {
        System.out.println("\n==============================");
        System.out.println(" Counselor List ");
        System.out.println("==============================");
        for (int i = 0; i < counselors.size(); i++) {
            System.out.println((i + 1) + ". " + counselors.get(i));
        }
        System.out.print("Choose a counselor to edit (enter number): ");
        int counselorIndex = Integer.parseInt(StudentCounselingSystem.scanner.nextLine()) - 1;

        if (counselorIndex < 0 || counselorIndex >= counselors.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Counselor counselor = counselors.get(counselorIndex);
        System.out.print("Enter new name: ");
        String name = StudentCounselingSystem.scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = StudentCounselingSystem.scanner.nextLine();
        counselor.updateProfile(name, email);
        System.out.println("Counselor profile updated.");
    }

    private void deleteUser() {
        System.out.println("\n==============================");
        System.out.println(" Delete User ");
        System.out.println("==============================");
        System.out.println("1. Delete Student");
        System.out.println("2. Delete Counselor");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(StudentCounselingSystem.scanner.nextLine());

        switch (choice) {
            case 1:
                deleteStudent();
                break;
            case 2:
                deleteCounselor();
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void deleteStudent() {
        System.out.println("\n==============================");
        System.out.println(" Student List ");
        System.out.println("==============================");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        System.out.print("Choose a student to delete (enter number): ");
        int studentIndex = Integer.parseInt(StudentCounselingSystem.scanner.nextLine()) - 1;

        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        students.remove(studentIndex);
        System.out.println("Student deleted.");
    }

    private void deleteCounselor() {
        System.out.println("\n==============================");
        System.out.println(" Counselor List ");
        System.out.println("==============================");
        for (int i = 0; i < counselors.size(); i++) {
            System.out.println((i + 1) + ". " + counselors.get(i));
        }
        System.out.print("Choose a counselor to delete (enter number): ");
        int counselorIndex = Integer.parseInt(StudentCounselingSystem.scanner.nextLine()) - 1;

        if (counselorIndex < 0 || counselorIndex >= counselors.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        counselors.remove(counselorIndex);
        System.out.println("Counselor deleted.");
    }
}

class Appointment {
    String date;
    String time;
    Counselor counselor;

    public Appointment(String date, String time, Counselor counselor) {
        this.date = date;
        this.time = time;
        this.counselor = counselor;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Time: " + time + ", Counselor: " + counselor.name;
    }
}

public class StudentCounselingSystem {
    static Scanner scanner = new Scanner(System.in);
    static Admin admin = new Admin();
    static List<Student> students = new ArrayList<>();
    static List<Counselor> counselors = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            clearScreen();
            System.out.println("\n==============================");
            System.out.println(" Student Counseling System Menu ");
            System.out.println("==============================");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Counselor");
            System.out.println("3. Login as Student");
            System.out.println("4. Exit");
            System.out.println("==============================");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            delayAndClearScreen();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    counselorLogin();
                    break;
                case 3:
                    studentLogin();
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        delayAndClearScreen();

        if (username.equals(admin.id) && password.equals(admin.password)) {
            adminDashboard();
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    static void adminDashboard() {
        while (true) {
            System.out.println("\n==============================");
            System.out.println(" Admin Dashboard ");
            System.out.println("==============================");
            System.out.println("1. Add New Student");
            System.out.println("2. Add New Counselor");
            System.out.println("3. Manage Users");
            System.out.println("4. View Messages");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    delayAndClearScreen();
                    registerStudent();
                    break;
                case 2:
                    delayAndClearScreen();
                    registerCounselor();
                    break;
                case 3:
                    delayAndClearScreen();
                    admin.manageUsers();
                    break;
                case 4:
                    delayAndClearScreen();
                    admin.viewMessages();
                    break;
                case 5:
                    delayAndClearScreen();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void registerStudent() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Student student = new Student(name, email, id, password);
        students.add(student);
        admin.registerUser(student);
    }

    static void registerCounselor() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Counselor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();

        Counselor counselor = new Counselor(name, email, id, password, specialization);
        counselors.add(counselor);
        admin.registerUser(counselor);
    }

    static void studentLogin() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        delayAndClearScreen();

        for (Student student : students) {
            if (student.id.equals(id) && student.password.equals(password)) {
                studentDashboard(student);
                return;
            }
        }
        System.out.println("Invalid credentials. Try again.");
    }

    static void counselorLogin() {
        System.out.print("Enter Counselor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        delayAndClearScreen();

        for (Counselor counselor : counselors) {
            if (counselor.id.equals(id) && counselor.password.equals(password)) {
                counselorDashboard(counselor);
                return;
            }
        }
        System.out.println("Invalid credentials. Try again.");
    }

    static void studentDashboard(Student student) {
        while (true) {
            System.out.println("\n==============================");
            System.out.println(" Student Dashboard ");
            System.out.println("==============================");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. View Appointments");
            System.out.println("4. Send Message to Counselor");
            System.out.println("5. View Notifications");
            System.out.println("6. View Messages");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    delayAndClearScreen();
                    scheduleAppointment(student);
                    break;
                case 2:
                    delayAndClearScreen();
                    cancelAppointment(student);
                    break;
                case 3:
                    delayAndClearScreen();
                    viewAppointments(student);
                    break;
                case 4:
                    delayAndClearScreen();
                    sendMessageToCounselor(student);
                    break;
                case 5:
                    delayAndClearScreen();
                    viewNotifications(student);
                    break;
                case 6:
                    delayAndClearScreen();
                    student.viewMessages();
                    break;
                case 7:
                    delayAndClearScreen();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void scheduleAppointment(Student student) {
        System.out.println("\nAvailable Counselors:");
        for (int i = 0; i < counselors.size(); i++) {
            Counselor counselor = counselors.get(i);
            System.out.println((i + 1) + ". " + counselor.name + " (Specialization: " + counselor.specialization + ")");
        }
        System.out.print("Choose a counselor (enter number): ");
        int counselorIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (counselorIndex < 0 || counselorIndex >= counselors.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Counselor counselor = counselors.get(counselorIndex);

        System.out.print("Enter Appointment Date (e.g., 2024-12-20): ");
        String date = scanner.nextLine();
        System.out.print("Enter Appointment Time (e.g., 10:00 AM): ");
        String time = scanner.nextLine();

        Appointment appointment = new Appointment(date, time, counselor);
        student.scheduleAppointment(appointment);
        counselor.updateAvailability(appointment);
    }

    static void cancelAppointment(Student student) {
        System.out.println("\nYour Scheduled Appointments:");
        for (int i = 0; i < student.appointments.size(); i++) {
            Appointment appointment = student.appointments.get(i);
            System.out.println((i + 1) + ". " + appointment);
        }

        if (student.appointments.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return;
        }

        System.out.print("Choose an appointment to cancel (enter number): ");
        int appointmentIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (appointmentIndex < 0 || appointmentIndex >= student.appointments.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Appointment appointment = student.appointments.get(appointmentIndex);
        student.cancelAppointment(appointment);
        appointment.counselor.schedule.remove(appointment);
    }

    static void viewAppointments(Student student) {
        System.out.println("\nYour Scheduled Appointments:");
        for (Appointment appointment : student.appointments) {
            System.out.println(appointment);
        }

        if (student.appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        }
    }

    static void sendMessageToCounselor(Student student) {
        System.out.println("\nAvailable Counselors:");
        for (int i = 0; i < counselors.size(); i++) {
            Counselor counselor = counselors.get(i);
            System.out.println((i + 1) + ". " + counselor.name);
        }

        System.out.print("Choose a counselor to message (enter number): ");
        int counselorIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (counselorIndex < 0 || counselorIndex >= counselors.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Counselor counselor = counselors.get(counselorIndex);

        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        student.sendMessage(counselor, message);
    }

    static void viewNotifications(Student student) {
        System.out.println("No new notifications.");
    }

    static void counselorDashboard(Counselor counselor) {
        while (true) {
            System.out.println("\n==============================");
            System.out.println(" Counselor Dashboard ");
            System.out.println("==============================");
            System.out.println("1. View Appointments");
            System.out.println("2. Update Availability");
            System.out.println("3. View Notifications");
            System.out.println("4. Send Message to Student");
            System.out.println("5. Send Message to Admin");
            System.out.println("6. View Messages");
            System.out.println("7. Log Out");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    delayAndClearScreen();
                    counselor.manageAppointments();
                    break;
                case 2:
                    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Appointment Time (HH:MM): ");
                    String time = scanner.nextLine();
                    Appointment appointment = new Appointment(date, time, counselor);
                    counselor.updateAvailability(appointment);
                    System.out.println("Availability updated.");
                    break;
                case 3:
                    delayAndClearScreen();
                    System.out.println("No new notifications.");
                    break;
                case 4:
                    delayAndClearScreen();
                    sendMessageToStudent(counselor);
                    break;
                case 5:
                    delayAndClearScreen();
                    sendMessageToAdmin(counselor);
                    break;
                case 6:
                    delayAndClearScreen();
                    counselor.viewMessages();
                    break;
                case 7:
                    delayAndClearScreen();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void sendMessageToStudent(Counselor counselor) {
        System.out.println("\nAvailable Students:");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". " + student.name);
        }

        System.out.print("Choose a student to message (enter number): ");
        int studentIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Student student = students.get(studentIndex);

        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        counselor.sendNotification(student, message);
        System.out.println("Message sent.");
    }

    static void sendMessageToAdmin(Counselor counselor) {
        System.out.print("Enter your message to Admin: ");
        String message = scanner.nextLine();

        counselor.sendMessage(admin, message);
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void delayAndClearScreen() {
        try {
            Thread.sleep(2000); // 2 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearScreen();
    }
}
