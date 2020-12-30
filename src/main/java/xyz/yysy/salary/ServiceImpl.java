package xyz.yysy.salary;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceImpl implements xyz.yysy.salary.Service {
    @Override
    public ArrayList<ArrayList<Double>> getGenderSalaryData() {
        ArrayList<Double> test1 = new ArrayList<>();
        ArrayList<Double> test2 = new ArrayList<>();
        ArrayList<Double> test3 = new ArrayList<>();
        ArrayList<ArrayList<Double>> chartData = new ArrayList<>();
        chartData.add(test1);
        chartData.add(test2);
        chartData.add(test3);
        return chartData;
    }
}
