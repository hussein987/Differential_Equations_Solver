package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

public class Controller implements Initializable {

    @FXML
    LineChart<Double, Double> function;

    @FXML
    LineChart<Double, Double> function1;

    @FXML
    LineChart<Double, Double> function2;

    @FXML
    TextField x0Field;

    @FXML
    TextField y0Field;

    @FXML
    TextField XField;

    @FXML
    TextField NField;

    @FXML
    TextField x0Field1;

    @FXML
    TextField y0Field1;

    @FXML
    TextField XField1;

    @FXML
    TextField NField1;

    @FXML
    TextField x0Field2;

    @FXML
    TextField y0Field2;

    @FXML
    TextField XField2;

    @FXML
    TextField NField2;

    @FXML
    TextField N0Field;

    @FXML
    TextField NfField;

    BiFunction<Double, Double, Double> f = (x, y) -> (3*y + 2*x*y)/Math.pow(x, 2);
    public Graph mygraph, mygraph1, mygraph2;
    int x0, y0, X, N, a;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mygraph = new Graph(function);
        mygraph1 = new Graph(function1);
        mygraph2 = new Graph(function2);
    }

    boolean loadValues(){
        if(x0Field.getText().isEmpty() || y0Field.getText().isEmpty() || NField.getText().isEmpty() || NField.getText().isEmpty()){
            AlertBox.display("Error", "Some Values are empty pelase fill all the values and try again");
            return false;
        }
        // solve the issue (Please enter some value)
        x0 = Integer.parseInt(x0Field.getText());
        x0Field1.setText(x0Field.getText());
        x0Field2.setText(x0Field.getText());
        y0 = Integer.parseInt(y0Field.getText());
        y0Field1.setText(y0Field.getText());
        y0Field2.setText(y0Field.getText());
        X = Integer.parseInt(XField.getText());
        XField1.setText(XField.getText());
        XField2.setText(XField.getText());
        N = Integer.parseInt(NField.getText());
        NField1.setText(NField.getText());
        NField2.setText(NField.getText());
        return true;
    }

    boolean loadValues1(){
        if(x0Field1.getText().isEmpty() || y0Field1.getText().isEmpty() || NField1.getText().isEmpty() || NField1.getText().isEmpty()){
            AlertBox.display("Error", "Some Values are empty pelase fill all the values and try again");
            return false;
        }
        x0 = Integer.parseInt(x0Field1.getText());
        x0Field.setText(x0Field1.getText());
        x0Field2.setText(x0Field1.getText());
        y0 = Integer.parseInt(y0Field1.getText());
        y0Field.setText(y0Field1.getText());
        y0Field2.setText(y0Field1.getText());
        X = Integer.parseInt(XField1.getText());
        XField.setText(XField1.getText());
        XField2.setText(XField1.getText());
        N = Integer.parseInt(NField1.getText());
        NField.setText(NField1.getText());
        NField2.setText(NField1.getText());
        return true;
    }

    boolean loadValues2(){
        if(x0Field2.getText().isEmpty() || y0Field2.getText().isEmpty() || NField2.getText().isEmpty() || NField2.getText().isEmpty()){
            AlertBox.display("Error", "Some Values are empty pelase fill all the values and try again");
            return false;
        }
        x0 = Integer.parseInt(x0Field2.getText());
        x0Field.setText(x0Field2.getText());
        x0Field1.setText(x0Field2.getText());
        y0 = Integer.parseInt(y0Field2.getText());
        y0Field.setText(y0Field2.getText());
        y0Field1.setText(y0Field2.getText());
        X = Integer.parseInt(XField2.getText());
        XField.setText(XField2.getText());
        XField1.setText(XField2.getText());
        N = Integer.parseInt(NField2.getText());
        NField.setText(NField2.getText());
        NField1.setText(NField2.getText());
        return true;
    }

    public void LoadChart(ActionEvent event) {
        //function.getData().clear();
        if(loadValues()) {
            mygraph.clear(mygraph.series);
            mygraph.plotLine(x0, X, N, x -> Math.exp(-3 / x + 3) * Math.pow(x, 2));
        }
    }

    public void Euler(ActionEvent event) {
        if (loadValues()) {
            mygraph.clear(mygraph.eulerSeries);
            mygraph.plotEuler(x0, y0, X, N, f);
        }
    }

    public void ImprovedEuler(ActionEvent event) {
        if (loadValues()) {
            mygraph.clear(mygraph.improvedEulerSeries);
            mygraph.plotEulerImproved(x0, y0, X, N, f);
        }
    }

    public void RungeKutta(ActionEvent event) {
        if (loadValues()) {
            mygraph.clear(mygraph.rungeKuttaSeries);
            mygraph.plotRungeKutta(x0, y0, X, N, f);
        }
    }

    public void Clear(ActionEvent event) {
        function.getData().clear();
        mygraph.clear(mygraph.series);
        mygraph.clear(mygraph.eulerSeries);
        mygraph.clear(mygraph.improvedEulerSeries);
        mygraph.clear(mygraph.rungeKuttaSeries);
    }



    /** --------------------------------- Actions For Local Errors ---------------------------------------- **/
    public void eulerError(ActionEvent event) {
        if (loadValues1()) {
            mygraph1.eulerSeries = mygraph1.solveEuler(x0, y0, X, N, f);
            mygraph1.clear(mygraph1.eulerSeriesLocal);
            mygraph1.eulerSeriesLocal = new XYChart.Series<>();
            mygraph1.plotError(mygraph1.eulerSeries, mygraph1.eulerSeriesLocal);
        }
    }

    public void improvedEulerError(ActionEvent event) {
        if (loadValues1()) {
            mygraph1.improvedEulerSeries = mygraph1.solveEulerImproved(x0, y0, X, N, f);
            mygraph1.clear(mygraph1.improvedEulerSeriesLocal);
            mygraph1.improvedEulerSeriesLocal = new XYChart.Series<>();
            mygraph1.plotError(mygraph1.improvedEulerSeries, mygraph1.improvedEulerSeriesLocal);
        }
    }

    public void rungeKuttaError(ActionEvent event) {
        if (loadValues1()) {
            mygraph1.rungeKuttaSeries = mygraph1.solveRungeKutta(x0, y0, X, N, f);
            mygraph1.clear(mygraph1.rungeKuttaSeriesLocal);
            mygraph1.rungeKuttaSeriesLocal = new XYChart.Series<>();
            mygraph1.plotError(mygraph1.rungeKuttaSeries, mygraph1.rungeKuttaSeriesLocal);
        }
    }

    public void ClearError(ActionEvent event) {
        function1.getData().clear();
        mygraph1.clear(mygraph1.eulerSeriesLocal);
        mygraph1.clear(mygraph1.improvedEulerSeriesLocal);
        mygraph1.clear(mygraph1.rungeKuttaSeriesLocal);
    }



    /** --------------------------------- Actions For Total Errors ---------------------------------------- **/
    public void eulerTotal(ActionEvent event) {
        if (loadValues2() && !N0Field.getText().isEmpty() && !NfField.getText().isEmpty()) {
            mygraph2.clear(mygraph2.eulerSeriesTotal);
            mygraph2.eulerSeriesTotal = new XYChart.Series<>();
            mygraph2.eulerSeriesTotal.setName("Euler Method");
            mygraph2.plotTotalError(x0, y0, X, Integer.parseInt(N0Field.getText()),
                    Integer.parseInt(NfField.getText()), f, 0, mygraph2.eulerSeriesTotal);
        }
    }

    public void improvedEulerTotal(ActionEvent event) {
        if (loadValues2() && !N0Field.getText().isEmpty() && !NfField.getText().isEmpty()) {
            mygraph2.clear(mygraph2.improvedEulerSeriesTotal);
            mygraph2.improvedEulerSeriesTotal = new XYChart.Series<>();
            mygraph2.improvedEulerSeriesTotal.setName("Improved Euler Method");
            mygraph2.plotTotalError(x0, y0, X, Integer.parseInt(N0Field.getText()),
                    Integer.parseInt(NfField.getText()), f, 1, mygraph2.improvedEulerSeriesTotal);
        }
    }

    public void rungeKuttaTotal(ActionEvent event) {
        if (loadValues2() && !N0Field.getText().isEmpty() && !NfField.getText().isEmpty()) {
            mygraph2.clear(mygraph2.rungeKuttaSeriesTotal);
            mygraph2.rungeKuttaSeriesTotal = new XYChart.Series<>();
            mygraph2.rungeKuttaSeriesTotal.setName("RungeKutta Method");
            mygraph2.plotTotalError(x0, y0, X, Integer.parseInt(N0Field.getText()),
                    Integer.parseInt(NfField.getText()), f, 2, mygraph2.rungeKuttaSeriesTotal);
        }
    }

    public void ClearErrorTotal(ActionEvent event) {
        function2.getData().clear();
        mygraph2.clear(mygraph2.eulerSeriesTotal);
        mygraph2.clear(mygraph2.improvedEulerSeriesTotal);
        mygraph2.clear(mygraph2.rungeKuttaSeriesTotal);
    }


}

