package test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GradeIntegrationTest {
    protected Service service;
    protected StudentXMLRepository studentRepo;
    protected TemaXMLRepository temaRepo;
    protected NotaXMLRepository notaRepo;
    protected StudentValidator studentValidator;
    protected TemaValidator temaValidator;
    protected NotaValidator notaValidator;

    @BeforeEach
    void setUp() {
        initializeTestFile("test_studenti.xml");
        initializeTestFile("test_teme.xml");
        initializeTestFile("test_note.xml");

        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        studentRepo = new StudentXMLRepository(studentValidator, "test_studenti.xml");
        temaRepo = new TemaXMLRepository(temaValidator, "test_teme.xml");
        notaRepo = new NotaXMLRepository(notaValidator, "test_note.xml");
        service = new Service(studentRepo, temaRepo, notaRepo);
    }

    @AfterEach
    void tearDown() {
        deleteTestFile("test_studenti.xml");
        deleteTestFile("test_teme.xml");
        deleteTestFile("test_note.xml");
    }

    private void initializeTestFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n</Entitati>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void deleteTestFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("N-am putut sa stergem fisieru : " + filename);
            }
        }
    }

    @Test
    void testAddStudent() {
        String id = "1";
        String nume = "Andrei";
        int grupa = 221;

        int result = service.saveStudent(id, nume, grupa);

        assertEquals(1, result, "Should return 1 for successful student addition");
        Student savedStudent = studentRepo.findOne(id);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(id, savedStudent.getID(), "Student ID should match");
        assertEquals(nume, savedStudent.getNume(), "Student name should match");
        assertEquals(grupa, savedStudent.getGrupa(), "Student group should match");
    }


    @Test
    void testAddAssignment() {

        String id = "1";
        String descriere = "Assignment 1";
        int deadline = 7;
        int startline = 1;


        int result = service.saveTema(id, descriere, startline, deadline);

        assertEquals(1, result, "Should return 1 for successful assignment addition");
        Tema savedTema = temaRepo.findOne(id);
        assertNotNull(savedTema, "Assignment should exist in repository");
        assertEquals(id, savedTema.getID(), "Assignment ID should match");
        assertEquals(descriere, savedTema.getDescriere(), "Assignment description should match");
        assertEquals(deadline, savedTema.getDeadline(), "Assignment deadline should match");
    }


    @Test
    void testAddGrade() {

        String studentId = "1";
        String temaId = "1";
        double nota = 9;
        int predata = 8;
        int deadline = 7;
        String feedback = "Good work";

        service.saveStudent(studentId, "Bogdan", 221);
        service.saveTema(temaId, "Assignment 1", 1, deadline);


        int result = service.saveNota(studentId, temaId, nota, predata, feedback);
        double expectedNota = Math.max(1.0, nota - ((predata - deadline) * 2.5));

        assertEquals(1, result, "Should return 1 for successful grade addition");
        String idnota = studentId + "#" + temaId;
        Nota savedNota = notaRepo.findOne(idnota);
        assertNotNull(savedNota, "Grade should exist in repository");
        assertEquals(expectedNota, savedNota.getNota(), "Grade value should match");
        assertEquals(predata, savedNota.getSaptamanaPredare(), "Submission week should match");
        assertEquals(feedback, savedNota.getFeedback(), "Feedback should match");
    }

    @Test
    void testCompleteGradeFlow() {
        String studentId = "1";
        String temaId = "1";
        String studentName = "Mircea";
        int grupa = 221;
        String descriere = "Assignment 1";
        int deadline = 7;
        int startline = 1;
        double nota = 9.5;
        int predata = 6;
        String feedback = "Good work";

        int studentResult = service.saveStudent(studentId, studentName, grupa);
        assertEquals(1, studentResult, "Student should be added successfully");


        int temaResult = service.saveTema(temaId, descriere, startline, deadline);
        assertEquals(1, temaResult, "Assignment should be added successfully");


        int notaResult = service.saveNota(studentId, temaId, nota, predata, feedback);
        assertEquals(1, notaResult, "Grade should be added successfully");


        Student savedStudent = studentRepo.findOne(studentId);
        assertNotNull(savedStudent, "Student should exist");
        assertEquals(studentName, savedStudent.getNume(), "Student name should match");

        Tema savedTema = temaRepo.findOne(temaId);
        assertNotNull(savedTema, "Assignment should exist");
        assertEquals(descriere, savedTema.getDescriere(), "Assignment description should match");

        String idnota = studentId + "#" + temaId;
        Nota savedNota = notaRepo.findOne(idnota);
        assertNotNull(savedNota, "Grade should exist");
        assertEquals(nota, savedNota.getNota(), "Grade value should match");
        assertEquals(feedback, savedNota.getFeedback(), "Feedback should match");
    }
} 