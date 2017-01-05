package ihmTable.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import data.User;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public final class Utility {

	private static final String DEFAULT_USER_AVATAR = "/ihmTable/resources/png/user.png";

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

	// convert java.awt.Image (Profile.jave) to javafx.scene.Image
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

	public static void bindPrefProperties(Pane pane, ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		pane.prefWidthProperty().bind(widthProperty);
		pane.prefHeightProperty().bind(heightProperty);
	}
}
