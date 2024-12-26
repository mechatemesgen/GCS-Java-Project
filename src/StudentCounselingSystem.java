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
        userData.put(id, name + "," + id + "," + email + "," + position);

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
                        System.out.println("Your request status: " + statusDetails[2]);
                    } else {
                        System.out.println("No request found for the given Name and ID.");
                    }
                } else {
                    System.out.println("No request found for the given ID.");
                }
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
            }

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
                    sendMessageToCounselorFromStudent(scanner);
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

    // Store messages sent to counselors from students
    private static Map<String, Map<String, String>> studentToCounselorMessages = new LinkedHashMap<>();

    private static void sendMessageToCounselorFromStudent(Scanner scanner) {
        if (counselorAvailability.isEmpty()) {
            clearScreen();
            System.out.println("*************************************");
            System.out.println("****| Send Message to Counselor |****");
            System.out.println("*************************************");
            System.out.println("");
            System.out.println("No counselors available.");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
            return;
        }

        int index = 1;
        clearScreen();
        System.out.println("**********************************");
        System.out.println("****|  Select User to send   |****");
        System.out.println("**********************************");
        System.out.println("--------------------------------------");
        System.out.printf("%-5s %-20s %-20s%n", "No.", "Name", "Availability");
        System.out.println("--------------------------------------");
        for (Map.Entry<String, String> entry : counselorAvailability.entrySet()) {
            String[] counselorDetails = entry.getValue().split(": ");
            System.out.printf("%-5d %-20s %-20s%n", index, counselorDetails[0], counselorDetails[1]);
            index++;
        }

        System.out.println("");
        System.out.print("Enter the counselor number to send message (0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 0) {
            return;
        } else if (choice > 0 && choice <= counselorAvailability.size()) {
            String selectedDate = (String) counselorAvailability.keySet().toArray()[choice - 1];
            String[] counselorDetails = counselorAvailability.get(selectedDate).split(": ");
            String counselorName = counselorDetails[0];
            clearScreen();
            System.out.println("*************************************");
            System.out.println("****| Send Message to Counselor |****");
            System.out.println("*************************************");
            System.out.println("Selected Counselor: " + counselorName);
            System.out.println("");
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();
            String studentId = "student"; // Assuming the student ID is "student" for demonstration

            // Store message for the counselor from the student
            studentToCounselorMessages.putIfAbsent(studentId, new LinkedHashMap<>());
            studentToCounselorMessages.get(studentId).put(counselorName, message);

            messages.put(counselorName, "From: " + studentId + ", Message: " + message);
            System.out.println("");
            System.out.println("Message sent to " + counselorName + ".");
        } else {
            System.out.println("Invalid choice.");
        }
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        }





         
       
        private static void messageForStudent(Scanner scanner, String studentId) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| Messages |**************");
            System.out.println("**********************************");
            boolean hasMessages = false;

            // Display messages from admin
            if (studentMessages.containsKey(studentId)) {
                hasMessages = true;
                System.out.println("");
                System.out.println("------------------------------------");
                System.out.println(">>>>|   Messages from Admin    |<<<<");
                System.out.println("------------------------------------");
                System.out.println("");
                int messageIndex = 1;
                for (String message : studentMessages.getOrDefault(studentId, new ArrayList<>())) {
                    System.out.println(messageIndex + ". Message: " + message);
                    System.out.println("");
                    System.out.println("-----------------------------------");
                    messageIndex++;
                }
            }



            
            // Display messages from counselors
            if (counselorToStudentMessages.containsKey(studentId)) {
                hasMessages = true;
                System.out.println("");
                System.out.println("------------------------------------");
                System.out.println(">>>>| Messages from Counselors |<<<<");
                System.out.println("------------------------------------");
                System.out.println("");
                int messageIndex = 1;
                for (Map.Entry<String, String> entry : counselorToStudentMessages.get(studentId).entrySet()) {
                    String counselorName = entry.getKey();
                    System.out.println(messageIndex + ". From Counselor: " + counselorName);
                    System.out.println("Message: " + entry.getValue());
                    System.out.println("---------------------------------------------");
                    messageIndex++;
                }
            }

            // Display replies from student to counselors
            if (studentToCounselorMessages.containsKey(studentId)) {
                hasMessages = true;
                System.out.println("");
                System.out.println("------------------------------------");
                System.out.println(">>>>| Replies to Counselors |<<<<");
                System.out.println("------------------------------------");
                System.out.println("");
                int messageIndex = 1;
                for (Map.Entry<String, String> entry : studentToCounselorMessages.get(studentId).entrySet()) {
                    String counselorName = entry.getKey();
                    System.out.println(messageIndex + ". To Counselor: " + counselorName);
                    System.out.println("Message: " + entry.getValue());
                    System.out.println("---------------------------------------------");
                    messageIndex++;
                }
            }

            if (!hasMessages) {
                System.out.println("No messages available.");
            } else {
                System.out.print("Enter the message number to reply (0 to go back): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice > 0) {
                    int messageIndex = 1;
                    for (Map.Entry<String, String> entry : counselorToStudentMessages.get(studentId).entrySet()) {
                        if (messageIndex == choice) {
                            String counselorName = entry.getKey();
                            System.out.print("Enter your reply to " + counselorName + ": ");
                            String reply = scanner.nextLine();
                            studentToCounselorMessages.putIfAbsent(studentId, new LinkedHashMap<>());
                            studentToCounselorMessages.get(studentId).put(counselorName, reply);
                            System.out.println("Reply sent to " + counselorName + ".");
                            break;
                        }
                        messageIndex++;
                    }
                }
            }

            System.out.print("Press Enter to go back...");
            scanner.nextLine();
        }
        
        





        
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
                    sendMessage(scanner);
                    break;
                case 3:
                    viewAppointments(scanner);
                    break;
                case 4:
                    messageForCounselor(scanner);
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


        
        private static void sendMessage(Scanner scanner) {
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
                    sendMessageToStudentFromCounselor(scanner);
                } else if (recipientChoice == 2) {
                    sendMessageToAdminFromCounselor(scanner);
                } else if (recipientChoice == 3) {
                    sendMessageToCounselorFromCounselor(scanner);
                } else if (recipientChoice == 4) {
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }



        // store each message sent for each student from counselors
        private static Map<String, String> messages = new LinkedHashMap<>();      
        
        
        
        //store messages sent to studens from counselors  
        private static Map<String, Map<String, String>> counselorToStudentMessages = new LinkedHashMap<>();

        private static void sendMessageToStudentFromCounselor(Scanner scanner) {
            if (studentData.isEmpty()) {
            System.out.println("No students available.");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
            return;
            }

            int index = 1;
            clearScreen();
            System.out.println("**********************************");
            System.out.println("*****| Select User to Send |******");
            System.out.println("**********************************");
            System.out.println("--------------------------------------");
            System.out.printf("%-5s %-20s %-20s%n", "No.", "Name", "ID");
            System.out.println("--------------------------------------");
            for (Map.Entry<String, String> entry : studentData.entrySet()) {
            String[] studentDetails = entry.getValue().split(",");
            System.out.printf("%-5d %-20s %-20s%n", index, studentDetails[0], entry.getKey());
            index++;
            }

            System.out.print("Enter the student number to send message (0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 0) {
            return;
            } else if (choice > 0 && choice <= studentData.size()) {
            String selectedStudentId = (String) studentData.keySet().toArray()[choice - 1];
            clearScreen();
            System.out.println("**********************************");
            System.out.println("***| Send Message to Student |****");
            System.out.println("**********************************");
            String[] selectedStudentDetails = studentData.get(selectedStudentId).split(",");
            System.out.println("Selected Student: " + selectedStudentDetails[0]);
            System.out.println("");
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();
            
            // Store message for the student from the counselor
            counselorToStudentMessages.putIfAbsent(selectedStudentId, new LinkedHashMap<>());
            counselorToStudentMessages.get(selectedStudentId).put("counselor", message);
            
            messages.put(selectedStudentId, "From Counselor: " + message);
            System.out.println("");
            System.out.println("Message sent to " + studentData.get(selectedStudentId).split(",")[0] + ".");
            } else {
            System.out.println("Invalid choice.");
            }
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }






        private static void sendMessageToAdminFromCounselor(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("***| Send Message to Admin |******");
            System.out.println("**********************************");
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();
            String counselorId = "counselor"; // Assuming the counselor ID is "counselor" for demonstration
            counselorToAdminMessages.put(counselorId, "From: " + counselorId + ", Message: " + message);
            System.out.println("");
            System.out.println("Message sent to admin.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
        // Store each message sent to admin from each counselor
        private static Map<String, String> counselorToAdminMessages = new LinkedHashMap<>();


        // Store each message sent to other counselors from one counselor
        private static Map<String, String> counselorToCounselorMessages = new LinkedHashMap<>();

        private static void sendMessageToCounselorFromCounselor(Scanner scanner) {
            if (counselorData.isEmpty()) {
                clearScreen();
                System.out.println("****************************************");
                System.out.println("******| Send Message to Counselor |*****");
                System.out.println("****************************************");
                System.out.println("");
                System.out.println("No counselors available.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
                return;
            }

            int index = 1;
            clearScreen();
            System.out.println("**********************************");
            System.out.println("******| Select User to Send |*****");
            System.out.println("**********************************");
            System.out.println("--------------------------------------");
            System.out.printf("%-5s %-20s %-20s%n", "No.", "Name", "ID");
            System.out.println("--------------------------------------");
           
            for (Map.Entry<String, String> entry : counselorData.entrySet()) {
                String[] counselorDetails = entry.getValue().split(",");
                System.out.printf("%-5d %-20s %-20s%n", index, counselorDetails[0], entry.getKey());
                index++;
            }

            System.out.println("");
            System.out.print("Enter the counselor number to send message (0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= counselorData.size()) {
                String selectedCounselorId = (String) counselorData.keySet().toArray()[choice - 1];
                clearScreen();
                System.out.println("**********************************");
                System.out.println("***| Send Message to Counselor |**");
                System.out.println("**********************************");
                String[] selectedCounselorDetails = counselorData.get(selectedCounselorId).split(",");
                System.out.println("Selected Counselor: " + selectedCounselorDetails[0]);
                System.out.println("");
                System.out.print("Enter your message: ");
                String message = scanner.nextLine();
                counselorToCounselorMessages.put(selectedCounselorId, "Counselor: " + message);
                System.out.println("");
                System.out.println("Message sent to " + counselorData.get(selectedCounselorId).split(",")[0] + ".");
            } else {
                System.out.println("Invalid choice.");
            }
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }


//**************************************************************** */


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

    //*********************************************************** */
            
    private static void messageForCounselor(Scanner scanner) {
        clearScreen();
        System.out.println("**********************************");
        System.out.println("********| View Messages |*********");
        System.out.println("**********************************");

        boolean hasMessages = false;
        List<String> messageKeys = new ArrayList<>();

        // Display messages from students
        int messageIndex = 1;
        for (Map.Entry<String, Map<String, String>> entry : studentToCounselorMessages.entrySet()) {
            for (Map.Entry<String, String> messageEntry : entry.getValue().entrySet()) {
                if (messageEntry.getKey().equals("counselor")) {
                    hasMessages = true;
                    System.out.println(messageIndex + ". From Student: " + entry.getKey());
                    System.out.println("Message: " + messageEntry.getValue());
                    System.out.println("**********************************");
                    messageKeys.add("student:" + entry.getKey());
                    messageIndex++;
                }
            }
        }

        // Display messages from admin
        for (Map.Entry<String, String> entry : counselorToAdminMessages.entrySet()) {
            if (entry.getKey().equals("counselor")) {
                hasMessages = true;
                System.out.println(messageIndex + ". From Admin: " + entry.getKey());
                System.out.println("Message: " + entry.getValue());
                System.out.println("**********************************");
                messageKeys.add("admin:" + entry.getKey());
                messageIndex++;
            }
        }

        // Display messages from other counselors
        for (Map.Entry<String, String> entry : counselorToCounselorMessages.entrySet()) {
            if (entry.getKey().startsWith("counselor")) {
                hasMessages = true;
                System.out.println(messageIndex + ". From Counselor: " + entry.getKey());
                System.out.println("Message: " + entry.getValue());
                System.out.println("**********************************");
                messageKeys.add("counselor:" + entry.getKey());
                messageIndex++;
            }
        }

        if (!hasMessages) {
            System.out.println("No messages available.");
        } else {
            System.out.print("Enter the message number to reply (0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice > 0 && choice <= messageKeys.size()) {
                String[] selectedMessage = messageKeys.get(choice - 1).split(":");
                String recipientType = selectedMessage[0];
                String recipientId = selectedMessage[1];

                System.out.print("Enter your reply: ");
                String reply = scanner.nextLine();

                if (recipientType.equals("student")) {
                    studentToCounselorMessages.putIfAbsent(recipientId, new LinkedHashMap<>());
                    studentToCounselorMessages.get(recipientId).put("counselor", reply);
                    System.out.println("Reply sent to Student: " + recipientId);
                } else if (recipientType.equals("admin")) {
                    counselorToAdminMessages.put("counselor", reply);
                    System.out.println("Reply sent to Admin.");
                } else if (recipientType.equals("counselor")) {
                    counselorToCounselorMessages.put(recipientId, reply);
                    System.out.println("Reply sent to Counselor: " + recipientId);
                }
            }
        }

        System.out.print("Press Enter to go back...");
        scanner.nextLine();
    }

//***************************************************************************** */


        
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
    scanner.nextLine(); // consume newline

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

        private static Map<String, String> studentData = new HashMap<>();

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



        
        private static Map<String, String> counselorData = new HashMap<>();

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






        private static void messageForAdmin(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| My Messages |*********");
            System.out.println("**********************************");

            boolean hasMessages = false;
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                if (counselorData.containsKey(entry.getKey())) {
                    hasMessages = true;
                    String[] counselorDetails = counselorData.get(entry.getKey()).split(",");
                    System.out.println("From: " + counselorDetails[0]);
                    System.out.println("Message: " + entry.getValue());
                    System.out.println("**********************************");
                    System.out.print("Do you want to reply to this message? (yes/no): ");
                    String replyChoice = scanner.nextLine();
                    if ("yes".equalsIgnoreCase(replyChoice)) {
                        System.out.print("Enter your reply: ");
                        String reply = scanner.nextLine();
                        messages.put(entry.getKey(), "Admin: " + reply);
                        System.out.println("Reply sent.");
                    }
                }
            }

            if (!hasMessages) {
                System.out.println("");
                System.out.println("No messages from counselors.");
            }

            System.out.println("");
            System.out.print("Press Enter to go back...");
            scanner.nextLine();
        }




        private static void sendMessageFromAdmin(Scanner scanner) {
            clearScreen();
            System.out.println("**********************************");
            System.out.println("********| Send Message |**********");
            System.out.println("**********************************");
            System.out.println("1. To Student");
            System.out.println("2. To Counselor");
            System.out.print("Select recipient: ");
            int recipientChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (recipientChoice == 1) {
                sendMessageToIndividual(scanner, studentData, "student");
            } else if (recipientChoice == 2) {
                sendMessageToIndividual(scanner, counselorData, "counselor");
            } else {
                System.out.println("Invalid choice.");
            }
        }

        private static void sendMessageToIndividual(Scanner scanner, Map<String, String> userData, String userType) {
            if (userData.isEmpty()) {
                System.out.print("");
                System.out.println("No users available.");
                System.out.print("Press Enter to go back...");
                scanner.nextLine();
                return;
            }

            int index = 1;
            System.out.printf("%-5s %-20s %-20s%n", "No.", "Name", "ID");
            System.out.println("--------------------------------------");
            for (Map.Entry<String, String> entry : userData.entrySet()) {
                String[] userDetails = entry.getValue().split(",");
                System.out.printf("%-5d %-20s %-20s%n", index, userDetails[0], entry.getKey());
                index++;
            }

            System.out.println("");
            System.out.print("Enter the user number to send message (0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= userData.size()) {
                String selectedUserId = (String) userData.keySet().toArray()[choice - 1];
                String[] selectedUserDetails = userData.get(selectedUserId).split(",");
                clearScreen();
                System.out.println("**********************************");
                System.out.println("******| Send Message |************");
                System.out.println("**********************************");
                System.out.println("Selected User: " + selectedUserDetails[0] + " (ID: " + selectedUserId + ")");
                System.out.println("");
                System.out.print("Enter your message: ");
                String message = scanner.nextLine();

                if (userType.equals("student")) {
                studentMessages.putIfAbsent(selectedUserId, new ArrayList<>());
                studentMessages.get(selectedUserId).add(message);
                } else if (userType.equals("counselor")) {
                counselorMessages.put(selectedUserId, message);
                }

                System.out.println("");
                System.out.println("Message sent successfully to " + selectedUserDetails[0] + ".");
                System.out.println("*********************************");
                System.out.println("");
                System.out.println("1. Send another message");
                System.out.println("2. Go back");
                System.out.print("Please select an option: ");
                int nextAction = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (nextAction == 1) {
                clearScreen();
                sendMessageToIndividual(scanner, userData, userType);
                } else if (nextAction == 2) {
                return;
                } else {
                System.out.println("Invalid choice. Going back...");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }


    private static Map<String, List<String>> studentMessages = new HashMap<>();
    private static Map<String, String> counselorMessages = new HashMap<>();

  


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




