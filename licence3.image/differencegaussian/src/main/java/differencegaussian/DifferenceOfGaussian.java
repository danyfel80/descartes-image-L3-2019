package differencegaussian;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 7>Difference of gaussian")
public class DifferenceOfGaussian<T extends RealType<T>> implements Command {

	@Parameter
	OpService ops;

	@Parameter(type = ItemIO.INPUT)
	ImgPlus<T> inputImg;

	@Parameter(type = ItemIO.INPUT)
	double sigma1 = 2;

	@Parameter(type = ItemIO.INPUT)
	double sigma2 = 4;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> result;

	@Override
	public void run() {
		Img<DoubleType> floatImg = ops.convert().float64(inputImg);
		result = ImgPlus.wrap(ops.create().img(floatImg));
		result.setName(inputImg.getName() + "_DOG(" + sigma1 + "," + sigma2 + ")");
		ops.filter().dog(result, floatImg, sigma1, sigma2);
	}

}
