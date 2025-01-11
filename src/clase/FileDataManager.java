package clase;

import java.io.*;
import java.util.*;

public class FileDataManager {

    File studentFile = new File("src/inputData/studenti.txt");
    File profesorFile = new File("src/inputData/profesori.txt");
    File courseFile = new File("src/inputData/cursuri.txt");
    File noteFile = new File("src/inputData/note.txt");

    public List<Student> createStudentsData() {
        List<Student> students = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(studentFile));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student(Integer.parseInt(data[0].trim()), data[1].trim(), data[2].trim(), Integer.parseInt(data[3].trim()), Integer.parseInt(data[4].trim()), data[5].trim(), data[6].trim());
                students.add(student);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Profesor> createProfessorsData() {
        List<Profesor> professors = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(profesorFile));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Profesor profesor = new Profesor(Integer.parseInt(data[0].trim()), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
                professors.add(profesor);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return professors;
    }

    public List<Curs> createCoursesData() {
        List<Curs> courses = new ArrayList<>();
        try {
            BufferedReader coursesReader = new BufferedReader(new FileReader(courseFile));
            String line;
            coursesReader.readLine();
            while ((line = coursesReader.readLine()) != null) {
                String[] data = line.split(",");
                Curs curs = new Curs(Integer.parseInt(data[0].trim()),
                        data[1].trim(),
                        data[2].trim(),
                        Integer.parseInt(data[3].trim()),
                        Integer.parseInt(data[4].trim()));
                courses.add(curs);
            }
            coursesReader.close();
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
                for (Curs curs : courses) {
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

        return courses;
    }
}
