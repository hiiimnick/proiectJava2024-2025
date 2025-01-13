package clase;

import java.io.*;
import java.util.*;

public class FileDataManager {

    private static FileDataManager instance;
    private final File studentFile = new File("src/inputData/studenti.txt");
    private final File profesorFile = new File("src/inputData/profesori.txt");
    private final File cursuriFile = new File("src/inputData/cursuri.txt");
    private final File noteFile = new File("src/inputData/note.txt");

    private FileDataManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized FileDataManager getInstance() {
        if (instance == null) {
            instance = new FileDataManager();
        }
        return instance;
    }

    public synchronized List<Student> createStudentsData() {
        List<Student> studenti = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(studentFile));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student(Integer.parseInt(data[0].trim()), data[1].trim(), data[2].trim(), Integer.parseInt(data[3].trim()), Integer.parseInt(data[4].trim()), data[5].trim(), data[6].trim());
                studenti.add(student);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studenti;
    }

    public synchronized List<Profesor> createProfessorsData() {
        List<Profesor> profesori = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(profesorFile));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Profesor profesor = new Profesor(Integer.parseInt(data[0].trim()), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
                profesori.add(profesor);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profesori;
    }

    public synchronized List<Curs> createCoursesData() {
        List<Curs> cursuri = new ArrayList<>();
        try {
            BufferedReader cursuriReader = new BufferedReader(new FileReader(cursuriFile));
            String line;
            cursuriReader.readLine();
            List<Student> studenti = createStudentsData();
            while ((line = cursuriReader.readLine()) != null) {
                String[] data = line.split(",");
                Curs curs = new Curs(Integer.parseInt(data[0].trim()),
                        data[1].trim(),
                        data[2].trim(),
                        Integer.parseInt(data[3].trim()),
                        Integer.parseInt(data[4].trim()));
                for (Student student : studenti) {
                    if (student.getCursuri().contains(curs)) {
                        curs.addStudent(student);
                    }
                }
                cursuri.add(curs);
            }
            cursuriReader.close();
        } catch (IOException e) {
            System.err.println("Eroare citire fisier cursuri.txt: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            BufferedReader noteReader = new BufferedReader(new FileReader(noteFile));
            String line;
            noteReader.readLine();
            while ((line = noteReader.readLine()) != null) {
                String[] data = line.split(",");
                int cursID = Integer.parseInt(data[0].trim());
                int studentID = Integer.parseInt(data[1].trim());
                double notaStudent = Double.parseDouble(data[2].trim());
                for (Curs curs : cursuri) {
                    if (curs.getID() == cursID) {
                        curs.addNota(cursID, studentID, notaStudent);
                    }
                }
            }
            noteReader.close();
        } catch (IOException e) {
            System.err.println("Eroare citire fisier note.txt: " + e.getMessage());
            e.printStackTrace();
        }

        return cursuri;
    }
}