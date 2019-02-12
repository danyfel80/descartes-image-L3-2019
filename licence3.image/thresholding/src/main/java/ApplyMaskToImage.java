import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.RealType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 3>Apply mask to image")
public class ApplyMaskToImage<T extends RealType<T>, M extends RealType<M>> implements Command {

	@Parameter(persist = false)
	ImgPlus<T> inputImage;

	@Parameter(persist = false)
	ImgPlus<M> mask;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<T> maskedImage;

	@Override
	public void run() {
		// Create masked image as a copy of input image
		maskedImage = inputImage.copy();

		// Use cursors for input, mask, and result images
		Cursor<T> inputImageCursor = inputImage.localizingCursor();
		RandomAccess<M> maskCursor = mask.randomAccess();
		RandomAccess<T> resultImageCursor = maskedImage.randomAccess();

		long[] imageDimensions = new long[inputImage.numDimensions()];
		inputImage.dimensions(imageDimensions);

		long[] imagePosition = new long[imageDimensions.length];
		long[] maskPosition = new long[imageDimensions.length];
		while (inputImageCursor.hasNext()) {
			inputImageCursor.fwd();
			// set imagePosition to current input image cursor position
			inputImageCursor.localize(imagePosition);
			inputImageCursor.localize(maskPosition);
			maskPosition[2] = 0; // mask has only one channel

			// set mask and result cursors to current image position
			maskCursor.setPosition(maskPosition);
			resultImageCursor.setPosition(imagePosition);

			// TODO Affectez la valeur du pixel sur l'image resultat selon la valeur de la
			// masque.
		}

	}

}
