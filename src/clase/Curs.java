package clase;

import java.util.*;

public class Curs {
    int ID;
    String nume;
    String descriere;
    Profesor prof;
    Set<Student> stud;
    HashMap <Student, Double> nota;
    int an;

    public Curs(int ID, String nume, String descriere, Profesor prof, Set<Student> stud, HashMap<Student, Double> nota, int an) {
        this.ID = ID;
        this.nume = nume;
        this.descriere = descriere;
        this.prof = prof;
        this.stud = stud;
        this.nota = nota;
        this.an = an;
    }

    public Curs() {
        this.ID = 0;
        this.nume = "";
        this.descriere = "";
        this.prof = null;
        this.stud = new HashSet<>();
        this.nota = new HashMap<>();
        this.an = 0;
    }
}
