package ihmTable.util;

import data.Vote;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * Class to display a dialog when a vote is casted. Extends {@link Alert}
 *
 * @see Alert
 */
public class VoteAlert extends Alert {
	/**
	 * The interface with data
	 *
	 * @see InterImplDataTable
	 */
	private InterImplDataTable interImplDataTable;
	/**
	 * Label containing the count of accepted votes
	 */
	private Label acceptedCount;
	/**
	 * Label containing the count of refused votes
	 */
	private Label refusedCount;
	/**
	 *  the count of accepted votes
	 */
	private int accepted;
	/**
	 * the count of refused votes
	 */
	private int refused;
	/**
	 * Container of the dialog's content
	 */
	private VBox contentContainer;
	/**
	 * Container of the control buttons
	 */
	private HBox buttonsContainer;
	/**
	 * Create the vote dialog
	 * @param interImplDataTable the interface with data
	 *
	 * @see VoteAlert#interImplDataTable
	 */
	public VoteAlert(InterImplDataTable interImplDataTable) {
		super(AlertType.INFORMATION);
		this.initStyle(StageStyle.UNDECORATED);
		this.setHeaderText("Vote en cours");
		this.getButtonTypes().setAll(new ButtonType[0]);

		this.interImplDataTable = interImplDataTable;
		this.interImplDataTable.getActualTable().getVoteCasted().addListener((ListChangeListener.Change<? extends Vote> change) -> onVoteCastedChange(change));

		contentContainer = new VBox();
		this.getDialogPane().setContent(contentContainer);
		contentContainer.getChildren().add(new Label("Un vote a été lancé. Voulez-vous continuer à jouer ?"));

		HBox acceptedContainer = new HBox();
		acceptedContainer.setSpacing(5);
		acceptedContainer.getChildren().add(new Label("Réponses positives"));
		this.accepted = 0;
		this.acceptedCount = new Label(String.valueOf(accepted));
		acceptedContainer.getChildren().add(this.acceptedCount);

		HBox refusedContainer = new HBox();
		refusedContainer.setSpacing(5);
		refusedContainer.getChildren().add(new Label("Réponses négatives"));
		this.refused = 0;
		this.refusedCount = new Label(String.valueOf(refused));
		refusedContainer.getChildren().add(this.refusedCount);

		this.buttonsContainer = new HBox();
		this.buttonsContainer.setSpacing(10);
		this.buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
		Button buttonAccept = new Button("Accepter");
		buttonAccept.setOnAction(event -> onButtonAction(true));
		Button buttonRefuse = new Button("Refuser");
		buttonRefuse.setOnAction(event -> onButtonAction(false));

		this.buttonsContainer.getChildren().add(buttonAccept);
		this.buttonsContainer.getChildren().add(buttonRefuse);

		contentContainer.getChildren().add(acceptedContainer);
		contentContainer.getChildren().add(refusedContainer);
		contentContainer.getChildren().add(this.buttonsContainer);
	}

	/**
	 * Updated the counts label when
	 * @param change
	 */
	private void onVoteCastedChange(ListChangeListener.Change<? extends Vote> change) {
		System.out.print("test 1");
		while(change.next()) {
			if (change.wasAdded()) {
				for (Vote vote : change.getAddedSubList()) {
					if(vote.isValue()) {
						++accepted;
					} else {
						++refused;
					}
				}
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				acceptedCount.setText(String.valueOf(accepted));
				refusedCount.setText(String.valueOf(refused));
			}
		});
	}

	/**
	 * Action performed when accept or refused button are clicked
	 * @param vote whether the vote is positive
	 */
	private void onButtonAction(boolean vote) {
		this.interImplDataTable.vote(vote);
		this.contentContainer.getChildren().remove(buttonsContainer);
	}
}
