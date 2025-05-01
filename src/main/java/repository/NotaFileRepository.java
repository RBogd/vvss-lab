package repository;

import domain.Nota;
import validation.ValidationException;
import validation.Validator;

import java.io.*;
import java.util.stream.Collectors;

public class NotaFileRepository extends AbstractFileRepository<String, Nota> {

    public NotaFileRepository(Validator<Nota> validator, String filename) {
        super(validator, filename);
        loadFromFile();
    }

    protected void loadFromFile() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            buffer.lines().collect(Collectors.toList()).forEach(line -> {
                String[] result = line.split("#");
                String idnota = result[0] + "#" + result[1];
                Nota nota = new Nota(idnota, Double.parseDouble(result[2]),
                        Integer.parseInt(result[3]), result[4], result[0],result[1]);
                try {
                    super.save(nota);
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void writeToFile(Nota nota) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(nota.getIdStudent() + "#" + nota.getID() + "#" + nota.getNota() + "#"
                    + nota.getSaptamanaPredare() + "#" + nota.getFeedback() + "\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void writeToFileAll() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {
            super.entities.values().forEach(nota -> {
                try {
                    bw.write(nota.getIdStudent() + "#" + nota.getID() + "#" + nota.getNota()
                            + "#" + nota.getSaptamanaPredare() + "#" + nota.getFeedback() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

//    protected void createFile(Nota notaObj) {
//        String idStudent = notaObj.getIdStudent();
//        StudentValidator sval = new StudentValidator();
//        TemaValidator tval = new TemaValidator();
//        StudentXMLRepository srepo = new StudentXMLRepository(sval, "studenti.txt");
//        TemaXMLRepository trepo = new TemaXMLRepository(tval, "teme.txt");
//
//        Student student = srepo.findOne(idStudent);
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getNume() + ".txt", false))) {
//            super.findAll().forEach(nota -> {
//                if (nota.getIdStudent().equals(idStudent)) {
//                    try {
//                        bw.write("Tema: " + nota.getID() + "\n");
//                        bw.write("Nota: " + nota.getNota() + "\n");
//                        bw.write("Predata in saptamana: " + nota.getSaptamanaPredare() + "\n");
//                        bw.write("Deadline: " + trepo.findOne(nota.getID()).getDeadline() + "\n");
//                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
}