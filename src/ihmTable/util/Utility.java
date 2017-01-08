package ihmTable.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import data.User;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Utility class which provides some utility methods
 */
public final class Utility {

	/**
	 * The default user avatar
	 */
	private static final String DEFAULT_USER_AVATAR = "/ihmTable/resources/png/user.png";

	/**
	 * Return the given user's avatar
	 * @param user the user
	 * @return The image corresponding the user's avatar
	 */
	public static Image getPlayerAvatar(User user) {
		Image image = null;
		java.awt.Image avatar = user.getPublicData().getAvatar();
		if(avatar != null) {
			image = SwingFXUtils.toFXImage(toBufferedImage(avatar), null);
		} else {
			image = new Image(DEFAULT_USER_AVATAR);
		}
		return image;
	}

	/**
	 * Convert an java.awt.Image to BufferedImage
	 * @param img
	 * @return the buffered image
	 *
	 * @see java.awt.Image
	 * @see BufferedImage
	 */
	public static BufferedImage toBufferedImage(java.awt.Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
	}

	/**
	 * Bind pref properties (width and height) of the given pane
	 * @param pane the pane
	 * @param widthProperty the width property for the pane
	 * @param heightProperty the height property for the height pane
	 */
	public static void bindPrefProperties(Pane pane, ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		pane.prefWidthProperty().bind(widthProperty);
		pane.prefHeightProperty().bind(heightProperty);
	}

	/**
	 * Bind pref properties (width and height) of the given label
	 * @param label the label
	 * @param widthProperty the width property for the pane
	 * @param heightProperty the height property for the height pane
	 */
	public static void bindPrefProperties(Label label, ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		label.prefWidthProperty().bind(widthProperty);
		label.prefHeightProperty().bind(heightProperty);
	}
}
