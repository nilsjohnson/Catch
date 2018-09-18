package userinterface;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/

/*
Purpose: to let a user enter a userrname, password, and confirmation password,
to listen to changes in the password, to tell the user if the passwords are valid
and match, and to let the user send the info to the server by hitting "Create
Account" button
*/

/*
Modification info:
No modifications known
*/
import java.util.ArrayList;

import authentication.Authenticator;
import authentication.NewUserException.UsernameError;
import authentication.NewUserException.PasswordError;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NewUserPane extends VBox
{
	// title
	private Text txtTitle = new Text("Make New Player");

	// to get user entry
	private GridPane entryGridPane = new GridPane();

	// containers
	private HBox buttonHBox = new HBox();
	private HBox entryContainerHBox = new HBox();
	private StackPane messageTxtPane = new StackPane();
	private VBox requirementVBox = new VBox();
	private StackPane titleStackPane = new StackPane();

	// buttons
	private Button makeUserBtn = new Button("Create Account");
	private Button cancelBtn = new Button("Cancel");

	// labels
	private Label lblServerIp = new Label("Server ip: ");
	private Label lblServerPort = new Label("Server Port: ");
	private Label lblUsername = new Label("Desired Username: ");
	private Label lblPassword = new Label("Password: ");
	private Label lblPwconfirm = new Label("Confirm: ");

	// textfields
	private TextField tfServerIp = new TextField();
	private TextField tfClientPort = new TextField();
	public TextField tfName = new TextField();
	public TextField pfPassword = new PasswordField();
	public TextField pfPasswordConfirm = new PasswordField();
	private Text txtMessageArea = new Text();

	// text to tell user about their entries
	private Label lblRequirments = new Label("");
	private Text txtMaxLength = new Text();
	private Text txtMinLength = new Text();
	private Text txtUpper = new Text("Uppercase Character(s)");
	private Text txtLower = new Text("Lowercase Character(s)");
	private Text txtNumber = new Text("Number(s)");
	private Text txtSpecial = new Text();
	private Text txtNoIllegalChar = new Text();
	private Text txtMatch = new Text("To Be Matching");

	private boolean isSetForNameEntry;
	private boolean isSetForPwEntry;

	public NewUserPane(EventHandler<ActionEvent> createAccountHandler, EventHandler<ActionEvent> cancelHandler)
	{
		// node, col, row
		entryGridPane.add(lblServerIp, 0, 0);
		entryGridPane.add(tfServerIp, 1, 0);
		entryGridPane.add(lblServerPort, 0, 1);
		entryGridPane.add(tfClientPort, 1, 1);
		entryGridPane.add(lblUsername, 0, 2);
		entryGridPane.add(tfName, 1, 2);
		entryGridPane.add(lblPassword, 0, 3);
		entryGridPane.add(pfPassword, 1, 3);
		entryGridPane.add(lblPwconfirm, 0, 4);
		entryGridPane.add(pfPasswordConfirm, 1, 4);

		// set spacing and positioning
		entryGridPane.setVgap(5);
		entryGridPane.setHgap(5);

		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.getChildren().addAll(makeUserBtn, cancelBtn);
		buttonHBox.setSpacing(5);
		buttonHBox.setPadding(new Insets(5, 5, 5, 5));


		entryContainerHBox.setAlignment(Pos.CENTER);
		entryContainerHBox.setSpacing(5);
		entryContainerHBox.setPadding(new Insets(5, 5, 0, 5));

		titleStackPane.getChildren().add(txtTitle);
		messageTxtPane.getChildren().add(txtMessageArea);

		entryContainerHBox.getChildren().addAll(entryGridPane, requirementVBox);
		this.getChildren().addAll(titleStackPane, entryContainerHBox, messageTxtPane, buttonHBox);

		setForNameEntry();
		requirementVBox.setPadding(new Insets(5, 5, 5, 5));

		makeUserBtn.setOnAction(createAccountHandler);
		cancelBtn.setOnAction(cancelHandler);

		class PwChangeListener implements ChangeListener
		{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue)
			{
				if (isSetForNameEntry)
				{
					setForPwEntry();
				}

				pfPassword.setStyle("-fx-control-inner-background: white;");
				pfPasswordConfirm.setStyle("-fx-control-inner-background: white;");

				ArrayList<PasswordError> errorList = Authenticator.checkPasswordLegality(pfPassword.getText());
				updatePwStatus(errorList);
			}
		}

		class usernameChangeListener implements ChangeListener<Object>
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
			{
				// minLengthTxt, specialTxt, maxLengthTxt
				if (isSetForPwEntry)
				{
					setForNameEntry();
				}

				tfName.setStyle("-fx-control-inner-background: white;");

				ArrayList<UsernameError> errorList = Authenticator.checkUsernameLegality(tfName.getText());
				updateNameStatus(errorList);
			}
		}

		pfPassword.textProperty().addListener(new PwChangeListener());
		pfPassword.focusedProperty().addListener(new PwChangeListener());

		tfName.textProperty().addListener(new usernameChangeListener());
		tfName.focusedProperty().addListener(new usernameChangeListener());

		pfPasswordConfirm.textProperty().addListener(new ChangeListener<Object>()
		{
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
			{
				// minLengthTxt, specialTxt, maxLengthTxt
				if (isSetForNameEntry)
				{
					setForPwEntry();
				}

				ArrayList<PasswordError> errorList = Authenticator.checkPasswordLegality(pfPassword.getText());
				updatePwStatus(errorList);
				;
			}
		});

		tfName.requestFocus();

		// use for figuring out size
		this.widthProperty().addListener(e ->
		{
			System.out.println("this.width: " + this.getWidth());
		});
		this.heightProperty().addListener(e ->
		{
			System.out.println("this.height: " + this.getHeight());
		});
	}

	private void setForNameEntry()
	{
		requirementVBox.getChildren().clear();
		lblRequirments.setText("Usernames Need:");
		txtMinLength.setText("At Least " + Authenticator.MIN_NAME_LENGTH +
				" Characters");
		txtNoIllegalChar.setText("");
		txtMaxLength.setText("");
		requirementVBox.getChildren().addAll(lblRequirments, txtMinLength, txtNoIllegalChar, txtMaxLength);
		isSetForNameEntry = true;
		isSetForPwEntry = false;
	}

	private void setForPwEntry()
	{
		requirementVBox.getChildren().clear();
		lblRequirments.setText("Passwords Need:");
		txtMinLength.setText("At Least " + Authenticator.MIN_PW_LENGTH +
				" Characters");
		txtSpecial.setText("Special Characters(s)");
		txtMaxLength.setText("");
		requirementVBox.getChildren().addAll(lblRequirments, txtMinLength, txtUpper, txtLower, txtNumber, txtSpecial, txtMatch, txtNoIllegalChar, txtMaxLength);
		isSetForNameEntry = false;
		isSetForPwEntry = true;
	}

	private void updatePwStatus(ArrayList<PasswordError> errorList)
	{
		if (errorList != null && errorList.contains(PasswordError.TOO_SHORT))
		{
			txtMinLength.setFill(Color.BLACK);
		}
		else
		{
			txtMinLength.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(PasswordError.NEEDS_UPPER))
		{
			txtUpper.setFill(Color.BLACK);
		}
		else
		{
			txtUpper.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(PasswordError.NEEDS_LOWER))
		{
			txtLower.setFill(Color.BLACK);
		}
		else
		{
			txtLower.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(PasswordError.NEEDS_NUMBER))
		{
			txtNumber.setFill(Color.BLACK);
		}
		else
		{
			txtNumber.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(PasswordError.NEEDS_SPECIAL_CHAR))
		{
			txtSpecial.setFill(Color.BLACK);
		}
		else
		{
			txtSpecial.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(PasswordError.HAS_ILLEGAL_CHAR))
		{
			txtNoIllegalChar.setText("Allowed Special Characters: \n\t" + getLegalPwSpecialChar());
			txtNoIllegalChar.setFill(Color.BLACK);

		}
		else
		{
			txtNoIllegalChar.setText("");
		}

		if (errorList != null && errorList.contains(PasswordError.TOO_LONG))
		{
			txtMaxLength.setFill(Color.BLACK);
			txtMaxLength.setText("No More Than " + Authenticator.MAX_PW_LENGTH +
					" Characters");
		}
		else
		{
			txtMaxLength.setText("");
		}

		if (!pfPassword.getText().equals(pfPasswordConfirm.getText()))
		{
			txtMatch.setFill(Color.BLACK);
		}
		else if (pfPassword.getLength() > 1)
		{
			txtMatch.setFill(Color.GREEN);
		}
	}

	private void updateNameStatus(ArrayList<UsernameError> errorList)
	{
		if (errorList != null && errorList.contains(UsernameError.TOO_SHORT))
		{
			txtMinLength.setFill(Color.BLACK);
		}
		else
		{
			txtMinLength.setFill(Color.GREEN);
		}

		if (errorList != null && errorList.contains(UsernameError.HAS_ILLEGAL_CHAR))
		{
			txtNoIllegalChar.setText("No Illegal Characters: \n\t" + getIllegalNameChar());
			txtNoIllegalChar.setFill(Color.BLACK);
		}
		else
		{
			txtNoIllegalChar.setText("");
		}
		if (errorList != null && errorList.contains(UsernameError.TOO_LONG))
		{
			txtMaxLength.setText("No More Than " + Authenticator.MAX_NAME_LENGTH +
					" Characters");
			txtMaxLength.setFill(Color.BLACK);
		}
		else
		{
			txtMaxLength.setText("");
		}

	}

	private String getLegalPwSpecialChar()
	{
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < Authenticator.LEGAL_PW_SPECIAL_CHARACTER.length; i++)
		{
			if (i < Authenticator.LEGAL_PW_SPECIAL_CHARACTER.length - 1)
			{
				str.append(Authenticator.LEGAL_PW_SPECIAL_CHARACTER[i] + ", ");
			}
			else
			{
				str.append(Authenticator.LEGAL_PW_SPECIAL_CHARACTER[i]);
			}
		}
		return str.toString();
	}

	private String getIllegalNameChar()
	{
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < Authenticator.ILLEGAL_NAME_CHARACTER.length; i++)
		{
			if (i < Authenticator.ILLEGAL_NAME_CHARACTER.length - 1)
			{
				str.append(Authenticator.ILLEGAL_NAME_CHARACTER[i] + ", ");
			}
			else
			{
				str.append(Authenticator.ILLEGAL_NAME_CHARACTER[i]);
			}
		}
		return str.toString();
	}

	public void setErrorText(String str)
	{
		txtMessageArea.setText(str);
	}

	public String getDesiredPassword()
	{
		return pfPassword.getText().trim();
	}

	public String getDesiredPasswordConfirm()
	{
		return pfPasswordConfirm.getText().trim();
	}

	public String getDesiredName()
	{
		return tfName.getText().trim();
	}

	public int getServerPort()
	{
		return Integer.parseInt(tfClientPort.getText());
	}

	public String getServerIpAddress()
	{
		return tfServerIp.getText().trim();
	}

	public void setServerPortNum(int portNum)
	{
		tfClientPort.setText(Integer.toString(portNum));
	}

	public void setServerIpAddress(String address)
	{
		tfServerIp.setText(address);
	}
}
