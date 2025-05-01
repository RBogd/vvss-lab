package domain;

public class Nota implements HasID<String> {
    String idNota;
    private double nota;
    private int saptamanaPredare;
    private String feedback;
    private String idTema;
    private String idStudent;

    public Nota(String idNota, double nota, int saptamanaPredare, String feedback, String idStudent, String idTema) {
        this.idNota = idNota;
        this.nota = nota;
        this.saptamanaPredare = saptamanaPredare;
        this.feedback = feedback;
        this.idTema = idTema;
        this.idStudent = idStudent;
    }

    @Override
    public String getID() {
        return idNota;
    }

    @Override
    public void setID(String idNota) {
        this.idNota = idNota;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getSaptamanaPredare() {
        return saptamanaPredare;
    }

    public void setSaptamanaPredare(int saptamanaPredare) {
        this.saptamanaPredare = saptamanaPredare;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }


    @Override
    public String toString() {
        return "Nota{" +
                "id_student = " + idStudent +
                ", id_tema = " + idTema +
                ", nota = " + nota +
                ", saptamanaPredare = " + saptamanaPredare +
                ", feedback = '" + feedback + '\'' +
                '}';
    }
}
