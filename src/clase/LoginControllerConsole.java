package clase;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LoginControllerConsole {
    private boolean isProfessor = false;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Selecteaza optiunea: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Switch Mode");
            System.out.println("4. Exit");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    login(scanner);
                    break;
                case "2":
                    register(scanner);
                    break;
                case "3":
                    switchMode();
                    break;
                case "4":
                    System.out.println("Iesire... (daca nu a iesit, GUI-ul e inca pornit)");
                    return;
                default:
                    System.out.println("Optiune invalida.");
            }
        }
    }

    private void switchMode() {
        if (isProfessor) {
            System.out.println("Schimbat la modul Student.");
        } else {
            System.out.println("Schimbat la modul profesor.");
        }
        isProfessor = !isProfessor;
    }

    public void login(Scanner scanner) {
        System.out.print("Introdu username-ul: ");
        String username = scanner.nextLine();
        System.out.print("Introdu parola: ");
        String password = scanner.nextLine();
        boolean loginSuccessful = false;
        int studentId = -1;
        int profesorId = -1;

        if (isProfessor) {
            loginSuccessful = checkCredentials("src/inputData/profesori.txt", username, password);
            if (loginSuccessful) {
                profesorId = getProfessorIdByUsername(username);
            }
        } else {
            loginSuccessful = checkCredentials("src/inputData/studenti.txt", username, password);
            if (loginSuccessful) {
                studentId = getStudentIdByUsername(username);
            }
        }

        if (loginSuccessful) {
            System.out.println("Logare reusita!");
            if (isProfessor) {
                System.out.println("Bun venit domnule profesor");
                handleProfessorDashboard(scanner, profesorId);
            } else {
                System.out.println("Bun venit studentule");
                handleStudentDashboard(scanner, studentId);
            }
        } else {
            System.out.println("Logarea a esuat. Username sau parola incorecte.");
        }
    }


    public void register(Scanner scanner) {
        System.out.print("Introdu username-ul: ");
        String username = scanner.nextLine();
        System.out.print("Introdu parola: ");
        String password = scanner.nextLine();

        String hashedPassword = PasswordUtils.encodePassword(password);

        if (isProfessor) {
            System.out.print("Introdu numele: ");
            String nume = scanner.nextLine();
            if (nume.isEmpty()) {
                System.out.println("Nume invalid.");
                return;
            }

            System.out.print("Introdu prenumele: ");
            String prenume = scanner.nextLine();
            if (prenume.isEmpty()) {
                System.out.println("Prenume invalid.");
                return;
            }

            int nextId = getNextId("src/inputData/profesori.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/profesori.txt", true))) {
                writer.write(nextId + "," + nume + ',' + prenume + ',' + username + "," + hashedPassword);
                writer.newLine();
                System.out.println("Profesor inregistrat cu succes.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Eroare in inregistrarea profesorului.");
            }
        } else {
            System.out.print("Introdu numele: ");
            String nume = scanner.nextLine();
            if (nume.isEmpty()) {
                System.out.println("Nume invalid.");
                return;
            }

            System.out.print("Introdu prenumele: ");
            String prenume = scanner.nextLine();
            if (prenume.isEmpty()) {
                System.out.println("Prenume invalid.");
                return;
            }

            System.out.print("Introdu grupa: ");
            String grupa = scanner.nextLine();
            if (grupa.isEmpty()) {
                System.out.println("Grupa invalida.");
                return;
            }

            System.out.print("Introdu anul universitar curent (1-4): ");
            String an = scanner.nextLine();
            if (an.isEmpty()) {
                System.out.println("An invalid. Te rog introdu un an valid (1-4).");
                return;
            }

            int nextId = getNextId("src/inputData/studenti.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/studenti.txt", true))) {
                writer.write(nextId + "," + nume + "," + prenume + "," + grupa + "," + an + "," + username + "," + hashedPassword);
                writer.newLine();
                System.out.println("Student inregistrat cu succes.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Eroare in inregistrarea studentului.");
            }
        }
    }

    private int getNextId(String filePath) {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    private int getProfessorIdByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/profesori.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[3].equals(username)) {
                    return Integer.parseInt(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the professor ID is not found
    }

    private boolean checkCredentials(String filePath, String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String hashedPassword = PasswordUtils.encodePassword(password);
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!isProfessor && data[5].trim().equals(username) && data[6].trim().equals(hashedPassword)) {
                    return true;
                } else if (data[3].trim().equals(username) && data[4].trim().equals(hashedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handleStudentDashboard(Scanner scanner, int studentId) {
        System.out.print("Introdu un an universitar (1-4): ");
        String year = scanner.nextLine();
        if (year.isEmpty() || Integer.parseInt(year) > 4) {
            System.out.println("An invalid. Te rog introdu un an universitar valid (1-4).");
            return;
        }

        while (true) {
            System.out.println("Alege optiune: ");
            System.out.println("1. Vezi cursuri");
            System.out.println("2. Vezi note");
            System.out.println("3. Vezi media");
            System.out.println("4. Vezi restante");
            System.out.println("5. Iesire");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    List<Curs> loadedCourses = loadCoursesForYear(year);
                    System.out.println("Cursurile tale: " + loadedCourses.stream().map(Curs::getNume).collect(Collectors.joining(", ")));
                    break;
                case "2":
                    StringBuilder loadedGrades = loadGradesForStudent(studentId, loadCoursesForYear(year));
                    System.out.println("Notele tale: \n" + loadedGrades.toString());
                    break;
                case "3":
                    loadedGrades = loadGradesForStudent(studentId, loadCoursesForYear(year));
                    displayAverageGrade(loadedGrades);
                    break;
                case "4":
                    loadedGrades = loadGradesForStudent(studentId, loadCoursesForYear(year));
                    displayFailedCourses(loadedGrades);
                    break;
                case "5":
                    System.out.println("Iesire...");
                    return;
                default:
                    System.out.println("Optiune invalida.");
            }
        }
    }

    private List<Curs> loadCoursesForYear(String year) {
        FileDataManager fileDataManager = new FileDataManager();
        return fileDataManager.createCoursesData().stream()
                .filter(curs -> Integer.toString(curs.getAnCurs()).equals(year))
                .collect(Collectors.toList());
    }

    private StringBuilder loadGradesForStudent(int studentId, List<Curs> loadedCourses) {
        StringBuilder loadedGrades = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/note.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int courseId = Integer.parseInt(parts[0]);
                int studentIdFromFile = Integer.parseInt(parts[1]);
                if (studentIdFromFile == studentId && loadedCourses.stream().anyMatch(curs -> curs.getID() == courseId)) {
                    String courseName = loadedCourses.stream()
                            .filter(curs -> curs.getID() == courseId)
                            .map(Curs::getNume)
                            .findFirst()
                            .orElse("Curs necunoscut");
                    loadedGrades.append("Curs: ").append(courseName)
                            .append(", Nota: ").append(parts[2])
                            .append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedGrades;
    }

    private void displayAverageGrade(StringBuilder loadedGrades) {
        String[] gradesLines = loadedGrades.toString().split("\n");
        double totalGrades = 0;
        int count = 0;

        for (String line : gradesLines) {
            String[] parts = line.split(", Nota: ");
            if (parts.length == 2) {
                double grade = Double.parseDouble(parts[1]);
                if (grade != 0 && grade > 4) {
                    totalGrades += grade;
                    count++;
                }
            }
        }

        double averageGrade = (count > 0) ? totalGrades / count : 0;
        System.out.println("Media ta este: " + averageGrade);
    }

    private void displayFailedCourses(StringBuilder loadedGrades) {
        String[] gradesLines = loadedGrades.toString().split("\n");
        StringBuilder failedCourses = new StringBuilder();

        for (String line : gradesLines) {
            String[] parts = line.split(", Nota: ");
            if (parts.length == 2) {
                double grade = Double.parseDouble(parts[1]);
                if (grade <= 4) {
                    String courseName = line.split(", Nota: ")[0].replace("Curs: ", "");
                    failedCourses.append(courseName).append("\n");
                }
            }
        }

        System.out.println("Restantele tale: " + (failedCourses.length() > 0 ? failedCourses.toString() : "Fara restante."));
    }

    private int getStudentIdByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/inputData/studenti.txt"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(username)) {
                    return Integer.parseInt(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the student ID is not found
    }

    private void handleProfessorDashboard(Scanner scanner, int professorId) {
        while (true) {
            System.out.println("Alege optiune: ");
            System.out.println("1. Vezi cursuri");
            System.out.println("2. Vezi studenti in curs");
            System.out.println("3. Noteaza student");
            System.out.println("4. Iesire");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    viewCourses(professorId);
                    break;
                case "2":
                    viewStudentsInCourse(scanner, professorId);
                    break;
                case "3":
                    gradeStudent(scanner, professorId);
                    break;
                case "4":
                    System.out.println("Iesire...");
                    return;
                default:
                    System.out.println("Comanda invalida.");
            }
        }
    }

    private void viewCourses(int professorId) {
        List<Curs> courses = loadCoursesForProfessor(professorId);
        System.out.println("Cursurile pe care le predai: " + courses.stream().map(Curs::getNume).collect(Collectors.joining(", ")));
    }

    private void viewStudentsInCourse(Scanner scanner, int professorId) {
        List<Curs> courses = loadCoursesForProfessor(professorId);
        if (courses.isEmpty()) {
            System.out.println("Nu predai niciun curs.");
            return;
        }

        Curs selectedCourse = selectCourse(scanner, courses);
        if (selectedCourse != null) {
            List<String> studentNames = getStudentNamesForCourse(selectedCourse);
            System.out.println("Studenti in " + selectedCourse.getNume() + ": " + String.join(", ", studentNames));
        }
    }

    private List<String> getStudentNamesForCourse(Curs course) {
        FileDataManager fileDataManager = new FileDataManager();
        List<Student> loadedStudents = fileDataManager.createStudentsData();
        return loadedStudents.stream()
                .filter(student -> student.getAn() == course.getAnCurs())
                .map(Student::getNumeComplet)
                .collect(Collectors.toList());
    }

    private void gradeStudent(Scanner scanner, int professorId) {
        List<Curs> courses = loadCoursesForProfessor(professorId);
        if (courses.isEmpty()) {
            System.out.println("Nu predai niciun curs.");
            return;
        }

        Curs selectedCourse = selectCourse(scanner, courses);
        if (selectedCourse != null) {
            System.out.print("Introdu numele complet al studentului: ");
            String studentName = scanner.nextLine();
            System.out.print("Introdu nota: ");
            String gradeStr = scanner.nextLine();
            try {
                int grade = Integer.parseInt(gradeStr);
                if (grade < 1 || grade > 10) {
                    System.out.println("Nota invalida. Te rog introdu o nota intre 1 si 10.");
                    return;
                }

                List<Student> students = loadStudentsForCourse(selectedCourse);
                Student student = null;
                for (Student s : students) {
                    if (s.getNumeComplet().equalsIgnoreCase(studentName)) {
                        student = s;
                        break;
                    }
                }

                if (student != null) {
                    addGrade(selectedCourse.getID(), student.getID(), grade);
                    System.out.println("Nota intodusa cu succes.");
                } else {
                    System.out.println("Studentul nu a fost gasit in curs.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nota invalida. Te rog introdu un numar.");
            }
        }
    }

    private Curs selectCourse(Scanner scanner, List<Curs> courses) {
        if (courses.size() == 1) {
            return courses.get(0);
        } else {
            System.out.println("Alege cursul: ");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i).getNume());
            }
            String courseIndexStr = scanner.nextLine();
            try {
                int courseIndex = Integer.parseInt(courseIndexStr) - 1;
                if (courseIndex >= 0 && courseIndex < courses.size()) {
                    return courses.get(courseIndex);
                } else {
                    System.out.println("Curs invalid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Alegere invalida. Te rog introdu un numar.");
            }
        }
        return null;
    }

    private void addGrade(int courseId, int studentId, int grade) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/note.txt", true))) {
            writer.write(courseId + "," + studentId + "," + grade);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Eroare in adaugarea notei.");
        }
    }

    private List<Curs> loadCoursesForProfessor(int professorId) {
        FileDataManager fileDataManager = new FileDataManager();
        return fileDataManager.createCoursesData().stream()
                .filter(curs -> curs.getIDProfesor() == professorId)
                .collect(Collectors.toList());
    }

    private List<Student> loadStudentsForCourse(Curs course) {
        FileDataManager fileDataManager = new FileDataManager();
        return fileDataManager.createStudentsData().stream()
                .filter(student -> student.getAn() == course.getAnCurs())
                .collect(Collectors.toList());
    }
}