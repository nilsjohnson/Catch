package catchgame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import authentication.BadPasswordException;
import authentication.BadUsernameException;
import authentication.NewUserException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DatabaseManipulator
{
	private UserDAO userDAO;
	private Stage stage = new Stage();
	private Scene scene;
	private DataEntryVBox dataEntryVBox;
	
	// TODO-
	// make so admin can add user - in progress
	// make so admin can delete user by name - use method in userDAO.java
	// view all users - needs a new method in userDAO.java, and in IUserDAO.java
	// view all users online - use method in userDAO.java
	
	public DatabaseManipulator()
	{
		// define actions
		class MakeUserAction implements EventHandler
		{

			@Override
			public void handle(Event e)
			{
				try
				{
					userDAO.createUser(dataEntryVBox.getTfUsername(), dataEntryVBox.getTfPassword(), dataEntryVBox.getTfPassword());
					Player player = new Player(dataEntryVBox.getTfUsername());
					player.addMoney(Integer.parseInt(dataEntryVBox.getTfCashOnHand()));
					userDAO.savePlayer(player);
				}
				catch(NewUserException e1)
				{
					if (e1 instanceof BadPasswordException)
					{
						BadPasswordException exception = (BadPasswordException)e1;
						for(int i = 0; i < exception.getErrorList().size(); i++)
						{
							System.out.println(exception.getErrorList().get(i));
						}
					}
					if (e1 instanceof BadUsernameException)
					{
						BadUsernameException exception = (BadUsernameException)e1;
						for(int i = 0; i < exception.getErrorList().size(); i++)
						{
							System.out.println(exception.getErrorList().get(i));
						}
					}
				}
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		try
		{
			userDAO = new UserDAO();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// show the GUI
		dataEntryVBox = new DataEntryVBox(new MakeUserAction());
		scene = new Scene(dataEntryVBox, 450, 200);
		stage.setTitle("Exercise31_09Client");
		stage.setScene(scene);
		stage.show();
	}
	
	private class DataEntryVBox extends VBox
	{
		private GridPane addUserGridPane = new GridPane();
		private Button btnCreateUser = new Button("Create User");
		
		//labls for new user
		Label lblUsername = new Label("Username: ");
		Label lblPassword = new Label("Password");
		Label lblCashOnHand = new Label("Cash On Hand: ");
		
		// text fields for new user
		TextField tfUsername = new TextField();
		TextField tfPassword = new TextField();
		TextField tfCashOnHand = new TextField();
		
		public DataEntryVBox(EventHandler<ActionEvent> makeUserAction)
		{
			//set gridpane node, col, row
			addUserGridPane.add(lblUsername, 0, 0);
			addUserGridPane.add(tfUsername, 1, 0);
			addUserGridPane.add(lblPassword, 0, 1);
			addUserGridPane.add(tfPassword, 1, 1);
			addUserGridPane.add(lblCashOnHand, 0, 2);
			addUserGridPane.add(tfCashOnHand, 1, 2);
			
			// set actions
			btnCreateUser.setOnAction(makeUserAction);
			
			// spacing
			addUserGridPane.setVgap(5);
			this.setSpacing(5);
			
			this.getChildren().addAll(addUserGridPane, btnCreateUser);
			
		}
		
		public String getTfUsername()
		{
			return this.tfUsername.getText();
		}
		
		public String getTfPassword()
		{
			return this.tfPassword.getText();
		}
		
		public String getTfCashOnHand()
		{
			return this.tfCashOnHand.getText();
		}
		
		
	}
}
