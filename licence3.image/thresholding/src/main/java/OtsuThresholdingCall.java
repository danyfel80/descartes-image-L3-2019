import java.util.concurrent.ExecutionException;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.histogram.Histogram1d;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 3>Otsu thresholding")
public class OtsuThresholdingCall<T extends RealType<T>> implements Command {

	@Parameter
	OpService ops;

	@Parameter
	CommandService commands;

	@Parameter(persist = false)
	ImgPlus<T> inputImage;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<UnsignedByteType> result;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Histogram1d<T> histogram = ops.image().histogram(inputImage);
		T threshold = ops.threshold().otsu(histogram);
		System.out.println("Threshold=" + threshold.getRealDouble());
		try {
			result = (ImgPlus<UnsignedByteType>) commands.run(ThresholdImage.class, false, "inputImage", inputImage,
					"threshold", (long) threshold.getRealDouble()).get().getOutput("mask");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
