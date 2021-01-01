package xyz.yysy.salary.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yysy.salary.model.Respondent;
import xyz.yysy.salary.repository.RespondentRepository;

import java.util.Optional;

@RestController
//@RequestMapping(path = "/data", produces = "applicaiton/json")
@CrossOrigin("*")
public class AjaxController {
    private RespondentRepository respondentRepo;

    public AjaxController(RespondentRepository respondentRepo) {
        this.respondentRepo = respondentRepo;
    }

    @GetMapping("/all")
    public Iterable<Respondent> allRespondents() {
        return respondentRepo.findAll();
    }
}
