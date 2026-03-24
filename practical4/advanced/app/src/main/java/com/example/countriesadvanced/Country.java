package com.example.countriesadvanced;

import java.io.Serializable;

public class Country implements Serializable {
    private String name;
    private String capital;
    private long population;
    private double area;

    public Country(String name, String capital, long population, double area) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.area = area;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
}
