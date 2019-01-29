import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * @author Daniel Felipe Gonzalez Obando
 *
 */
@Plugin(type = Command.class, name = "createGradientImage", menuPath = "Plugins>TD 1>Create Gradient Image")
public class Gradient<T extends RealType<T>> implements Command {

	@Parameter(required = false)
	private int size = 256;

	@Parameter(type = ItemIO.OUTPUT)
	private Img<DoubleType> gradientImg;

	@Override
	public void run() {
		gradientImg = ArrayImgs.doubles(size, size);

		final Cursor<DoubleType> c = gradientImg.localizingCursor();
		final long[] pos = new long[gradientImg.numDimensions()];

		while (c.hasNext()) {
			c.fwd();
			c.localize(pos);
			c.get().setReal(sum(pos));
		}
	}

	private double sum(long[] pos) {
		float sum = 0;
		for (long p : pos)
			sum += p;
		return sum;
	}

}
