import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.Tema;
import repository.TemaRepository;
import validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

public class TemaTest {
    private TemaRepository TemaRepo;

    @BeforeEach
    void setUp() {
        TemaValidator TemaValidator = new TemaValidator();
        TemaRepo = new TemaRepository(TemaValidator);
    }

    @Test
    void testValidTema() {
        String id = "1";
        String descriere = "tema1";
        int startline = 3;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere,  startline, deadline);
        Tema result = TemaRepo.save(Tema);
        assertNotNull(result, "Shouldn't be null");
        Tema savedTema = TemaRepo.findOne(id);
        assertNotNull(savedTema, "Tema should exist in repository");
        assertEquals(id, savedTema.getID(), "Tema ID should match");
        assertEquals(descriere, savedTema.getDescriere(), "Tema description should match");
        assertEquals(deadline, savedTema.getDeadline(), "Tema deadline should match");
        assertEquals(startline, savedTema.getStartline(), "Tema startline should match");
    }

    @Test
    void testInvalidTemaIDEmpty() {
        String id = "";
        String descriere = "tema1";
        int startline = 3;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because id is empty");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidTemaIDnull() {
        String id = null;
        String descriere = "tema1";
        int startline = 3;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because id is null");
        try {
            Tema savedTema = TemaRepo.findOne(id);
            fail("Invalid Tema should not exist in repository");
        } catch (Exception e) {
            System.out.println("ID is null");
        }
    }

    @Test
    void testInvalidTemaDescriptionEmpty() {
        String id = "1";
        String descriere = "";
        int startline = 3;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because description is empty");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidAssignemntDescriptionNull() {
        String id = "1";
        String descriere = null;
        int startline = 3;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because description is null");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidStartlineSmall() {
        String id = "1";
        String descriere = "tema1";
        int startline = -1;
        int deadline = 5;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because startline is negative");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidStartlineBig() {
        String id = "1";
        String descriere = "tema1";
        int startline = 20;
        int deadline = 21;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because startline is bigger than 14");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidDeadLineSmall() {
        String id = "1";
        String descriere = "tema1";
        int startline = 13;
        int deadline = 0;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because deadline is 0");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidDeadlineBig() {
        String id = "1";
        String descriere = "tema1";
        int startline = 3;
        int deadline = 16;
        Tema Tema = new Tema(id, descriere, deadline, startline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because deadline is over 14");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

    @Test
    void testInvalidStartlineBiggerThanDeadline() {
        String id = "1";
        String descriere = "tema1";
        int startline = 5;
        int deadline = 3;
        Tema Tema = new Tema(id, descriere, startline, deadline);
        Tema result = TemaRepo.save(Tema);
        assertNull(result, "Should return null because startline is bigger than deadline");
        Tema savedTema = TemaRepo.findOne(id);
        assertNull(savedTema, "Invalid Tema should not exist in repository");
    }

}
