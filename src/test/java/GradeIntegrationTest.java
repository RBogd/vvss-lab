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
    private Service service;
    private StudentXMLRepository studentRepo;
    private TemaXMLRepository temaRepo;
    private NotaXMLRepository notaRepo;
    private Validator<Student> studentValidator;
    private Validator<Tema> temaValidator;
    private Validator<Nota> notaValidator;

    @BeforeEach
    void setUp() {
        // Initialize test files
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
        // Clean up test files
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

    private void deleteTestFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    // Unit test for addStudent
    @Test
    void testAddStudent() {
        // Arrange
        String id = "1";
        String nume = "John Doe";
        int grupa = 221;

        // Act
        int result = service.saveStudent(id, nume, grupa);

        // Assert
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


        int result = service.saveTema(id, descriere, deadline, startline);


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
        int predata = 6;
        String feedback = "Good work";

        service.saveStudent(studentId, "John Doe", 221);
        service.saveTema(temaId, "Assignment 1", 7, 1);


        int result = service.saveNota(studentId, temaId, nota, predata, feedback);

        assertEquals(1, result, "Should return 1 for successful grade addition");
        String idnota = studentId + "#" + temaId;
        Nota savedNota = notaRepo.findOne(idnota);
        assertNotNull(savedNota, "Grade should exist in repository");
        assertEquals(nota, savedNota.getNota(), "Grade value should match");
        assertEquals(predata, savedNota.getSaptamanaPredare(), "Submission week should match");
        assertEquals(feedback, savedNota.getFeedback(), "Feedback should match");
    }

    // Integration test for the complete flow
    @Test
    void testCompleteGradeFlow() {
        // Arrange
        String studentId = "1";
        String temaId = "1";
        String studentName = "John Doe";
        int grupa = 221;
        String descriere = "Assignment 1";
        int deadline = 7;
        int startline = 1;
        double nota = 9.5;
        int predata = 6;
        String feedback = "Good work";

        // Act - Add student
        int studentResult = service.saveStudent(studentId, studentName, grupa);
        assertEquals(1, studentResult, "Student should be added successfully");

        // Act - Add assignment
        int temaResult = service.saveTema(temaId, descriere, deadline, startline);
        assertEquals(1, temaResult, "Assignment should be added successfully");

        // Act - Add grade
        int notaResult = service.saveNota(studentId, temaId, nota, predata, feedback);
        assertEquals(1, notaResult, "Grade should be added successfully");

        // Assert - Verify all entities exist and have correct values
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