package clase;

public class Nota {
    int idCurs;
    int studentId;
    double nota;

    public Nota(int studentId, int idCurs, double nota) {
        this.studentId = studentId;
        this.idCurs = idCurs;
        this.nota = nota;
    }

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "studentId=" + studentId +
                ", nota=" + nota +
                '}';
    }
}