package clase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Student extends Persoana {

    int grupa;
    int an;
    List<Curs> cursuri = new ArrayList<>();
    List<Nota> note = new ArrayList<>();

    public Student(int ID, String nume, String prenume, int grupa, int an, String user, String pass) {
        super(ID, nume, prenume, user, pass);
        this.grupa = grupa;
        this.an = an;
    }

    public Student() {
        super(0, "", "", "", "");
        this.grupa = 0;
        this.an = 0;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public int getId() {
        return getID();
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public void addCurs(Curs curs) {
        cursuri.add(curs);
    }

    public void addGrade(Curs course, double grade) {
        note.add(new Nota(course.getID(), this.getId(), grade));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputData/note.txt", true))) {
            writer.write(course.getID() + "," + this.getId() + "," + grade);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

}