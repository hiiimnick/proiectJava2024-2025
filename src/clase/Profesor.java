package clase;

public class Profesor extends Persoana {

    public Profesor(int ID, String nume, String prenume, String user, String pass) {
        super(ID, nume, prenume, user, pass);
    }

    public Profesor() {
        super(0, "", "", "", "");
    }

    public int getId() {
        return getID();
    }

}
