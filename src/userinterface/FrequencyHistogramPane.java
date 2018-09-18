package userinterface;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/
/*
Purpose: currently simply to display a bar graph where the
height of each bar is the number of that species in the ocean,
and that has the time it was updated in the title.  All the control 
is currently handled in the pane, as there is little.
Modification Info:
new
*/
import java.text.SimpleDateFormat;
import java.util.Calendar;

import catchgame.Ocean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Matt Roberts
 * @author Thanh Lam
 */
public class FrequencyHistogramPane extends Pane
{
	
	Button btnRefresh=new Button("Refresh Data");
	
	final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    public final BarChart<String,Number> bc = 
        new BarChart<String,Number>(xAxis,yAxis);
    XYChart.Series series1;
    final static String cod="Cod";
    final static String salmon="Salmon";
    final static String tuna="Tuna";
    final static String oyster="Oyster";
    final static String lobster="Lobster";
    final static String crab="Crab";
    private Ocean ocean;
    VBox vb=new VBox();
	
	 public FrequencyHistogramPane(Ocean ocean) {
		 this.ocean=ocean;
		 

     refreshData();
     
     btnRefresh.setOnAction(new Refresh_FH_DataHandler());
     
     vb.getChildren().addAll(bc, btnRefresh);
     
     this.getChildren().add(vb);
	 }
	 
	 public class Refresh_FH_DataHandler implements EventHandler<ActionEvent>
		{
			@Override
			public void handle(ActionEvent e)
			{
				refreshData();
			}
		}
	 
	public void refreshData() {
		Calendar cal = Calendar.getInstance();

		 String time= new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
		 //Calendar.HOUR+":"+Calendar.MINUTE+":"+Calendar.SECOND
    bc.setTitle("Ocean Species Populations at "+time);
    xAxis.setLabel("Populations");       
    yAxis.setLabel("Quantity");
    
		bc.getData().removeAll(series1);
	     series1 = new XYChart.Series();
	     //series1.setName("");       
	     series1.getData().add(new XYChart.Data(cod, ocean.getCurrentCodPopulation()));
	     series1.getData().add(new XYChart.Data(salmon, ocean.getCurrentSalmonPopulation()));
	     series1.getData().add(new XYChart.Data(tuna, ocean.getCurrentTunaPopulation()));
	     series1.getData().add(new XYChart.Data(oyster, ocean.getCurrentOysterPopulation()));
	     series1.getData().add(new XYChart.Data(lobster, ocean.getCurrentLobsterPopulation()));
	     series1.getData().add(new XYChart.Data(crab, ocean.getCurrentCrabPopulation()));
	     bc.getData().addAll(series1);
	}
	
}
