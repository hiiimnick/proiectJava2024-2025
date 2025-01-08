package clase;

public class Nota {
    int studentId;
    int nota;

    public Nota(int studentId, int nota) {
        this.studentId = studentId;
        this.nota = nota;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
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