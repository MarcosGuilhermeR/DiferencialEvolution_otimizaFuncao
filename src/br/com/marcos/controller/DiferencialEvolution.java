/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marcos.controller;

/**
 *
 * @author marcos
 */
public class DiferencialEvolution {
    
    static Individual population [];
    
    public static void main(String[] args) {
        population = new Individual[20];
        
        generatePopulation(population);
        
    }
    
    public static void generatePopulation (Individual population[]){
        double x,y, number;
        Individual individual;
        for (int i = 0; i < population.length; i++) {
            number = Math.random();
            x = number*20 - 10;
            
            number = Math.random();
            y = number*20 - 10;
            
            individual = new Individual();
            individual.setX(x);
            individual.setY(y);
            
            population[i] = individual;
        }
    }
}
