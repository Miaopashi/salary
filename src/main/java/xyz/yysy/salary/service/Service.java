package xyz.yysy.salary.service;

import xyz.yysy.salary.model.Respondent;

import java.util.ArrayList;

public interface Service {
    ArrayList<Double> getLayers(Iterable<Respondent> all);
    ArrayList<ArrayList<Double>> getFmChartData(ArrayList<Double> layer, Iterable<Respondent> all);
}
