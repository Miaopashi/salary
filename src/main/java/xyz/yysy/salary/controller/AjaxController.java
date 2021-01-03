package xyz.yysy.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private RespondentRepository respondentRepo;

    public AjaxController(RespondentRepository respondentRepo, Service service) {
        this.respondentRepo = respondentRepo;
        this.service = service;
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

    @GetMapping("/grade_chart")
    public ArrayList<ArrayList<Double>> gradeChart() {
        Iterable<Respondent> all = respondentRepo.findAll();

        // 根据薪资分为4层
        ArrayList<Double> layer = service.getLayers(all);

        // 统计各层的数据
        int gpaCount, perCount;
        double gpaSum, perSum;
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Double> avg = new ArrayList<>();
            gpaCount = perCount = 0;
            gpaSum = perSum = 0;
            for (Respondent r :
                    all) {
                if (r.getSalary() >= layer.get(i) && r.getSalary() < layer.get(i + 1)) {
                    gpaCount++;
                    gpaSum += r.getCollegeGPA();
                    perCount++;
                    perSum += r.getPercentage12();
                }
            }
            avg.add(perSum / perCount);
            avg.add(gpaSum / gpaCount);
            result.add(avg);
        }
        return result;
    }
}
