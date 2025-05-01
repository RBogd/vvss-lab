package test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//import
public class TakeHomeMockTests {

    private Service service;
    private StudentXMLRepository studentRepoMock;
    private TemaXMLRepository temaRepoMock;
    private NotaXMLRepository notaRepoMock;


    @BeforeEach
    void setUp() {
        service = mock(Service.class);

        studentRepoMock = mock(StudentXMLRepository.class);
        temaRepoMock = mock(TemaXMLRepository.class);
        notaRepoMock = mock(NotaXMLRepository.class);

        service = new Service(studentRepoMock, temaRepoMock, notaRepoMock);
    }

    //    nimic deocamdata nu cred
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

        when(studentRepoMock.save(any(Student.class))).thenReturn(new Student(id, nume, grupa));
        when(studentRepoMock.findOne(id)).thenReturn(new Student(id, nume, grupa));

        int result = service.saveStudent(id, nume, grupa);

        assertEquals(1, result, "Should return 1 for successful student addition");
        Student savedStudent = studentRepoMock.findOne(id);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(id, savedStudent.getID(), "Student ID should match");
        assertEquals(nume, savedStudent.getNume(), "Student name should match");
        assertEquals(grupa, savedStudent.getGrupa(), "Student group should match");


        verify(studentRepoMock).save(any(Student.class));
        verify(studentRepoMock).findOne(any(String.class));

    }

    @Test
    void addAssignemnt() {
        /*
        incremental integration test for add_student+ assignment
        */

        String idStudent = "1";
        String numeStudent = "Andrei";
        int grupaStudent = 221;

        String idTema = "1";
        String descriereTema = "Tema 1";
        int startlineTema = 1;
        int deadlineTema = 2;

        when(temaRepoMock.save(any(Tema.class))).thenReturn(new Tema(idTema, descriereTema, startlineTema, deadlineTema));
        when(temaRepoMock.findOne(idTema)).thenReturn(new Tema(idTema, descriereTema, startlineTema, deadlineTema));

        when(studentRepoMock.save(any(Student.class))).thenReturn(new Student(idStudent, numeStudent, grupaStudent));
        when(studentRepoMock.findOne(idStudent)).thenReturn(new Student(idStudent, numeStudent, grupaStudent));

        int resultStudent = service.saveStudent(idStudent, numeStudent, grupaStudent);
        assertEquals(1, resultStudent, "Should return 1 for successful student addition");
        Student savedStudent = studentRepoMock.findOne(idStudent);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(idStudent, savedStudent.getID(), "Student ID should match");
        assertEquals(numeStudent, savedStudent.getNume(), "Student name should match");
        assertEquals(grupaStudent, savedStudent.getGrupa(), "Student group should match");

        int resultTema = service.saveTema(idTema, descriereTema, startlineTema, deadlineTema);
        assertEquals(1, resultTema, "Should return 1 for successful assignment addition");
        Tema savedTema = temaRepoMock.findOne(idTema);
        assertNotNull(savedTema, "Assignment should exist in repository");
        assertEquals(idTema, savedTema.getID(), "Assignment ID should match");
        assertEquals(descriereTema, savedTema.getDescriere(), "Assignment description should match");
        assertEquals(deadlineTema, savedTema.getDeadline(), "Assignment deadline should match");
        assertEquals(startlineTema, savedTema.getStartline(), "Assignment startline should match");


        verify(studentRepoMock).save(any(Student.class));
        verify(studentRepoMock).findOne(any(String.class));

        verify(temaRepoMock).save(any(Tema.class));
        verify(temaRepoMock).findOne(any(String.class));
    }

    @Test
    void addGrade() {
        /*
        incremental integration test for add_student + assignment + grade
         */

                /*
        incremental integration test for add_student+ assignment
        */
        String idStudent = "1";
        String numeStudent = "Andrei";
        int grupaStudent = 221;

        String idTema = "1";
        String descriereTema = "Tema 1";
        int startlineTema = 1;
        int deadlineTema = 2;

        // jastea pt nota gen
        double varNota = 9;
        int predata = 8;
        String feedback = "Good work";

        when(temaRepoMock.save(any(Tema.class))).thenReturn(new Tema(idTema, descriereTema, startlineTema, deadlineTema));
        when(temaRepoMock.findOne(idTema)).thenReturn(new Tema(idTema, descriereTema, startlineTema, deadlineTema));

        when(studentRepoMock.save(any(Student.class))).thenReturn(new Student(idStudent, numeStudent, grupaStudent));
        when(studentRepoMock.findOne(idStudent)).thenReturn(new Student(idStudent, numeStudent, grupaStudent));

        when(notaRepoMock.save(any(Nota.class))).thenReturn(new Nota(idStudent + "#" + idTema, varNota, predata, feedback, idStudent, idTema));
        when(notaRepoMock.findOne(idStudent + "#" + idTema)).thenReturn(new Nota(idStudent + "#" + idTema, 1, predata, feedback, idStudent, idTema));

        int resultStudent = service.saveStudent(idStudent, numeStudent, grupaStudent);
        assertEquals(1, resultStudent, "Should return 1 for successful student addition");
        Student savedStudent = studentRepoMock.findOne(idStudent);
        assertNotNull(savedStudent, "Student should exist in repository");
        assertEquals(idStudent, savedStudent.getID(), "Student ID should match");
        assertEquals(numeStudent, savedStudent.getNume(), "Student name should match");
        assertEquals(grupaStudent, savedStudent.getGrupa(), "Student group should match");

        int resultTema = service.saveTema(idTema, descriereTema, startlineTema, deadlineTema);
        assertEquals(1, resultTema, "Should return 1 for successful assignment addition");
        Tema savedTema = temaRepoMock.findOne(idTema);
        assertNotNull(savedTema, "Assignment should exist in repository");
        assertEquals(idTema, savedTema.getID(), "Assignment ID should match");
        assertEquals(descriereTema, savedTema.getDescriere(), "Assignment description should match");
        assertEquals(deadlineTema, savedTema.getDeadline(), "Assignment deadline should match");
        assertEquals(startlineTema, savedTema.getStartline(), "Assignment startline should match");

        int resultNota = service.saveNota(idStudent, idTema, varNota, predata, feedback);
        assertEquals(1, resultNota, "Should return 1 for successful grade addition");
        String idnota = idStudent + "#" + idTema;
        Nota savedNota = notaRepoMock.findOne(idnota);
        assertNotNull(savedNota, "Grade should exist in repository");
        assertEquals(idnota, savedNota.getID(), "Grade ID should match");
        assertEquals(1, savedNota.getNota(), "Grade value should match");
        assertEquals(predata, savedNota.getSaptamanaPredare(), "Grade predata should match");
        assertEquals(feedback, savedNota.getFeedback(), "Grade feedback should match");


        verify(studentRepoMock).save(any(Student.class));
        verify(studentRepoMock, times(2)).findOne(any(String.class));

        verify(temaRepoMock).save(any(Tema.class));
        verify(temaRepoMock, times(3)).findOne(any(String.class));

        verify(notaRepoMock).save(any(Nota.class));
        verify(notaRepoMock).findOne(any(String.class));

    }

}
