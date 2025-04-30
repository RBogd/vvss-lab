package test;

import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//import
public class TakeHomeMockTests {

    protected Service service;
    protected StudentXMLRepository studentRepoMock;
    protected TemaXMLRepository temaRepoMock;
    protected NotaXMLRepository notaRepoMock;
    protected StudentValidator studentValidator;
    protected TemaValidator temaValidator;
    protected NotaValidator notaValidator;


    @BeforeEach
    void setUp() {
        service = mock(Service.class);

        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        studentRepoMock = mock(StudentXMLRepository.class);
        temaRepoMock = mock(TemaXMLRepository.class);
        notaRepoMock =  mock(NotaXMLRepository.class);

        service = new Service(studentRepoMock, temaRepoMock, notaRepoMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addStudent() {
       /*
       normal simple test for add_student
        */

        String id = "1";
        String nume = "Andrei";
        int grupa = 221;

        int result = service.saveStudent(id, nume, grupa);

        assertEquals(1, result, "Should return 1 for successful student addition");
        Student savedStudent = studentRepoMock.findOne(id);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(id, savedStudent.getID(), "Student ID should match");
        assertEquals(nume, savedStudent.getNume(), "Student name should match");
        assertEquals(grupa, savedStudent.getGrupa(), "Student group should match");
    }

    @Test
    void addAssignemnt() {
        /*
        incremental integration test for add_student+ assignment
        */

    }

    @Test
    void addGrade() {
        /*
        incremental integration test for add_student + assignment + grade
         */


    }

}
