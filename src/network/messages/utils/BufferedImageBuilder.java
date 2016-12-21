package network.messages.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * @author Anthony Eden
 */
public class BufferedImageBuilder {
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static byte[] toByteArray(Image img)
	{
		BufferedImage bfi = toBufferedImage(img);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(bfi, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return baos.toByteArray();
	}
	
	public static Image toImage(byte[] img)
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		BufferedImage bfi = null;
		try {
			bfi = ImageIO.read(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return bfi;
	}
}