package clase;

public class Student extends Persoana {

    int grupa;
    int an;

    public Student(int ID, String nume, String prenume, int grupa, int an,String user, String pass) {
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

}
