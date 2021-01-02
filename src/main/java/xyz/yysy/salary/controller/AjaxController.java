package xyz.yysy.salary.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yysy.salary.model.Respondent;
import xyz.yysy.salary.repository.RespondentRepository;

import java.util.*;

@RestController
//@RequestMapping(path = "/data", produces = {"applicaiton/json", "text/html"})
@CrossOrigin("*")
public class AjaxController {
    private RespondentRepository respondentRepo;

    public AjaxController(RespondentRepository respondentRepo) {
        this.respondentRepo = respondentRepo;
    }

    @GetMapping("/fm_chart")
    public HashMap<String, ArrayList<ArrayList<Double>>> fmChart() {
        Iterable<Respondent> allRespondents = respondentRepo.findAll();
        ArrayList<Double> fSalary = new ArrayList<>();
        ArrayList<Double> mSalary = new ArrayList<>();
        for (Respondent r :
                allRespondents) {
            if (r.getGender().equals("f"))
                fSalary.add(r.getSalary());
            else
                mSalary.add(r.getSalary());
        }
        ArrayList<ArrayList<Double>> allSalary = new ArrayList<>();
        allSalary.add(fSalary);
        allSalary.add(mSalary);
        HashMap<String, ArrayList<ArrayList<Double>>> result = new HashMap<>();
        result.put("data_salary", allSalary);
        return result;
    }
}
