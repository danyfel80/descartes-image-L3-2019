/**
 * 
 */
package histogram;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.Cursor;
import net.imglib2.Point;
import net.imglib2.algorithm.region.BresenhamLine;
import net.imglib2.histogram.Histogram1d;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.LongType;

/**
 * This plugin computes the histogram of an image.
 */
@Plugin(type = Command.class, name = "computeHistogram", menuPath = "Plugins>TD 2>Compute Histogram")
public class ComputeHistogram<T extends RealType<T>> implements Command {

	@Parameter
	private OpService ops;

	@Parameter
	private ImgPlus<T> image;

	@Parameter(required = false)
	private int histogramBins = 256;

	@Parameter(required = false)
	private int histogramHeight = 256;

	@Parameter(type = ItemIO.OUTPUT)
	private ImgPlus<IntType> histogramImage;

	@Override
	public void run() {
		histogramImage = ImgPlus.wrap(ArrayImgs.ints(histogramBins, histogramHeight));

		Histogram1d<T> histogram = ops.image().histogram(image, histogramBins);
		long maxBinValue = getMaxBinValue(histogram);
		// Completez l'image histogramImage
	}

	private long getMaxBinValue(Histogram1d<T> histogram) {
		long maxValue = 0;
		Cursor<LongType> cursor = histogram.cursor();
		while (cursor.hasNext()) {
			cursor.fwd();
			maxValue = Math.max(cursor.get().get(), maxValue);
		}
		return maxValue;
	}

	private <V extends RealType<V>> void drawLine(ImgPlus<V> image, Point p1, Point p2, V value) {
		BresenhamLine<V> cursorHisto = new BresenhamLine<V>(image);
		cursorHisto.reset(p1, p2);
		while (cursorHisto.hasNext()) {
			cursorHisto.fwd();
			cursorHisto.get().set(value);
		}
	}

}
