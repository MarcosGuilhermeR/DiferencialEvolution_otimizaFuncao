/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marcos.controller;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author trabalho
 */
public class GerarGrafico extends JFrame {

    public GerarGrafico(XYSeries series1, XYSeries series2, String label) {
        super("Resultado Gráfico RNA MultiCamadas");

        JPanel chartPanel = createChartPanel(series1, series2, label);
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(XYSeries series1, XYSeries series2, String label) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        String chartTitle = label;
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        return new ChartPanel(chart);
    }

    public static void exec(XYSeries series1, XYSeries series2, String label) {
        new GerarGrafico(series1, series2, label).setVisible(true);
    }

}
