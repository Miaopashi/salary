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

    @GetMapping("/all")
    public HashMap<String, ArrayList<Integer>> allRespondents() {
        Iterable<Respondent> allRespondents = respondentRepo.findAll();
        // 根据薪水分为4层
        double maxSalary = allRespondents.iterator().next().getSalary();
        double minSalary = allRespondents.iterator().next().getSalary();
        for (Respondent r :
                allRespondents) {
            if (r.getSalary() > maxSalary)
                maxSalary = r.getSalary();
            if (r.getSalary() < minSalary)
                minSalary = r.getSalary();
        }
        ArrayList<Double> salaryLayer = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            salaryLayer.add((maxSalary - minSalary) * (double) i / (double) 4 + minSalary);
        }

//        ArrayList<Double> salaryLayer = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            salaryLayer.add((double) 0);
//        }
//        ArrayList<Double> salaryList = new ArrayList<>();
//        for (Respondent r :
//                allRespondents) {
//            salaryList.add(r.getSalary());
//        }
//        Collections.sort(salaryList);
//        int size = salaryList.size();
//        salaryLayer.set(0, salaryList.get(0));
//        if (size % 4 != 1) {
//            salaryLayer.set(1, (salaryList.get(size/4 - 1) + salaryList.get(size/4)) / 2);
//            if (size % 2 != 1)
//                salaryLayer.set(2, (salaryList.get(size/2 - 1) + salaryList.get(size/2)) / 2);
//            else
//                salaryLayer.set(2, salaryList.get(size/2 - 1) / 2);
//            salaryLayer.set(3, (salaryList.get(size*3/4 - 1) + salaryList.get(size*3/4)) / 2);
//        } else {
//            salaryLayer.set(1, salaryList.get(size/4 - 1) / 2);
//            salaryLayer.set(2, salaryList.get(size/2 - 1) / 2);
//            salaryLayer.set(3, salaryList.get(size*3/4 - 1) / 2);
//        }
//        salaryLayer.set(4, salaryList.get(size-1));

        ArrayList<Integer> fNum = new ArrayList<>();
        ArrayList<Integer> mNum = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int fCount, mCount;
            fCount = mCount = 0;
            for (Respondent r :
                    allRespondents) {
                double salary = r.getSalary();
                if (salary >= salaryLayer.get(i) && salary <= salaryLayer.get(i+1)) {
                    if (r.getGender().equals("f"))
                        fCount++;
                    else
                        mCount++;
                }
            }
            fNum.add(fCount);
            mNum.add(mCount);
        }
        HashMap<String, ArrayList<Integer>> result = new HashMap<>();
        result.put("fNum", fNum);
        result.put("mNum", mNum);
        return result;
    }
}
