package convolution;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.Dataset;
import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.ImgView;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 5>Gaussian")
public class Gaussian implements Command {

	@Parameter
	OpService ops;

	@Parameter(persist = false)
	double sigma = 1d;

	@Parameter
	Dataset img;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> outImgP;

	@Override
	public void run() {
		// Take img dataset as random accessible interval
		@SuppressWarnings("rawtypes")
		RandomAccessibleInterval image = img;

		// Create gauss kernel
		@SuppressWarnings("unchecked")
		RandomAccessibleInterval<DoubleType> kernel = ops.convert()
				.float64((IterableInterval<DoubleType>) ops.create().kernelGauss(sigma, image.numDimensions()));

		// Apply filter
		@SuppressWarnings({ "unchecked" })
		RandomAccessibleInterval<DoubleType> result = ops.filter().convolve(image, kernel);

		// Save result as output
		outImgP = new ImgPlus<DoubleType>(ImgView.wrap(result, null), "Gauss_" + sigma + img.getName());
	}

}
