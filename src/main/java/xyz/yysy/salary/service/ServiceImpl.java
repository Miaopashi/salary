package xyz.yysy.salary.service;

import org.springframework.stereotype.Service;
import xyz.yysy.salary.model.Respondent;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ServiceImpl implements xyz.yysy.salary.service.Service {
    @Override
    public ArrayList<ArrayList<Double>> getGenderSalaryData() {
        return new ArrayList<>();
    }

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
}
