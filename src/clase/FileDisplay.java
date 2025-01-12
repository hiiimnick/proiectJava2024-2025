package clase;

import java.io.*;

public class FileDisplay {

    public void displayStudents(Student[] studenti) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inputData/studenti.txt"))) {
            for (Student student : studenti) {
                writer.write(student.getId() + "," + student.getNume() + "," + student.getPrenume() + "," + student.getAn());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Eroare in scrierea fisierului studenti.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayTeachers(Profesor[] profesori) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inputData/profesori.txt"))) {
            for (Profesor profesor : profesori) {
                writer.write(profesor.getId() + "," + profesor.getNume() + "," + profesor.getPrenume());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Eroare in scrierea fisierului profesori.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayCourses(Curs[] cursuri) {
        try (BufferedWriter cursuriWriter = new BufferedWriter(new FileWriter("inputData/cursuri.txt"));
             BufferedWriter noteWriter = new BufferedWriter(new FileWriter("inputData/note.txt"))) {
            for (Curs curs : cursuri) {
                cursuriWriter.write(curs.getID() + "," + curs.getNume() + "," + curs.getDescriere() + "," + curs.getIDProfesor());
                cursuriWriter.newLine();
                for (Nota nota : curs.note) {
                    noteWriter.write(nota.getStudentId() + "," + nota.getNota() + "," + curs.getID());
                    noteWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare in scrierea fisierului cursuri.txt sau note.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
