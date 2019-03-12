package convolution;

import java.util.Arrays;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.ImgView;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 6>Convolve (Not normalized)")
public class Convolve2<T extends RealType<T>> implements Command {

	@Parameter
	OpService ops;

	@Parameter(label = "Kernel", persist = false)
	String kernelString = "0 1 0;1 -4 1;0 1 0";

	@Parameter
	ImgPlus<T> inputImage;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> outImage;

	@Override
	public void run() {

		// Split kernel string values and convert them to double values
		double[][] kValues = Arrays.stream(kernelString.split(";"))
				.map(s -> Arrays.stream(s.split(" +")).mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);

		System.out.println(Arrays.deepToString(kValues));

		// Create kernel random accessible interval
		RandomAccessibleInterval<DoubleType> kernel = ops.create().kernel(kValues, new DoubleType(0d));
		// To apply gaussian use: kernel = ops.create().kernelGauss(sigma);

		// Apply filter
		@SuppressWarnings({ "unchecked" })
		RandomAccessibleInterval<DoubleType> result = ops.convert()
				.float64((IterableInterval<DoubleType>) ops.filter().convolve(inputImage, kernel));

		// Save result as output
		outImage = new ImgPlus<DoubleType>(ImgView.wrap(result, null), "img");
	}

}