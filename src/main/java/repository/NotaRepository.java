package repository;

import domain.Nota;
import validation.Validator;

public class NotaRepository extends AbstractCRUDRepository<String, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
