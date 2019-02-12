package histogram;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.Cursor;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

@Plugin(type = Command.class, name = "expandHistogram", menuPath = "Plugins>TD 2>Expand histogram")
public class ExpandHistogram<T extends RealType<T>> implements Command {

	@Parameter
	private ImgPlus<T> image;

	@Parameter(type = ItemIO.OUTPUT)
	private ImgPlus<UnsignedByteType> result;

	@Override
	public void run() {
		double minIntensity = Double.MAX_VALUE;
		double maxIntensity = 0d;
		Cursor<T> cursorImg = image.cursor();
		while (cursorImg.hasNext()) {
			cursorImg.fwd();
			T pixel = cursorImg.get();
			minIntensity = Math.min(minIntensity, pixel.getRealDouble());
			maxIntensity = Math.max(maxIntensity, pixel.getRealDouble());
		}
		long[] dimensions = new long[image.numDimensions()];
		image.dimensions(dimensions);
		result = ImgPlus.wrap(ArrayImgs.unsignedBytes(dimensions));
		for (int c = 0; c < dimensions.length; c++) {
			result.setChannelMaximum(c, 255);
			result.setChannelMinimum(c, 0);
		}
		cursorImg.reset();
		Cursor<UnsignedByteType> cursorResult = result.cursor();
		while (cursorResult.hasNext()) {
			cursorImg.fwd();
			cursorResult.fwd();
			UnsignedByteType resultPixel = cursorResult.get();
			T imagePixel = cursorImg.get();

			resultPixel.setReal(computeNewIntensity(imagePixel, minIntensity, maxIntensity, resultPixel.getMinValue(),
					resultPixel.getMaxValue()));
		}
	}

	private double computeNewIntensity(T imagePixel, double minIntensity, double maxIntensity, double minLimitIntensity,
			double maxLimitIntensity) {
		double intensity = imagePixel.getRealDouble();
		double range = maxIntensity - minIntensity;
		double targetRange = maxLimitIntensity - minLimitIntensity;
		double newIntensity = ((intensity - minIntensity) * targetRange / range) + minLimitIntensity;
		return newIntensity;
	}

}
