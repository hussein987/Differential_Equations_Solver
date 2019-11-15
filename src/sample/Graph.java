package sample;


import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Graph {

    private XYChart<Double, Double> graph;
    public XYChart.Series<Double, Double> series, eulerSeries, improvedEulerSeries, rungeKuttaSeries;
    public XYChart.Series<Double, Double> eulerSeriesLocal, improvedEulerSeriesLocal, rungeKuttaSeriesLocal;
    public XYChart.Series<Double, Double> eulerSeriesTotal, improvedEulerSeriesTotal, rungeKuttaSeriesTotal;

    public Graph(final XYChart<Double, Double> graph){
        this.graph = graph;
        series = new XYChart.Series<>();
        eulerSeries = new XYChart.Series<>();
        improvedEulerSeries = new XYChart.Series<>();
        rungeKuttaSeries = new XYChart.Series<>();
        eulerSeriesLocal = new XYChart.Series<>();
        improvedEulerSeriesLocal = new XYChart.Series<>();
        rungeKuttaSeriesLocal = new XYChart.Series<>();
        eulerSeriesTotal = new XYChart.Series<>();
        improvedEulerSeriesTotal = new XYChart.Series<>();
        rungeKuttaSeriesTotal = new XYChart.Series<>();
    }

    public void plotLine(final double x0, final double b, final double n, final Function<Double, Double> function){
        series = new XYChart.Series<>();
        for(double x = x0; x <= b; x += 0.01){
            plotPoint(x, function.apply(x), series);
        }
        series.setName("Exact Solution");
        plotSeries(series);
    }

    public XYChart.Series<Double, Double> solveEuler(final double x0, final double y0, final double b, final double n,
                                                     final BiFunction<Double, Double, Double> function){
        XYChart.Series <Double, Double> ret = new XYChart.Series<>();
        ret.setName("Euler Method");
        double y = y0;
        double xPrev = x0;
        double h = (b - x0)/n;
        plotPoint(x0, y0, ret);
        for(double x = x0 + h, i = 0; i < n; x += h, i++){
            y += h*function.apply(xPrev, y);
            xPrev = x;
            plotPoint(x, y, ret);
        }
        return ret;
    }

    public void plotEuler(final double x0, final double y0, final double b, final double n, final BiFunction<Double, Double, Double> function){
        eulerSeries = solveEuler(x0, y0, b, n, function);
        plotSeries(eulerSeries);
    }

    public XYChart.Series<Double, Double> solveEulerImproved(final double x0, final double y0, final double b, final double n,
                                                             final BiFunction<Double, Double, Double> function){
        XYChart.Series <Double, Double> ret = new XYChart.Series<>();
        ret.setName("Improved Euler Method");
        double h = (b - x0)/n;
        double k1 = function.apply(x0, y0);
        double k2 = function.apply(x0 + h, y0 + h*k1);
        double y = y0;
        plotPoint(x0, y0, ret);
        for(double x = x0 + h, i = 0; i < n; x += h, i++){
            y += (h/2)*(k1 + k2);
            k1 = function.apply(x, y);
            k2 = function.apply(x + h, y + h*k1);
            plotPoint(x, y, ret);
        }
        return ret;
    }

    public void plotEulerImproved(final double x0, final double y0, final double b, final double n, final BiFunction<Double, Double, Double> function){
        improvedEulerSeries = solveEulerImproved(x0, y0, b, n, function);
        plotSeries(improvedEulerSeries);
    }

    public XYChart.Series<Double, Double> solveRungeKutta(final double x0, final double y0, final double b, final double n,
                                                          final BiFunction<Double, Double, Double> function){
        XYChart.Series<Double, Double> ret = new XYChart.Series<>();
        ret.setName("RungeKutta Method");
        double h = (b - x0)/n;
        double k1 = function.apply(x0, y0);
        double k2 = function.apply(x0 + h/2, y0 + h*k1/2);
        double k3 = function.apply(x0 + h/2, y0 + h*k2/2);
        double k4 = function.apply(x0 + h, y0 + h*k3);
        double y = y0;
        plotPoint(x0, y0, ret);
        for(double x = x0 + h, i = 0; i < n; x += h, i++){
            y += (h/6)*(k1 + 2*k2 + 2*k3 + k4);
            k1 = function.apply(x, y);
            k2 = function.apply(x + h/2, y + h*k1/2);
            k3 = function.apply(x + h/2, y + h*k2/2);
            k4 = function.apply(x + h, y + h*k3);
            System.out.println(y);
            plotPoint(x, y, ret);
        }
        return ret;
    }

    public void plotRungeKutta(final double x0, final double y0, final double b, final double n, final BiFunction<Double, Double, Double> function){
        rungeKuttaSeries = solveRungeKutta(x0, y0, b, n, function);
        plotSeries(rungeKuttaSeries);
    }



    public void plotError(XYChart.Series<Double, Double> series1, XYChart.Series<Double, Double> series2){
        for(int i = 0; i < series1.getData().size(); i++) {
            Double x = series1.getData().get(i).getXValue();
            plotPoint(x, Math.abs(series1.getData().get(i).getYValue() - Math.exp(-3/x + 3) * Math.pow(x, 2)), series2);
        }
        series2.setName(series1.getName());
        plotSeries(series2);
    }

    public void plotTotalError(final double x0, final double y0, final double b, final int N0, int Nf,
                               final BiFunction<Double, Double, Double> function, final int flag, XYChart.Series <Double, Double> series){
        for(int i = N0; i < Nf; i++) {
            XYChart.Series <Double, Double> temp;
            if(flag == 0)
                temp = solveEuler(x0, y0, b, i, function);
            else if(flag == 1)
                temp = solveEulerImproved(x0, y0, b, i, function);
            else
                temp = solveRungeKutta(x0, y0, b, i, function);
            //Double y = temp.getData().get(temp.getData().size() - 1).getYValue();
            Double y = Math.abs(temp.getData().get(temp.getData().size() - 1).getYValue() -  Math.exp(-3/b + 3) * Math.pow(b, 2));
            plotPoint(i, y, series);
        }
        plotSeries(series);
    }

    public void clear(XYChart.Series<Double, Double> seriesToClear){
        graph.getData().removeAll(seriesToClear);
        seriesToClear.getData().clear();
    }

    private void plotSeries(XYChart.Series <Double, Double> seriesToPlot){
        graph.getData().add(seriesToPlot);

            for (XYChart.Data<Double, Double> data : seriesToPlot.getData()) {
                StackPane stackPane = (StackPane) data.getNode();
                stackPane.setVisible(!(seriesToPlot.getName() == "Exact Solution") && seriesToPlot.getData().size() < 50);
            }

        for(final XYChart.Data<Double, Double> data: seriesToPlot.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Tooltip.install(data.getNode(), new Tooltip("x = " + data.getXValue() + "\ny = " + data.getYValue()));
                }
            });
        }
    }

    private void plotPoint(final double x, final double y, final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

}