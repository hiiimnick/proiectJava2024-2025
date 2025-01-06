package clase;

public abstract class Persoana {

    private int ID;
    private String nume;
    private String prenume;
    private String user;
    private String pass;

    public Persoana(int ID, String nume, String prenume, String user, String pass) {
        this.ID = ID;
        this.nume = nume;
        this.prenume = prenume;
        this.user = user;
        this.pass = pass;
    }

    public Persoana() {
        this.ID = 0;
        this.nume = "";
        this.prenume = "";
        this.user = "";
        this.pass = "";
    }

    @Override
    public String toString() {
        return "clase.Persoana{" +
                "ID=" + ID +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
