package morphology;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.img.ImgView;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;

@Plugin(type = Command.class, name = "Skeletonize", menuPath = "Plugins>TD 8>Skeleton")
public class Skeletonize<T extends RealType<T>> implements Command {

	@Parameter
	OpService ops;
	
	@Parameter(type = ItemIO.INPUT)
	ImgPlus<T> inputImage;
	
	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<RealType<?>> skeleton;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Img<BitType> bytes = ops.convert().bit(inputImage);
		skeleton = new ImgPlus<RealType<?>>(ImgView.wrap(ops.morphology().thinMorphological(bytes), null));
	}

}
