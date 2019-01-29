import java.util.Arrays;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.RealType;

/**
 * This Op draws a cross centered on the middle of the image.
 * 
 * @author Daniel Felipe Gonzalez Obando
 */
@Plugin(type = Command.class, name = "drawCross", menuPath = "Plugins>TD 1>Draw cross")
public class DrawCross<T extends RealType<T>> implements Command {

	@Parameter(type = ItemIO.INPUT)
	private ImgPlus<T> image;

	@Parameter(required = true, min = "0")
	private double intensity = 2000;

	@Parameter(type = ItemIO.OUTPUT)
	private ImgPlus<T> resultImage;

	@Override
	public void run() {
		// Retrieving image dimensions
		long[] dimensions = new long[2];
		image.dimensions(dimensions);

		// Using streams to obtain image middle coordinates
		long[] midCoords = Arrays.stream(dimensions).map(dim -> dim / 2).toArray();

		resultImage = image.copy();
		RandomAccess<T> cursor = resultImage.randomAccess();

		long[] position = new long[2];
		// For x
		position[0] = midCoords[0];
		for (long y = 0; y < dimensions[1]; y++) {
			position[1] = y;
			cursor.setPosition(position);
			cursor.get().setReal(intensity);
		}

		// For y
		position[1] = midCoords[1];
		for (long x = 0; x < dimensions[0]; x++) {
			position[0] = x;
			cursor.setPosition(position);
			cursor.get().setReal(intensity);
		}
	}

}
