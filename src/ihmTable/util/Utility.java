package ihmTable.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import data.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public final class Utility {

	public static Image getPlayerAvatar(User user) {
		Image image = null;
		java.awt.Image avatar = user.getPublicData().getAvatar();
		if(avatar != null) {
			image = SwingFXUtils.toFXImage(toBufferedImage(avatar), null);
		} else {
			image = new Image("/ihmTable/resources/png/user.png");
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

}
