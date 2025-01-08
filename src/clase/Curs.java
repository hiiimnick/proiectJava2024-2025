package clase;

import java.util.*;

public class Curs {
    int ID;
    String nume;
    String descriere;
    int IDProfesor;
    List<Nota> note = new ArrayList<>();

    public Curs(int ID, String nume, String descriere, int IDProfesor) {
        this.ID = ID;
        this.nume = nume;
        this.descriere = descriere;
        this.IDProfesor = IDProfesor;
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

    public void addNota(int studentId, int nota) {
        note.add(new Nota(studentId, nota));
    }

    @Override
    public String toString() {
        return "Curs{" +
                "ID=" + ID +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", IDProfesor=" + IDProfesor +
                '}';
    }
}
