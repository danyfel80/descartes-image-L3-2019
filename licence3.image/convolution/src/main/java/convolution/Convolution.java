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

@Plugin(type = Command.class, name = "Convolution", menuPath = "Plugins>TD 5>Convolution")
public class Convolution<T extends RealType<T>> implements Command {

	@Parameter
	OpService ops;

	@Parameter(label = "Kernel", persist = false)
	String kernelString = 
	  "1 4 7 4 1;"
	+ "4 16 26 16 4;"
	+ "7 26 41 26 7;"
	+ "4 16 26 16 4;"
	+ "1 4 7 4 1";

	@Parameter
	ImgPlus<T> img;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> outImgP;

	@Override
	public void run() {
		// 1. Create Kernel
		// Split kernel string values and convert them to double values
		double[][] kValues = Arrays.stream(kernelString.split(";"))
				.map(s -> 
					Arrays.stream(s.split(" +")).mapToDouble(Double::parseDouble).toArray())
				.toArray(double[][]::new);
		// Normalize values
		double sum = Arrays.stream(kValues).mapToDouble(l -> Arrays.stream(l).sum()).sum();
		kValues = Arrays.stream(kValues).map(l -> Arrays.stream(l).map(v -> v / sum).toArray())
				.toArray(double[][]::new);
		System.out.println(Arrays.deepToString(kValues));

		// Create kernel random accessible interval
		RandomAccessibleInterval<DoubleType> kernel = ops.create().kernel(kValues, new DoubleType(0d));
		// To apply gaussian use: kernel = ops.create().kernelGauss(sigma);

		// 2. Apply filter
		@SuppressWarnings({ "unchecked" })
		RandomAccessibleInterval<DoubleType> result = ops.convert()
				.float64((IterableInterval<DoubleType>) ops.filter().convolve(img, kernel));

		// 3. Save result as output
		outImgP = new ImgPlus<DoubleType>(ImgView.wrap(result, null), "Conv_" + img.getName());
	}

}
