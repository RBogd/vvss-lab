package test;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepository;
import service.Service;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class StudentsTest {
    private Service service;
    private StudentXMLRepository studentRepo;
    private Validator<Student> studentValidator;

    @BeforeEach
    void setUp() {
        studentValidator = new StudentValidator();
        studentRepo = new StudentXMLRepository(studentValidator, "test_studenti.xml");
        service = new Service(studentRepo, null, null);
    }

    @Test
    void testAddValidStudent() {
        String id = "1";
        String nume = "Bogdan";
        int grupa = 832;
        int result = service.saveStudent(id, nume, grupa);
        assertEquals(1, result, "Should return 1 for successful student addition");
        Student savedStudent = studentRepo.findOne(id);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(id, savedStudent.getID(), "Student ID should match");
        assertEquals(nume, savedStudent.getNume(), "Student name should match");
        assertEquals(grupa, savedStudent.getGrupa(), "Student group should match");
    }

    @Test
    void testAddInvalidStudent() {
        String id = "";
        String nume = "Andrei";
        int grupa = 832;
        int result = service.saveStudent(id, nume, grupa);
        assertEquals(0, result, "Should return 0 for invalid student");
        Student savedStudent = studentRepo.findOne(id);
        assertNull(savedStudent, "Invalid student should not exist in repository");
    }
} 