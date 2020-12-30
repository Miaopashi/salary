package xyz.yysy.salary.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceImpl implements xyz.yysy.salary.service.Service {
    @Override
    public ArrayList<ArrayList<Double>> getGenderSalaryData() {
        return new ArrayList<>();
    }
}
