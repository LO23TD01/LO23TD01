package ch.makery.editprofile.view;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.io.File;

import ch.makery.editprofile.MainApp;
import ch.makery.editprofile.model.Person;

public class PersonEditController {
	 @FXML
	    private TextField firstNameField;
	    @FXML
	    private TextField lastNameField;
	    @FXML
	    private TextField pseudoField;
	    @FXML
	    private TextField ageField;

	    @FXML
		private ImageView imgField;

	    private Person person;

	    private boolean okClicked = false;

	    private Stage dialogStage;

	    private MainApp mainApp;


	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    }

	    /**
	     * Sets the person to be edited in the dialog.
	     *
	     * @param person
	     */
	    public void setPerson(Person person) {
	        this.person = person;

	        firstNameField.setText(person.getFirstName());
	        lastNameField.setText(person.getLastName());
	        pseudoField.setText(person.getPseudo());
	        ageField.setText(Integer.toString(person.getAge()));
	        Image imageObject = new Image("file:ressources/images/img_test.png");
	        imgField.setImage(imageObject);

	    }
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        setPerson(mainApp.getPerson()) ;

	    }
	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     *
	     * @return
	     */
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    /**
	     * Validates the user input in the text fields.
	     *
	     * @return true if the input is valid
	     */
	    private boolean isInputValid() {
	        String errorMessage = "";

	        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
	            errorMessage += "Veuillez remplir le champ Prénom \n";
	        }
	        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
	            errorMessage += "Veuillez remplir le champ Nom\n";
	        }
	        if (pseudoField.getText() == null || pseudoField.getText().length() == 0) {
	            errorMessage += "Veuillez remplir le champ Pseudo!\n";
	        }

	        if (ageField.getText() == null || ageField.getText().length() == 0) {
	            errorMessage += "Veuillez renseigner l'age \n";
	        } else {
	            // try to parse the postal code into an int.
	            try {
	                Integer.parseInt(ageField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "L'age doit etre un entier\n";
	            }
	        }




	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	    }
	    /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {
	        if (isInputValid()) {
	            person.setFirstName(firstNameField.getText());
	            person.setLastName(lastNameField.getText());
	            person.setPseudo(pseudoField.getText());
	            person.setAge(Integer.parseInt(ageField.getText()));

	            okClicked = true;
	            //dialogStage.close();

	        }

	    }

	    /**
	     * Opens a FileChooser to let the user select an address book to load.
	     */
	    @FXML
	    private void handleOpen() {
	        FileChooser fileChooser = new FileChooser();

	        // Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "PNG files (*.png)", "*.png");
	        fileChooser.getExtensionFilters().add(extFilter);

	        // Show save file dialog
	        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

	        if (file != null) {
	        	   Image imageObject = new Image("file:"+file.toString());
	   	        imgField.setImage(imageObject);	        }
	    }

}
