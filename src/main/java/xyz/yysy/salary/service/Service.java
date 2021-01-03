package xyz.yysy.salary.service;

import xyz.yysy.salary.model.Respondent;

import java.util.ArrayList;

public interface Service {
    ArrayList<ArrayList<Double>> getGenderSalaryData();
    ArrayList<Double> getLayers(Iterable<Respondent> all);
}
