package xyz.yysy.salary.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import xyz.yysy.salary.model.Respondent;

import java.net.URLConnection;

public interface RespondentRepository extends CrudRepository<Respondent, String> {
}
