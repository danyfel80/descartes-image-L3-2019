package solution;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 3 Solution>Threshold image (Solved)")
public class ThresholdImageSolution<T extends RealType<T>> implements Command {

	@Parameter(persist = false)
	ImgPlus<T> inputImage;

	@Parameter(required = false)
	int threshold = 127;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<UnsignedByteType> mask;

	@Override
	public void run() {
		// dimensions holds the size of the input image in x and y
		long[] imageDimensions = new long[inputImage.numDimensions()];
		long[] maskDimensions = new long[inputImage.numDimensions()];
		inputImage.dimensions(imageDimensions);
		inputImage.dimensions(maskDimensions);
		maskDimensions[2] = 1;
		// Creation of the resulting image with the same size as the input image.

		mask = ImgPlus.wrap(ArrayImgs.unsignedBytes(maskDimensions));
		mask.setName(inputImage.getName() + "_Mask");

		// Two random cursor to visit all pixels in the input and output images.
		RandomAccess<T> inputImageCursor = inputImage.randomAccess();
		RandomAccess<UnsignedByteType> maskCursor = mask.randomAccess();

		long[] currentPosition = new long[imageDimensions.length];
		// 1. Parcourir toutes les lignes de l'image
		for (int i = 0; i < imageDimensions[0]; i++) {
			// 2. Parcourir toutes les colonnes de l'image
			for (int j = 0; j < imageDimensions[1]; j++) {
				// 3. affecter la position aux curseurs
				currentPosition[0] = i;
				currentPosition[1] = j;
				inputImageCursor.setPosition(currentPosition);
				maskCursor.setPosition(currentPosition);

				// 4. obtenir intensite de l'image a la position currentPosition
				if (inputImageCursor.get().getRealDouble() > threshold) {
					// 5. affecter pixel de l'image de sortie
					maskCursor.get().set(1);
				}
			}
		}
	}

}
