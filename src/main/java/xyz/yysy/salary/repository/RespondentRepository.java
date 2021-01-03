package xyz.yysy.salary.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.yysy.salary.model.Respondent;

public interface RespondentRepository extends CrudRepository<Respondent, String> {
}
