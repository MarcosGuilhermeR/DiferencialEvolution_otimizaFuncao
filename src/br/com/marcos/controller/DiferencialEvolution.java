/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marcos.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author marcos
 */
public class DiferencialEvolution {

    static Individual population[];
    static final int AMOUNT_INDIVIDUALS = 20;
    static final double F = 0.5;

    public static void main(String[] args) {
        population = new Individual[AMOUNT_INDIVIDUALS];

        generatePopulation(population);
        calculate_fitness_individual(population);

        for (int i = 0; i < 1000; i++) {
            generate_new_population(population);
            Individual betterIndividual = get_better_individual(population);
            System.out.println("Menor Fitness: X="+betterIndividual.getX() + " Y=" + betterIndividual.getY() + " F= " + betterIndividual.getFitness() + " Média Fitness: " + calculate_avgfitness_population(population));
        }
        generate_new_population(population);

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

            calculateFitness(ind_experiment);

            select(population, i, ind_experiment);
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
        int i1, i2, i3;

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

        return ind_noise; //Provisiorio, mudar isso
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

    public static void evaluatePopulation(Individual population[]) {
        for (int i = 0; i < population.length; i++) {
            calculateFitness(population[i]);
        }
    }

    public static void calculateFitness(Individual individual) {
        double fitness;
        double x, y;

        x = individual.getX();
        y = individual.getY();

        fitness = 0.26 * (x * x + y * y) - 0.48 * x * y;

        individual.setFitness(fitness);
    }

    public static double calculate_avgfitness_population(Individual[] population) {
        double soma = 0;
        for (int i = 0; i < population.length; i++) {
            Individual individual = population[i];
            soma += individual.getFitness();
        }
        double media = soma / AMOUNT_INDIVIDUALS;
        return media;
    }

    public static void calculate_fitness_individual(Individual[] population) {
        double fitness;
        double x, y;

        for (int i = 0; i < population.length; i++) {
            Individual individual = population[i];

            x = individual.getX();
            y = individual.getY();

            fitness = 0.26 * (x * x + y * y) - 0.48 * x * y;

            individual.setFitness(fitness);

            population[i] = individual;
        }
    }

    private static void select(Individual[] population, int i, Individual ind_experiment) {

        if (ind_experiment.getFitness() < population[i].getFitness()) {
            population[i] = ind_experiment;
        }
    }
}
