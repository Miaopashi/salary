package xyz.yysy.salary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yysy.salary.model.Respondent;
import xyz.yysy.salary.repository.RespondentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Service
public class ServiceImpl implements xyz.yysy.salary.service.Service {
    private final RespondentRepository respondentRepo;

    public ServiceImpl(RespondentRepository respondentRepo) {
        this.respondentRepo = respondentRepo;
    }

    @Override
    public ArrayList<Double> getLayers() { // 根据薪资分为4层
        Iterable<Respondent> all = respondentRepo.findAll();
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

        return layer;
    }

    @Override
    public ArrayList<ArrayList<Double>> getGradeChartData(ArrayList<Double> layer) { // 根据数据分层获取性别薪资差异数据
        Iterable<Respondent> all = respondentRepo.findAll();
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

    @Override
    public HashMap<String, ArrayList<ArrayList<Double>>> getFmChartData() {
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
