/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marcos.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author marcos
 */
public class DiferencialEvolution {

    static Individual population[];
    static final int AMOUNT_INDIVIDUALS = 20;
    static final double F = 0.5;

    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        XYSeries melhorFitness = new XYSeries("Imagem Melhor Fitness");
        XYSeries mediaFitness = new XYSeries("Imagem Media Indivíduos");
    
        population = new Individual[AMOUNT_INDIVIDUALS];

        generatePopulation(population);
        calculate_fitness_population(population);

        for (int i = 0; i < 1000; i++) {
            generate_new_population(population);
            Individual betterIndividual = get_better_individual(population);
            double media = get_avg_fitness_population(population);
            System.out.println("img ind melhor Fitness: X=" + betterIndividual.getX() + " Y=" + betterIndividual.getY() + " F= " + betterIndividual.getFitness() + " img Média individuos: " + media);
            melhorFitness.add(i, betterIndividual.getFitness());
            mediaFitness.add(i, media);
        }
        
        XYSeriesCollection graficoFitness = new XYSeriesCollection();
        
        graficoFitness.addSeries(melhorFitness);
        graficoFitness.addSeries(mediaFitness);
        
        JFreeChart chartQtde = ChartFactory.createXYLineChart("Imagem x Época", "Época", "Imagem", graficoFitness, PlotOrientation.VERTICAL, true, true, false);
        
        OutputStream imgGraf1 = new FileOutputStream("Fitness.png");
        ChartUtilities.writeChartAsPNG(imgGraf1, chartQtde, 2000, 1000);
        
        
        GerarGrafico.exec(melhorFitness, mediaFitness, "Imagem x Época");
        
    }

    public static void generatePopulation(Individual population[]) {
        double x, y, number;
        Individual individual;
        for (int i = 0; i < population.length; i++) {
            number = Math.random();
            x = number * 20 - 10;

            number = Math.random();
            y = number * 20 - 10;

            individual = new Individual();
            individual.setX(x);
            individual.setY(y);

            population[i] = individual;
        }
    }

    public static void generate_new_population(Individual population[]) {
        Individual vector_noise, ind_experiment;
        for (int i = 0; i < population.length; i++) {
            vector_noise = diference_and_mutation(population, i);
            ind_experiment = cross_breeding(population[i], vector_noise);

            calculate_fitness_individual(ind_experiment);

            select_better_individual(population, i, ind_experiment);
        }
    }

    public static Individual get_better_individual(Individual population[]) {
        double menorFitness = population[0].getFitness();
        Individual betterIndividual = population[0];

        for (int i = 1; i < population.length; i++) {
            Individual individual = population[i];

            if (individual.getFitness() < menorFitness) {
                menorFitness = individual.getFitness();
                betterIndividual = population[i];
            }
        }

        return betterIndividual;
    }

    public static Individual cross_breeding(Individual ind_i, Individual ind_noise) {
        Individual ind_experiment = new Individual();

        double number = Math.random();

        if (number <= 0.8) {
            ind_experiment.setX(ind_i.getX());
            ind_experiment.setY(ind_noise.getY());
        } else {
            ind_experiment.setX(ind_noise.getX());
            ind_experiment.setY(ind_i.getY());
        }

        return ind_experiment;
    }

    public static Individual diference_and_mutation(Individual population[], int i) {
        Individual ind1, ind2, ind3, ind_diference = new Individual(), ind_noise = new Individual();

        int numbers[] = random_numbers_not_repeat(0, AMOUNT_INDIVIDUALS - 1, 3);

        while (numbers[0] == i || numbers[1] == i || numbers[2] == i) {
            numbers = random_numbers_not_repeat(0, AMOUNT_INDIVIDUALS - 1, 3);
        }

        ind1 = population[numbers[0]];
        ind2 = population[numbers[1]];
        ind3 = population[numbers[2]];

        ind_diference.setX(F * (ind1.getX() - ind2.getX()));
        ind_diference.setY(F * (ind1.getY() - ind2.getY()));

        ind_noise.setX(ind_diference.getX() + ind3.getX());
        ind_noise.setY(ind_diference.getY() + ind3.getY());

        return ind_noise;
    }

    static int[] random_numbers_not_repeat(int a, int b, int amount_numbers) {
        int size_vector = amount_numbers;

        List<Integer> numbers = new ArrayList<>();
        for (int i = a; i <= b; i++) {

            numbers.add(i);
        }

        Collections.shuffle(numbers);

        int vector_return[] = new int[size_vector];
        for (int i = 0; i < size_vector; i++) {
            vector_return[i] = numbers.get(i);
        }

        return vector_return;
    }

    public static void calculate_fitness_individual(Individual individual) {
        double fitness;
        double x, y;

        x = individual.getX();
        y = individual.getY();

        fitness = 0.26 * (x * x + y * y) - 0.48 * x * y;

        individual.setFitness(fitness);
    }

    public static double get_avg_fitness_population(Individual[] population) {
        double soma = 0;
        for (int i = 0; i < population.length; i++) {
            Individual individual = population[i];
            soma += individual.getFitness();
        }
        double media = soma / AMOUNT_INDIVIDUALS;
        return media;
    }

    public static void calculate_fitness_population(Individual[] population) {

        for (int i = 0; i < population.length; i++) {
            calculate_fitness_individual(population[i]);
        }
    }

    private static void select_better_individual(Individual[] population, int i, Individual ind_experiment) {

        if (ind_experiment.getFitness() < population[i].getFitness()) {
            population[i] = ind_experiment;
        }
    }
}
