package xyz.yysy.salary.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yysy.salary.model.Respondent;
import xyz.yysy.salary.repository.RespondentRepository;
import xyz.yysy.salary.service.Service;

import java.util.*;

@RestController
//@RequestMapping(path = "/data", produces = {"applicaiton/json", "text/html"})
@CrossOrigin("*")
public class AjaxController {
    private final Service service;
    private final RespondentRepository respondentRepo;

    public AjaxController(RespondentRepository respondentRepo, Service service) {
        this.respondentRepo = respondentRepo;
        this.service = service;
    }

    @GetMapping("/fm_chart")
    public HashMap<String, ArrayList<ArrayList<Double>>> fmChart() {
        return service.getFmChartData();
    }

    @GetMapping("/grade_chart")
    public ArrayList<ArrayList<Double>> gradeChart() {
       return service.getGradeChartData(service.getLayers());
    }
}
