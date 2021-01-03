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

    @GetMapping("/grade_chart")
    public ArrayList<ArrayList<Double>> gradeChart() {
        Iterable<Respondent> all = respondentRepo.findAll();

        // 根据薪资分为4层
        ArrayList<Double> layer = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            layer.add((double) 0);
        }
        ArrayList<Double> salary = new ArrayList<>();
        for (Respondent r :
                all) {
            salary.add(r.getSalary());
        }
        Collections.sort(salary);
        int size = salary.size();
        layer.set(0, salary.get(0));
        if (size % 4 != 1) {
            layer.set(1, (salary.get(size/4 - 1) + salary.get(size/4)) / 2);  // 下四分位点
            if (size % 2 != 1)
                layer.set(2, (salary.get(size/2 - 1) + salary.get(size/2)) / 2);
            else
                layer.set(2, salary.get(size/2 - 1));
            layer.set(3, (salary.get(size*3/4 - 1) + salary.get((size*3/4)) / 2));  // 上四分位点
        } else {
            layer.set(1, salary.get(size/4 - 1));  // 下四分位点
            layer.set(2, salary.get(size/2 - 1));
            layer.set(3, salary.get(size*3/4 - 1));  // 上四分位点
        }
        layer.set(4, salary.get(size - 1));

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
