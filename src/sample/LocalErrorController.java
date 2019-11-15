package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LocalErrorController implements Initializable {
    @FXML
    LineChart<Double, Double> function;

    public Graph lcoalErrorGraph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lcoalErrorGraph = new Graph(function);
    }

    public void clear(ActionEvent event){

    }

}
