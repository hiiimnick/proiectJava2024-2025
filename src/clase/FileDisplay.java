package clase;

import java.io.*;

public class FileDisplay {

    public void displayStudents(Student[] students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inputData/studenti.txt"))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getNume() + "," + student.getPrenume() + "," + student.getAn());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to studenti.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayTeachers(Profesor[] profesors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inputData/profesori.txt"))) {
            for (Profesor profesor : profesors) {
                writer.write(profesor.getId() + "," + profesor.getNume() + "," + profesor.getPrenume());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to profesori.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayCourses(Curs[] cursuri) {
        try (BufferedWriter courseWriter = new BufferedWriter(new FileWriter("inputData/cursuri.txt"));
             BufferedWriter noteWriter = new BufferedWriter(new FileWriter("inputData/note.txt"))) {
            for (Curs curs : cursuri) {
                courseWriter.write(curs.getID() + "," + curs.getNume() + "," + curs.getDescriere() + "," + curs.getIDProfesor());
                courseWriter.newLine();
                for (Nota nota : curs.note) {
                    noteWriter.write(nota.getStudentId() + "," + nota.getNota() + "," + curs.getID());
                    noteWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to cursuri.txt or note.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
