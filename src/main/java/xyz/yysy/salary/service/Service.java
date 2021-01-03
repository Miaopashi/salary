package xyz.yysy.salary.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface Service {
    ArrayList<Double> getLayers();
    ArrayList<ArrayList<Double>> getGradeChartData(ArrayList<Double> layer);
    HashMap<String, ArrayList<ArrayList<Double>>> getFmChartData();
    ArrayList<ArrayList<Double>> getRadarChartData(ArrayList<Double> layer);
}
