package clase;

import java.util.*;

public class Curs {
    int ID;
    String nume;
    String descriere;
    int IDProfesor;
    int anCurs;
    Set<Student> studenti = new HashSet<>();
    List<Nota> note = new ArrayList<>();

    public Curs(int ID, String nume, String descriere, int IDProfesor, int anCurs) {
        this.ID = ID;
        this.nume = nume;
        this.descriere = descriere;
        this.IDProfesor = IDProfesor;
        this.anCurs = anCurs;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getIDProfesor() {
        return IDProfesor;
    }

    public void setIDProfesor(int IDProfesor) {
        this.IDProfesor = IDProfesor;
    }

    public int getAnCurs() {
        return anCurs;
    }

    public void setAnCurs(int anCurs) {
        this.anCurs = anCurs;
    }

    public Set<Student> getStudenti() {
        return studenti;
    }

    public void addStudent(Student student) {
        studenti.add(student);
    }

    public void addNota(int IDCurs, int studentId, double nota) {
        note.add(new Nota(IDCurs, studentId, nota));
    }

    @Override
    public String toString() {
        return "Curs{" +
                "ID=" + ID +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", IDProfesor=" + IDProfesor +
                ", anCurs=" + anCurs +
                '}';
    }
}