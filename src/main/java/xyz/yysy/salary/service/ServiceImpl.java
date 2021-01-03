package xyz.yysy.salary.service;

import org.springframework.stereotype.Service;
import xyz.yysy.salary.model.Respondent;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ServiceImpl implements xyz.yysy.salary.service.Service {
    @Override
    public ArrayList<Double> getLayers(Iterable<Respondent> all) {
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
    public ArrayList<ArrayList<Double>> getFmChartData(ArrayList<Double> layer, Iterable<Respondent> all) {
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
