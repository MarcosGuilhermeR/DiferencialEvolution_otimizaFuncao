/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marcos.controller;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author marcos
 */



public class DiferencialEvolution {

    static Individual population[];
    static final int AMOUNT_INDIVIDUALS = 20;

    public static void main(String[] args) {
        population = new Individual[AMOUNT_INDIVIDUALS];

        generatePopulation(population);
        cross_breeding(population);

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

    public static void cross_breeding(Individual population[]) {
        Individual vector_noise;
        for (int i = 0; i < population.length; i++) {
            vector_noise = diference_and_mutation(population, i);
        }
    }
    
    public static Individual diference_and_mutation (Individual population[], int i){
         Individual  ind1, ind2, ind3, ind_noise = new Individual();
        int i1, i2, i3;
        
        
        
        int numbers[] = random_numbers_not_repeat(0,AMOUNT_INDIVIDUALS-1, 3);
        
        while (numbers[0] == i || numbers[1] == i  || numbers[2] == i ) {
            numbers = random_numbers_not_repeat(0,AMOUNT_INDIVIDUALS-1, 3);
        }
        
        ind1 = population[numbers[0]];
        ind2 = population[numbers[1]];
        ind3 = population[numbers[2]];
        
        
        ind_noise.setX(ind1.getX()-ind2.getX());
        ind_noise.setY(ind1.getY()-ind2.getY());
        
        
    }
    
    static int[] random_numbers_not_repeat(int a, int b, int amount_numbers){
        int size_vector = amount_numbers;
        
        int numbers[] = new int[b-a + 1];
        
        for (int i = a; i < numbers.length; i++) {
            numbers[i] = i;
        }
        
        Arrays.sort(numbers);
        
        int vector_return[] = new int[size_vector];
        for (int i = 0; i < size_vector; i++) {
            vector_return[i] = numbers[i];
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
}
