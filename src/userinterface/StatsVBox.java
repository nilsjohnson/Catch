package userinterface;

import catchgame.Packets.LeaderBoardPacket;
import catchgame.Packets.LeaderBoardRow;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StatsVBox extends VBox
{
	private Text txtTitle = new Text("Your Stats");
	private Text txtLeaderBoard = new Text("High Scores");
	
	// to hold players info
	private GridPane myStatsGridPane = new GridPane();
	// to hold the leaderBoard, lb for leader board
	private GridPane lbGridPane = null;
	
	// labels for this players stats
	private Label lblName = new Label("Your Name: ");
	private Label lblRevenue = new Label("You Have Earned: ");
	private Label lblCashOnHand = new Label("Available Money: ");
	private Label lblNumCaught = new Label("Number Sea Creatures Caught: ");
	
	// text for this players stats
	private Text txtName = new Text();
	private Text txtRevenue = new Text();
	private Text txtCashOnHand = new Text();
	private Text txtNumCaught = new Text();
	
	public StatsVBox()
	{
		myStatsGridPane.add(lblName, 0, 0);
		myStatsGridPane.add(txtName, 1, 0);
		myStatsGridPane.add(lblRevenue, 0, 1);
		myStatsGridPane.add(txtRevenue, 1, 1);
		myStatsGridPane.add(lblCashOnHand, 0, 2);
		myStatsGridPane.add(txtCashOnHand, 1, 2);
		myStatsGridPane.add(lblNumCaught, 0, 3);
		myStatsGridPane.add(txtNumCaught, 1, 3);
		
		myStatsGridPane.setHgap(5);
		myStatsGridPane.setVgap(5);
		myStatsGridPane.setAlignment(Pos.CENTER);
		
		this.setSpacing(15);
		this.setAlignment(Pos.CENTER);
				
		this.getChildren().addAll(txtTitle, myStatsGridPane, txtLeaderBoard);
	}
	
	public void setPlayerStats(String name, double totalEarned, double availableMoney, int numCaught, LeaderBoardPacket leaderBoard)
	{
		txtName.setText(name);
		txtRevenue.setText("$" + Double.toString(totalEarned));
		txtCashOnHand.setText(Double.toString(availableMoney));
		txtNumCaught.setText(Integer.toString(numCaught));
		
		
		if(this.getChildren().contains(lbGridPane))
		{
			this.getChildren().remove(lbGridPane);
		}
		
		lbGridPane = new GridPane();
		
		// init the titles
		lbGridPane.add(new Label("Name"), 0, 0);
		lbGridPane.add(new Label("Total Earnings"), 1, 0);
		lbGridPane.add(new Label("Cash On Hand"), 2, 0);
		lbGridPane.add(new Label("Total Catches"), 3, 0);
		
		

		
		int i = 0;
		while(i < leaderBoard.rows.length && leaderBoard.rows[i] != null)
		{
			LeaderBoardRow row = leaderBoard.rows[i];
			lbGridPane.add(new Text(row.name), 0, i+1);
			lbGridPane.add(new Text("$" + Double.toString(row.totalMoneyEarned)), 1, i+1);
			lbGridPane.add(new Text("$" + Double.toString(row.cashOnHand)), 2, i+1);
			lbGridPane.add(new Text(Integer.toString(row.totalCatches)), 3, i+1);
			i++;
		}
		
		lbGridPane.setHgap(5);
		lbGridPane.setVgap(5);
		
		lbGridPane.setAlignment(Pos.CENTER);
		
		this.getChildren().add(lbGridPane);
	}
}
