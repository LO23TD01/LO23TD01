package IHM_MAIN.src.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.internal.org.xml.sax.SAXException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import IHM_MAIN.src.IHMLobbyAPI.IncompleteProfileException;
import IHM_MAIN.src.MainApp;
import data.Profile;
import data.User;
import data.client.InterImplDataMain;

/**
	*	PersonController is the controller for the Profile window.
	*	<br>It implements utility methods to handle the displaying of
	*	a given User, and if possible, the modification of the profile.
	*/
public class PersonController {
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

		@FXML
		private   Label wonField;
		@FXML
		private  Label lostField;
		@FXML
		private  Label leaveField;

		@FXML
		private Button selectPhotoButton;
		@FXML
		private Button saveButton;

		private User user;

		private boolean okClicked = false;

		private Stage dialogStage;

		private MainApp mainApp;
		private InterImplDataMain interImplDataMain;

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
		 * @param profil The Profile object used to display.
		 */
		public void setPerson(Profile profil) {
			firstNameField.setText(profil.getFirstName());
			lastNameField.setText(profil.getSurName());
			pseudoField.setText(profil.getNickName());
			ageField.setText(Integer.toString(profil.getAge()));
			wonField.setText(Integer.toString(profil.getNbGameWon()));
			lostField.setText(Integer.toString(profil.getNbGameLost()));
			leaveField.setText(Integer.toString(profil.getNbGameAbandonned()));
			
			if (profil.getAvatar() != null){
				BufferedImage img = (BufferedImage) profil.getAvatar();
				imgField.setImage(SwingFXUtils.toFXImage(img, null));
			}
			//Image imageObject = new Image("file:view/uti.jpg");
			//imgField.setImage(imageObject);
		}

		/**
		 * Sets all fields on the window from a Profile object
		 * @param profile The profile to be displayed
		 */
		public void setProfile(Profile profile) throws IncompleteProfileException{
			if(profile.getFirstName() == null || profile.getSurName() == null ||
					profile.getNickName() == null){
				throw new IncompleteProfileException();
			}
			firstNameField.setText(profile.getFirstName());
			lastNameField.setText(profile.getSurName());
			pseudoField.setText(profile.getNickName());
			ageField.setText(Integer.toString(profile.getAge()));
			wonField.setText(Integer.toString(profile.getNbGameWon()));
			lostField.setText(Integer.toString(profile.getNbGameLost()));
			leaveField.setText(Integer.toString(profile.getNbGameAbandonned()));
		}

		/**
		 * Disables edit buttons/fields on the Profile view
		 */
		public void disableButtonsAndFields(){
			firstNameField.setDisable(true);
			lastNameField.setDisable(true);
			pseudoField.setDisable(true);
			ageField.setDisable(true);
			selectPhotoButton.setVisible(false);
			saveButton.setVisible(false);
		}

		/**
		 * Returns true if the user clicked OK, false otherwise.
		 *
		 * @return Boolean
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
				errorMessage += "Veuillez remplir le champ Pr�nom \n";
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
				InterImplDataMain intImpl = this.getInterImplDataMain();
				Profile profil = intImpl.getLocalProfile();
				profil.setNickName(pseudoField.getText());
				profil.setFirstName(firstNameField.getText());
				profil.setSurName(lastNameField.getText());
				profil.setAge(Integer.parseInt(ageField.getText()));
				BufferedImage img=SwingFXUtils.fromFXImage(imgField.getImage(), null);
				profil.setAvatar(img);
				intImpl.changeMyProfile(profil);

				okClicked = true;
				dialogStage.close();
			}
		}

		/**
		*	Handles the user changing his picture.
		*/
		@FXML
		public void handleChangePhoto(){
			FileChooser fileChooser = new FileChooser();

			//Set extension filter
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			//Show open file dialog
			File file = fileChooser.showOpenDialog(null);
			if (file != null){
				try {
					BufferedImage bufferedImage = ImageIO.read(file);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					imgField.setImage(image);
				} catch (IOException ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Could not load image");
					alert.setContentText("Could not load image");
					alert.showAndWait();
				}
			}
		}

		/**
		*	A simple method to set the Dialog Stage.
		*/
		public void setDialogStage(Stage dialogStage) {
			this.dialogStage = dialogStage;
		}

		/**
		*	handleJoinAP is the method used to join a tbale as a Player.
		*	<br>MUST be used AFTER joinTableController#setJoiningGame.
		*	@see joinTableController#setJoiningGame
		*/
		public Profile loadPersonDataFromFile(File fXmlFile) {
			Profile profil = new Profile();

			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("profile");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						profil.setFirstName(getTagValue("firstName", eElement));
						profil.setSurName(getTagValue("surName", eElement));
						profil.setNickName(getTagValue("nickName", eElement));
						profil.setAge(Integer.parseInt(getTagValue("age", eElement)));
						profil.setNbGameWon(Integer.parseInt(getTagValue("nbGameWon", eElement)));
						profil.setNbGameLost(Integer.parseInt(getTagValue("nbGameLost", eElement)));
						profil.setNbGameAbandonned(Integer.parseInt(getTagValue("nbGameAbandonned", eElement)));
					}
				}

			} catch (Exception e) { // catches ANY exception
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Could not load data");
				alert.setContentText("Could not load data from file:\n" + fXmlFile.getPath());

				alert.showAndWait();

			}
			return profil;

		}

		/**
		*	Gets the value of a given element.
		*	@param sTag a String object containing the name of the value to get.
		*	@param eElement an Element object in which to find the sTag element.
		*/
		private static String getTagValue(String sTag, Element eElement) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			return nValue.getNodeValue();
		}

		/**
		*	Sets the value of a given element.
		*	@param sTag a String object containing the name of the value to set.
		*	@param eElement an Element object in which to find the sTag element.
		*	@param value A String object containing the value to set for the tag.
		*/
		private static void setTagValue(String sTag, Element eElement, String value) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			nValue.setTextContent(value);
		}

		/**
		*	Gets the Data API implementation given in this controller
		*	@return InterImplDataMain object
		*/
		public InterImplDataMain getInterImplDataMain() {
			return interImplDataMain;
		}

		/**
		*	Sets the Data API implementation.
		*	@param interImplDataMain An InterImplDataMain object containing the implementation of the Data API.
		*/
		public void setInterImplDataMain(InterImplDataMain interImplDataMain) {
			this.interImplDataMain = interImplDataMain;
		}
}
