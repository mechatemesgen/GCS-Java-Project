import java.util.*;

public class StudentCounselingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearScreen();
            showWelcomePage();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    adminLogin(scanner);
                    break;
                case 2:
                    counselorLogin(scanner);
                    break;
                case 3:
                    studentLogin(scanner);
                    break;
                case 4:
                    showHelpPage(scanner);
                    break;
                case 5:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    // **************************************************************

    // Data for students and counselors
    private static Map<String, String> studentData = new HashMap<>();
    private static Map<String, String> counselorData = new HashMap<>();
    private static Map<String, List<Message>> messageStore = new HashMap<>();
    private static Map<String, String> messages = new HashMap<>();
    private static final String adminId = "Admin";

    // Message class to represent each message
    private static class Message {
        String senderId;
        String content;
        boolean canReply;

        public Message(String senderId, String content, boolean canReply) {
            this.senderId = senderId;
            this.content = content;
            this.canReply = canReply;
        }

        @Override
        public String toString() {
            return "Message: " + content + "\nCan Reply: " + canReply + "\n";
        }
    }

    // Helper to display users for selection
    private static String selectUser(Scanner scanner, Map<String, String> userData) {
        int index = 1;
        System.out.printf("%-5s %-20s %-20s%n", "No.", "Name", "ID");
        System.out.println("--------------------------------------");
        for (Map.Entry<String, String> entry : userData.entrySet()) {
            System.out.printf("%-5d %-20s %-20s%n", index, entry.getValue().split(",")[0], entry.getKey());
            index++;
        }
        System.out.println("");
        System.out.print("Enter the user number to send a message (0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        if (choice == 0) {
            return null;
        }
        if (choice > 0 && choice <= userData.size()) {
            return new ArrayList<>(userData.keySet()).get(choice - 1);
        } else {
            System.out.println("Invalid choice. Try again.");
            return selectUser(scanner, userData);
        }
    }

    // Sending a message
    private static void sendMessage(Scanner scanner, String senderId, String recipient, String content, boolean canReply) {
        messageStore.putIfAbsent(recipient, new ArrayList<>());
        messageStore.get(recipient).add(new Message(senderId, content, canReply));
        String recipientName = getSenderName(recipient);
        System.out.println("");
        System.out.println("Message sent successfully to " + recipientName + "!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // From student to counselor
    private static void sendMessageToCounselorFromStudent(Scanner scanner, String studentId) {
        System.out.println("Select a counselor to send the message:");
        String counselorId = selectUser(scanner, counselorData);
        if (counselorId == null) {
            return;
        }
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, studentId, counselorId, message, true);
    }

    // From counselor to student
    private static void sendMessageToStudentFromCounselor(Scanner scanner, String counselorId) {
        System.out.println("Select a student to send the message:");
        String studentId = selectUser(scanner, studentData);
        if (studentId == null) {
            return;
        }
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, counselorId, studentId, message, true);
    }

    // From counselor to admin
    private static void sendMessageToAdminFromCounselor(Scanner scanner, String counselorId) {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, counselorId, "Admin", message, false);
    }

    // From counselor to counselor
    private static void sendMessageToCounselorFromCounselor(Scanner scanner, String counselorId) {
        System.out.println("Select a counselor to send the message:");
        String recipientId = selectUser(scanner, counselorData);
        if (recipientId == null) {
            return;
        }
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, counselorId, recipientId, message, true);
    }

    // From admin to student
    private static void sendMessageToStudentFromAdmin(Scanner scanner) {
        System.out.println("Select a student to send the message:");
        String studentId = selectUser(scanner, studentData);
        if (studentId == null) {
            return;
        }
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, adminId, studentId, message, false);
    }

    // From admin to counselor
    private static void sendMessageToCounselorFromAdmin(Scanner scanner) {
        System.out.println("Select a counselor to send the message:");
        String counselorId = selectUser(scanner, counselorData);
        if (counselorId == null) {
            return;
        }
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Send Message |**********");
        System.out.println("**********************************");
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(scanner, adminId, counselorId, message, false);
    }
    // Viewing messages for students
    private static void messageForStudent(Scanner scanner, String studentId) {
        List<Message> messages = messageStore.getOrDefault(studentId, new ArrayList<>());
        if (messages.isEmpty()) {
        System.out.println("No messages for you.");
        } else {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Messages |**************");
        System.out.println("**********************************");
        System.out.println("Messages for Student (" + studentId + "):");
        for (Message message : messages) {
            String senderName = getSenderName(message.senderId);
            System.out.println("From: " + senderName);
            System.out.println(message);
        }
        }
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }

    // Viewing messages for counselors
    private static void messageForCounselor(Scanner scanner, String counselorId) {
        List<Message> messages = messageStore.getOrDefault(counselorId, new ArrayList<>());
        if (messages.isEmpty()) {
        System.out.println("No messages for you.");
        } else {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Messages |**************");
        System.out.println("**********************************");
        System.out.println("Messages for Counselor (" + counselorId + "):");
        for (Message message : messages) {
            String senderName = getSenderName(message.senderId);
            System.out.println("From: " + senderName);
            System.out.println(message);
        }
        }
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }

    // Viewing messages for admin
    private static void messageForAdmin(Scanner scanner) {
        List<Message> messages = messageStore.getOrDefault("Admin", new ArrayList<>());
        if (messages.isEmpty()) {
        System.out.println("No messages for you.");
        } else {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| Messages |**************");
        System.out.println("**********************************");
        System.out.println("Messages for Admin:");
        for (Message message : messages) {
            String senderName = getSenderName(message.senderId);
            System.out.println("From: " + senderName);
            System.out.println(message);
        }
        }
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }

        // Helper method to get sender name
        private static String getSenderName(String senderId) {
            if (studentData.containsKey(senderId)) {
                String[] studentDetails = studentData.get(senderId).split(",");
                return studentDetails[0];
            } else if (counselorData.containsKey(senderId)) {
                String[] counselorDetails = counselorData.get(senderId).split(",");
                return counselorDetails[0];
            } else if ("Admin".equals(senderId)) {
                return "Admin";
            } else if (userData.containsKey(senderId)) {
                String[] userDetails = userData.get(senderId).split(",");
                return userDetails[0];
            } else {
                return "Unknown";
            }
        }



        

    // **********************************************************************
    




    private static void showWelcomePage() {
        System.out.println("*********************************");
        System.out.println("*******| Welcome to SGCS |*******");
        System.out.println("*********************************");
        System.out.println("1. Login as Admin");
        System.out.println("2. Login as Counselor");
        System.out.println("3. Login as Student");
        System.out.println("4. Help");
        System.out.println("5. Exit");
        System.out.print("Please select an option: ");
    }


    // ***********| Help Page |***********
    private static void showHelpPage(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*********| Help Dashboard |*******");
            System.out.println("**********************************");
            System.out.println("1. Forget Password");
            System.out.println("2. Check Status");
            System.out.println("3. FAQ");
            System.out.println("4. Go Back");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    forgetPassword(scanner);
                    break;
                case 2:
                    checkStatus(scanner);
                    break;
                case 3:
                    showFAQ(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    
    private static Map<String, String> userData = new HashMap<>();

    private static void forgetPassword(Scanner scanner) {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("*******| Forget Password |********");
        System.out.println("**********************************");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ID Number: ");
        String id = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();
        System.out.print("Enter Position: ");
        String position = scanner.nextLine();

        // Store user data
        // System.out.print("Enter Password: ");
        // String password = scanner.nextLine();
        userData.put(id, name + "," + id + "," + email + "," + position + "," );

        // Logic to send forget password request to admin
        if (requestStatus.containsKey(id)) {
            System.out.println("Your request status: " + requestStatus.get(id).split(",")[2]);
        } else {
            requestStatus.put(id, name + "," + id + ",Pending");
            System.out.println("Waiting for approval...");
        }

        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }


// *****************************************************************

private static void checkStatus(Scanner scanner) {
    clearScreen();
    System.out.println("**********************************");
    System.out.println("*********| Check Status |*********");
    System.out.println("**********************************");
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter ID Number: ");
    String id = scanner.nextLine();
    // Logic to check status of forget password request
    if (requestStatus.containsKey(id)) {
        String[] statusDetails = requestStatus.get(id).split(",");
        if (statusDetails[0].equals(name)) {
            System.out.println("");
            System.out.println("Your request status: " + statusDetails[2]);
            if ("Approved".equals(statusDetails[2])) {
                String[] userDetails;
                if (studentData.containsKey(id)) {
                    userDetails = studentData.get(id).split(",");
                } else if (counselorData.containsKey(id)) {
                    userDetails = counselorData.get(id).split(",");
                } else {
                    System.out.println("No user data found for the given ID.");
                    System.out.print("Press Enter to go back...");
                    scanner.nextLine();
                    return;
                }
                System.out.println("");
                System.out.println("-------------------------------------------------");
                System.out.println("User ID: " + id);
                System.out.println("Password: " + userDetails[2]);
                System.out.println("Email: " + userDetails[1]);
                System.out.println("-------------------------------------------------");
                System.out.println("");
                System.out.println("Please remember your credentials.");
            }
        } else {
            System.out.println("No request found for the given Name and ID.");
        }
    } else {
        System.out.println("No request found for the given ID.");
    }
    System.out.print("Press Enter to go back...");
    scanner.nextLine();
}

//***************************************************** */ 
    private static void showFAQ(Scanner scanner) {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("************| FAQ |***************");
        System.out.println("**********************************");
        System.out.println("Q1: How to reset my password?");
        System.out.println("A1: Go to Help -> Forget Password and fill the form.");
        System.out.println("Q2: How to check the status of my request?");
        System.out.println("A2: Go to Help -> Check Status and enter your details.");
        System.out.println("Q3: How to contact admin?");
        System.out.println("A3: Send a message through your dashboard.");
        System.out.println("Press Enter to go back...");
        scanner.nextLine();
        return;
    }
    
    
    

// ***********| Student Side |***********

private static void studentLogin(Scanner scanner) {
    clearScreen();
    System.out.println("*********************************");
    System.out.println("******| Student Login Page |*****");
    System.out.println("*********************************");
    System.out.print("Enter ID: ");
    String id = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    if (authenticateStudent(id, password)) {
        showStudentDashboard(scanner, id);
    } else {
        System.out.println("Invalid credentials. Please try again.");
    }
}

private static boolean authenticateStudent(String id, String password) {
    // Check if the student exists in the studentData map
    if (studentData.containsKey(id)) {
        String[] studentDetails = studentData.get(id).split(",");
        return studentDetails[2].equals(password); // Check if the password matches
    }
    return false;
}



private static void showStudentDashboard(Scanner scanner, String studentId) {
        while (true) {
            clearScreen();
            String studentName = studentData.get(studentId).split(",")[0];
            System.out.println("**********************************");
            System.out.println("*******| Student Dashboard |******");
            System.out.println("**********************************");
            System.out.println("Welcome, " + studentName + "!");
            System.out.println("");
            System.out.println("1. View Available Counselors");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. My Appointments");
            System.out.println("4. Send Message to Counselor");
            System.out.println("5. View Messages");
            System.out.println("6. View Notifications");
            System.out.println("7. Logout");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewCounselorsAvailability(scanner);
                    break;
                case 2:
                    scheduleAppointment(scanner);
                    break;
                case 3:
                    viewStudentAppointments(scanner);
                    break;
                case 4:
                    sendMessageToCounselorFromStudent(scanner, studentId);
                    break;
                case 5:
                    messageForStudent(scanner, studentId);
                    break;
                case 6:
                    viewStudentNotifications(scanner);
                    break;
                case 7:
                    System.out.println("");
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(2000); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }






        private static void viewCounselorsAvailability(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("****| Counselors Availability |***");
            System.out.println("**********************************");

            if (counselorAvailability.isEmpty()) {
            System.out.println("No counselors availability set.");
            } else {
            System.out.printf("%-5s %-20s %-30s %-20s%n", "No.", "Date", "Availability", "Counselor");
            System.out.println("--------------------------------------------------------------------");
            int index = 1;
            for (Map.Entry<String, String> entry : counselorAvailability.entrySet()) {
                String[] details = entry.getValue().split(": ");
                System.out.printf("%-5d %-20s %-30s %-20s%n", index, entry.getKey(), details[1], details[0]);
                index++;
            }
            }

            System.out.println("--------------------------------------------------------------------");
            System.out.println("");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
        }

        
       
        private static void scheduleAppointment(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("****| Schedule Appointment |******");
            System.out.println("**********************************");

            if (counselorAvailability.isEmpty()) {
                System.out.println("No counselors available for appointments.");
            } else {
                System.out.printf("%-5s %-20s %-30s %-20s%n", "No.", "Date", "Availability", "Counselor");
                System.out.println("--------------------------------------------------------------------");
                int index = 1;
                for (Map.Entry<String, String> entry : counselorAvailability.entrySet()) {
                    String[] details = entry.getValue().split(": ");
                    System.out.printf("%-5d %-20s %-30s %-20s%n", index, entry.getKey(), details[1], details[0]);
                    index++;
                }

                System.out.println("--------------------------------------------------------------------");
                System.out.println("");
                System.out.print("Enter the number of the counselor to schedule an appointment with: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice > 0 && choice <= counselorAvailability.size()) {
                    String selectedDate = (String) counselorAvailability.keySet().toArray()[choice - 1];
                    String[] details = counselorAvailability.get(selectedDate).split(": ");
                    String counselorName = details[0];
                    System.out.println("");
                    System.out.println("You have selected " + counselorName + " on " + selectedDate + " from " + details[1]);
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("");
                    System.out.print("Enter your ID to confirm the appointment: ");
                    String studentId = scanner.nextLine();
                    // Logic to store the appointment
                    studentAppointments.put(studentId, counselorName + "," + selectedDate + "," + details[1]);
                    System.out.println("");
                    System.out.println("Appointment scheduled successfully with " + counselorName + " on " + selectedDate);
                    System.out.println("--------------------------------------------------------------------");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.print("Press Enter to go back...");
            scanner.nextLine();
        }

    private static void viewStudentAppointments(Scanner scanner) {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("****| View Appointments |*********");
        System.out.println("**********************************");
        System.out.print("Enter your ID: ");
        String studentId = scanner.nextLine();

        if (studentAppointments.containsKey(studentId)) {
            String[] appointmentDetails = studentAppointments.get(studentId).split(",");
            System.out.println("Counselor: " + appointmentDetails[0]);
            System.out.println("Date: " + appointmentDetails[1]);
            System.out.println("Time: " + appointmentDetails[2]);
            System.out.println("");
            System.out.print("Do you want to cancel this appointment? (yes/no): ");
            String cancelChoice = scanner.nextLine();
            if ("yes".equalsIgnoreCase(cancelChoice)) {
                studentAppointments.remove(studentId);
                System.out.println("Appointment canceled successfully.");
            }
        } else {
            System.out.println("No appointments found.");
        }

        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }

    // store student appointments
    
    private static Map<String, String> studentAppointments = new HashMap<>();


   
    // **********************************************************************

        
        private static void viewStudentNotifications(Scanner scanner) {
        if (announcementsToStudents.isEmpty() && announcementsToBoth.isEmpty()) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| Notifications |*********");
            System.out.println("**********************************");
            System.out.println("");
            System.out.println("No notifications available.");
        } else {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| Notifications |*********");
            System.out.println("**********************************");
            for (Map.Entry<String, String> entry : announcementsToStudents.entrySet()) {
            System.out.println("Announcement!");
            System.out.println("Received at: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("");
            System.out.println("Message: " + entry.getValue());
            System.out.println("--------------------------------------");
            System.out.println("");
            }
            for (Map.Entry<String, String> entry : announcementsToBoth.entrySet()) {
            System.out.println("Announcement!");
            System.out.println("Received at: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("");
            System.out.println("Message: " + entry.getValue());
            System.out.println("--------------------------------------");
            System.out.println("");
            }
        }
        System.out.println("");
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
        }


// ***********| Counselor Side |***********
private static void counselorLogin(Scanner scanner) {
    clearScreen();
    System.out.println("*********************************");
    System.out.println("****| Counselor Login Page |*****");
    System.out.println("*********************************");
    System.out.print("Enter ID: ");
    String id = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    if (authenticateCounselor(id, password)) {
        showCounselorDashboard(scanner, id);
    } else {
        System.out.println("Invalid credentials. Please try again.");
    }
}

private static boolean authenticateCounselor(String id, String password) {
    // Check if the counselor exists in the counselorData map
    if (counselorData.containsKey(id)) {
        String[] counselorDetails = counselorData.get(id).split(",");
        return counselorDetails[3].equals(password); // Check if the password matches
    }
    return false;
}



private static void showCounselorDashboard(Scanner scanner, String counselorId) {
    String counselorName = counselorData.get(counselorId).split(",")[0];
        while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*****| Counselor Dashboard |******");
            System.out.println("**********************************");
            System.out.println("Welcome, " + counselorName + "!");
            System.out.println("");
            System.out.println("1. My Availability");
            System.out.println("2. Send Message");
            System.out.println("3. View Appointments");
            System.out.println("4. View Messages");
            System.out.println("5. View Notifications");
            System.out.println("6. Logout");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    updateAvailability(scanner);
                    break;
                case 2:
                    counselorSendsMessage(scanner, counselorId);
                    break;
                case 3:
                    viewAppointments(scanner);
                    break;
                case 4:
                    messageForCounselor(scanner, counselorId);
                    break;
                case 5:
                    viewNotifications(scanner);
                    break;
                case 6:
                    System.out.println("");
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(2000); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }




      
        private static Map<String, String> counselorAvailability = new HashMap<>();

        private static void updateAvailability(Scanner scanner) {
            while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*****| Update Availability |******");
            System.out.println("**********************************");

            if (counselorAvailability.isEmpty()) {
                System.out.println("No availabilities set.");
            } else {
                System.out.printf("%-5s %-20s %-30s %-20s%n", "No.", "Date", "Availability", "Counselor");
                System.out.println("--------------------------------------------------------------------");
                int index = 1;
                for (Map.Entry<String, String> entry : counselorAvailability.entrySet()) {
                    String[] details = entry.getValue().split(": ");
                    System.out.printf("%-5d %-20s %-30s %-20s%n", index, entry.getKey(), details[1], details[0]);
                    index++;
                }
            }

            System.out.println("");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("1. Add Availability");
            System.out.println("2. Edit Availability");
            System.out.println("3. Delete Availability");
            System.out.println("4. Go Back");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                addAvailability(scanner);
                break;
                case 2:
                editAvailability(scanner);
                break;
                case 3:
                deleteAvailability(scanner);
                break;
                case 4:
                return;
                default:
                System.out.println("Invalid choice. Please try again.");
            }
            }
        }

        private static void addAvailability(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("******| Add Availability |********");
            System.out.println("**********************************");
            System.out.print("Enter Your Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            System.out.print("Enter Availability (e.g., 9 AM - 5 PM): ");
            String availability = scanner.nextLine();

            // Store availability with counselor's name
            counselorAvailability.put(date, name + ": " + availability);
            System.out.println("");
            System.out.println("Availability updated for " + date);
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }

        private static void editAvailability(Scanner scanner) {
            if (counselorAvailability.isEmpty()) {
            System.out.println("No availabilities to edit.");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
            return;
            }

            System.out.println("");
            System.out.print("Select Availability to edit: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice > 0 && choice <= counselorAvailability.size()) {
            String date = (String) counselorAvailability.keySet().toArray()[choice - 1];
            System.out.print("Enter new Availability (e.g., 9 AM - 5 PM): ");
            String availability = scanner.nextLine();
            counselorAvailability.put(date, counselorAvailability.get(date).split(":")[0] + ": " + availability);
            System.out.println("Availability updated for " + date);
            } else {
            System.out.println("Invalid choice.");
            }
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }

        private static void deleteAvailability(Scanner scanner) {
            if (counselorAvailability.isEmpty()) {
            System.out.println("No availabilities to delete.");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
            return;
            }

            System.out.println("");
            System.out.print("Select Availability to delete: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice > 0 && choice <= counselorAvailability.size()) {
            String date = (String) counselorAvailability.keySet().toArray()[choice - 1];
            counselorAvailability.remove(date);
            System.out.println("");
            System.out.println("Availability deleted for " + date);
            } else {
            System.out.println("Invalid choice.");
            }
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }


        
        private static void counselorSendsMessage(Scanner scanner, String counselorId) {
            while (true) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("********| Send Message |**********");
                System.out.println("**********************************");
                System.out.println("1. To Student");
                System.out.println("2. To Admin");
                System.out.println("3. To Counselor");
                System.out.println("4. Go Back");
                System.out.print("Select recipient: ");
                int recipientChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (recipientChoice == 1) {
                    sendMessageToStudentFromCounselor(scanner, counselorId);
                } else if (recipientChoice == 2) {
                    sendMessageToAdminFromCounselor(scanner, counselorId);
                } else if (recipientChoice == 3) {
                    sendMessageToCounselorFromCounselor(scanner, counselorId);
                } else if (recipientChoice == 4) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }


   
    // **********************************************************************

private static void viewAppointments(Scanner scanner) {
        // Display appointments logic here
        clearScreen();
        System.out.println("**********************************");
        System.out.println("******| View Appointments |*******");
        System.out.println("**********************************");
        System.out.println("Appointments displayed.");
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
     }

   
    // **********************************************************************
        
        private static void viewNotifications(Scanner scanner) {
        if (announcementsToCounselors.isEmpty() && announcementsToBoth.isEmpty()) {
            System.out.println("No notifications available.");
        } else {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| Notifications |*********");
            System.out.println("**********************************");
            for (Map.Entry<String, String> entry : announcementsToCounselors.entrySet()) {
            System.out.println("Announcement!");
            System.out.println("Received at: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("");
            System.out.println("Message: " + entry.getValue());
            System.out.println("--------------------------------------");
            System.out.println("");
            }
            for (Map.Entry<String, String> entry : announcementsToBoth.entrySet()) {
            System.out.println("Announcement!");
            System.out.println("Received at: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("");
            System.out.println("Message: " + entry.getValue());
            System.out.println("--------------------------------------");
            System.out.println("");
            }
        }
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
        }

        private static void adminLogin(Scanner scanner) {
        clearScreen();
        System.out.println("*********************************");
        System.out.println("******| Admin Login Page |*******");
        System.out.println("*********************************");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticateAdmin(username, password)) {
            showAdminDashboard(scanner);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static boolean authenticateAdmin(String username, String password) {
        // Dummy authentication for demonstration
        return "admin".equals(username) && "admin".equals(password);
    }



    // ***********| Admin Side |***********
    private static void showAdminDashboard(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*******| Admin Dashboard |********");
            System.out.println("**********************************");
            System.out.println("1. Requests");
            System.out.println("2. Add Students");
            System.out.println("3. Add Counselors");
            System.out.println("4. Manage Users");
            System.out.println("5. View Messages");
            System.out.println("6. Send Message");
            System.out.println("7. Post Announcement");
            System.out.println("8. Logout");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleRequests(scanner);
                    break;
                case 2:
                    addStudent(scanner);
                    break;
                case 3:
                    addCounselor(scanner);
                    break;
                case 4:
                    manageUsers(scanner);
                    break;
                case 5:
                    messageForAdmin(scanner);
                    break;
                case 6:
                    sendMessageFromAdmin(scanner);
                    break;
                case 7:
                    postAnnouncement(scanner);
                    break;
                case 8:
                    System.out.println("");
                    System.out.println("Logging out...");
                    try {
                        Thread.sleep(2000); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
    
    }
}

   
    // **********************************************************************
 private static void sendMessageFromAdmin(Scanner scanner) {
            while (true) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("********| Send Message |**********");
                System.out.println("**********************************");
                System.out.println("1. To Student");
                System.out.println("2. To Counselor");
                System.out.println("3. Go Back");
                System.out.print("Select recipient: ");
                int recipientChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (recipientChoice) {
                    case 1:
                        sendMessageToStudentFromAdmin(scanner);
                        break;
                    case 2:
                        sendMessageToCounselorFromAdmin(scanner);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
            }
        }




// ***********| store the status of the request |***********

private static Map<String, String> requestStatus = new HashMap<>();

private static void handleRequests(Scanner scanner) {
    clearScreen();
    System.out.println("**********************************");
    System.out.println("*********| Requests |*************");
    System.out.println("**********************************");
    // Fetch requests from userData
    if (userData.isEmpty()) {
        System.out.println("No requests available.");
        System.out.print("Press Enter to go back...");
        scanner.nextLine();
        return;
    }

    int index = 1;
    System.out.printf("%-5s %-20s %-20s %-30s %-20s %-20s%n", "No.", "Name", "ID", "Email", "Position", "Status");
    System.out.println("------------------------------------------------------------------------------------------------------");
    for (Map.Entry<String, String> entry : userData.entrySet()) {
        String[] userDetails = entry.getValue().split(",");
        String status = requestStatus.getOrDefault(entry.getKey(), "Pending");
        System.out.printf("%-5d %-20s %-20s %-30s %-20s %-20s%n", index, userDetails[0], userDetails[1], userDetails[2], userDetails[3], status);
        index++;
    }

    System.out.println("");
    System.out.print("Enter the request number to handle (0 to go back): ");
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume newline

    if (choice == 0) {
        return;
    } else if (choice > 0 && choice <= userData.size()) {
        System.out.println(" ");
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Please select an option: ");
        int action = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String selectedUserId = (String) userData.keySet().toArray()[choice - 1];
        String[] userDetails = userData.get(selectedUserId).split(",");
        if (action == 1) {
            requestStatus.put(selectedUserId, userDetails[0] + "," + userDetails[1] + ",Approved");
            System.out.println("Request approved.");
            System.out.println("Do you want to add this user as a Student or Counselor?");
            System.out.println("1. Add as Student");
            System.out.println("2. Add as Counselor");
            System.out.print("Please select an option: ");
            int addChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (addChoice == 1) {
                addStudent(scanner);
            } else if (addChoice == 2) {
                addCounselor(scanner);
            } else {
                System.out.println("Invalid choice.");
            }

            // Display user ID and password to the requester
            System.out.println("User ID: " + userDetails[1]);
            System.out.println("Password: " + userDetails[2]);
        } else if (action == 2) {
            requestStatus.put(selectedUserId, userDetails[0] + "," + userDetails[1] + ",Rejected");
            System.out.println("Request rejected.");
        } else {
            System.out.println("Invalid choice.");
        }
    } else {
        System.out.println("Invalid choice.");
    }
}

// *****************************************************************************


        private static void addStudent(Scanner scanner) {
            while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*******| Add Student |************");
            System.out.println("**********************************");
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Phone/Email: ");
            String contact = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Store student data
            studentData.put(id, name + "," + contact + "," + password);

            // Registration logic here

            System.out.println("");
            System.out.println("Student registered successfully.");
            System.out.println("*********************************");
            System.out.println("");

            System.out.println("1. Register another student");
            System.out.println("2. Go back");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 2) {
                return;
            } else if (choice != 1) {
                System.out.println("Invalid choice. Going back...");
                return;
            }
            }
        }



        

        private static void addCounselor(Scanner scanner) {
            while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("******| Add Counselor |***********");
            System.out.println("**********************************");
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Position: ");
            String position = scanner.nextLine();
            System.out.print("Enter Phone/Email: ");
            String contact = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Store counselor data
            counselorData.put(id, name + "," + position + "," + contact + "," + password);

            // Registration logic here
            System.out.println("");
            System.out.println("Counselor registered successfully.");
            System.out.println("*********************************");
            System.out.println("");

            System.out.println("1. Register another counselor");
            System.out.println("2. Go back");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 2) {
                return;
            } else if (choice != 1) {
                System.out.println("Invalid choice. Going back...");
                return;
            }
            }
        }



            private static void manageUsers(Scanner scanner) {
                while (true) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("******| Manage Users |************");
                System.out.println("**********************************");
                System.out.println("1. Manage Students");
                System.out.println("2. Manage Counselors");
                System.out.println("3. Go Back");
                System.out.print("Please select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                    manageStudents(scanner);
                    break;
                    case 2:
                    manageCounselors(scanner);
                    break;
                    case 3:
                    return;
                    default:
                    System.out.println("Invalid choice. Please try again.");
                }
                }
            }

            private static void manageStudents(Scanner scanner) {
                while (true) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("******| Manage Students |*********");
                System.out.println("**********************************");

                if (studentData.isEmpty()) {
                    System.out.println("No students registered.");
                    System.out.print("Press Enter to go back...");
                    scanner.nextLine();
                    return;
                } else {
                    System.out.printf("%-5s %-20s %-20s %-30s%n", "No.", "Name", "ID", "Contact");
                    System.out.println("---------------------------------------------------------------");
                    int index = 1;
                    for (Map.Entry<String, String> entry : studentData.entrySet()) {
                    String[] studentDetails = entry.getValue().split(",");
                    System.out.printf("%-5d %-20s %-20s %-30s%n", index, studentDetails[0], entry.getKey(), studentDetails[1]);
                    index++;
                    }
                }

                System.out.print("Enter the student number to edit/delete (0 to go back): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 0) {
                    return;
                } else if (choice > 0 && choice <= studentData.size()) {
                    String selectedStudentId = (String) studentData.keySet().toArray()[choice - 1];
                    System.out.println("1. Edit");
                    System.out.println("2. Delete");
                    System.out.print("Please select an option: ");
                    int action = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (action == 1) {
                    editStudent(scanner, selectedStudentId);
                    } else if (action == 2) {
                    deleteStudent(scanner, selectedStudentId);
                    } else {
                    System.out.println("Invalid choice.");
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
                }
            }

            private static void editStudent(Scanner scanner, String studentId) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("**********| Edit Student |********");
                System.out.println("**********************************");
                System.out.print("Enter new Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new Contact: ");
                String contact = scanner.nextLine();
                System.out.print("Enter new Password: ");
                String password = scanner.nextLine();

                studentData.put(studentId, name + "," + contact + "," + password);
                System.out.println("Student details updated successfully.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
            }

            private static void deleteStudent(Scanner scanner, String studentId) {
                studentData.remove(studentId);
                System.out.println("Student deleted successfully.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
            }

            private static void manageCounselors(Scanner scanner) {
                while (true) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("*****| Manage Counselors |********");
                System.out.println("**********************************");

                if (counselorData.isEmpty()) {
                    System.out.println("No counselors registered.");
                    System.out.print("Press Enter to go back...");
                    scanner.nextLine();
                    return;
                } else {
                    System.out.printf("%-5s %-20s %-20s %-30s %-20s%n", "No.", "Name", "ID", "Position", "Contact");
                    System.out.println("--------------------------------------------------------------------------");
                    int index = 1;
                    for (Map.Entry<String, String> entry : counselorData.entrySet()) {
                    String[] counselorDetails = entry.getValue().split(",");
                    System.out.printf("%-5d %-20s %-20s %-30s %-20s%n", index, counselorDetails[0], entry.getKey(), counselorDetails[1], counselorDetails[2]);
                    index++;
                    }
                }

                System.out.print("Enter the counselor number to edit/delete (0 to go back): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 0) {
                    return;
                } else if (choice > 0 && choice <= counselorData.size()) {
                    String selectedCounselorId = (String) counselorData.keySet().toArray()[choice - 1];
                    System.out.println("1. Edit");
                    System.out.println("2. Delete");
                    System.out.print("Please select an option: ");
                    int action = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (action == 1) {
                    editCounselor(scanner, selectedCounselorId);
                    } else if (action == 2) {
                    deleteCounselor(scanner, selectedCounselorId);
                    } else {
                    System.out.println("Invalid choice.");
                    }
                } else {
                    System.out.println("Invalid choice.");
                }
                }
            }

            private static void editCounselor(Scanner scanner, String counselorId) {
                clearScreen();
                System.out.println("**********************************");
                System.out.println("*********| Edit Counselor |*******");
                System.out.println("**********************************");
                System.out.print("Enter new Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new Position: ");
                String position = scanner.nextLine();
                System.out.print("Enter new Contact: ");
                String contact = scanner.nextLine();
                System.out.print("Enter new Password: ");
                String password = scanner.nextLine();

                counselorData.put(counselorId, name + "," + position + "," + contact + "," + password);
                System.out.println("Counselor details updated successfully.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
            }

            private static void deleteCounselor(Scanner scanner, String counselorId) {
                counselorData.remove(counselorId);
                System.out.println("Counselor deleted successfully.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
            }

   
    // **********************************************************************
       

    // ***********| Store announcements |***********
    private static Map<String, String> announcementsToStudents = new HashMap<>();
    private static Map<String, String> announcementsToCounselors = new HashMap<>();
    private static Map<String, String> announcementsToBoth = new HashMap<>();

    private static void postAnnouncement(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("******| Post Announcement |*******");
            System.out.println("**********************************");
            System.out.println("1. To All Students");
            System.out.println("2. To All Counselors");
            System.out.println("3. To Both Students and Counselors");
            System.out.println("4. Go Back");
            System.out.println("");
            System.out.print("Select recipient: ");
            int recipientChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (recipientChoice < 1 || recipientChoice > 3) {
                System.out.println("Invalid choice. Going back...");
                return;
            }

            System.out.println("");
            System.out.print("Enter Announcement: ");
            String announcement = scanner.nextLine();

            // Store announcement with a unique ID
            String announcementId = "ANN" + (announcementsToStudents.size() + announcementsToCounselors.size() + announcementsToBoth.size() + 1);

            // Logic to send announcement based on recipient choice
            switch (recipientChoice) {
                case 1:
                    announcementsToStudents.put(announcementId, announcement);
                    for (String studentId : studentData.keySet()) {
                        messages.put(studentId, "Announcement: " + announcement);
                    }
                    break;
                case 2:
                    announcementsToCounselors.put(announcementId, announcement);
                    for (String counselorId : counselorData.keySet()) {
                        messages.put(counselorId, "Announcement: " + announcement);
                    }
                    break;
                case 3:
                    announcementsToBoth.put(announcementId, announcement);
                    for (String studentId : studentData.keySet()) {
                        messages.put(studentId, "Announcement: " + announcement);
                    }
                    for (String counselorId : counselorData.keySet()) {
                        messages.put(counselorId, "Announcement: " + announcement);
                    }
                    break;
                case 4:
                    return;
            }

            System.out.println("");
            System.out.println("Announcement posted successfully.");
            System.out.println("*********************************");
            System.out.println("");
            System.out.println("1. Post another announcement");
            System.out.println("2. Go back");
            System.out.print("Please select an option: ");
            int nextAction = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (nextAction == 2) {
                return;
            } else if (nextAction != 1) {
                System.out.println("Invalid choice. Going back...");
                return;
            }
        }
    }

    private static void clearScreen() {
        // Clear the console using ANSI escape code
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
