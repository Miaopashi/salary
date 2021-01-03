package xyz.yysy.salary.service;

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

    @Override
    public ArrayList<ArrayList<Double>> getRadarChartData(ArrayList<Double> layer) {
        Iterable<Respondent> all = respondentRepo.findAll();
        int count1, count2, count3, count4, count5, count6, count7, count8;
        double sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8;
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Double> temp = new ArrayList<>();
            count1 = count2 = count3 = count4 = count5 = count6 = count7 = count8 = 0;
            sum1 = sum2 = sum3 = sum4 = sum5 = sum6 = sum7 = sum8 = 0;
            for (Respondent r :
                    all) {
                if (r.getSalary() >= layer.get(i) && r.getSalary() < layer.get(i + 1)) {
                    count1++;
                    sum1 += r.getConscientiousness();
                    count2++;
                    sum2 += r.getAgreeableness();
                    count3++;
                    sum3 += r.getExtraversion();
                    count4++;
                    sum4 += r.getNueroticism();
                    count5++;
                    sum5 += r.getOpenness();
                    count6++;
                    sum6 += r.getEnglish();
                    count7++;
                    sum7 += r.getLogical();
                    count8++;
                    sum8 += r.getQuant();
                }
            }
            temp.add(sum1 / count1 + 1);
            temp.add(sum2 / count2 + 1);
            temp.add(sum3 / count3 + 1);
            temp.add(sum4 / count4 + 1);
            temp.add(sum5 / count5 + 1);
            temp.add(sum6 / count6);
            temp.add(sum7 / count7);
            temp.add(sum8 / count8);
            result.add(temp);
        }
        return result;
    }
}
