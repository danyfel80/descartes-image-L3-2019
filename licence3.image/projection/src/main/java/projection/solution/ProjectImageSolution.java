package projection.solution;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.IntType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 4 Solution>Projection (Solved)")
public class ProjectImageSolution<T extends RealType<T>> implements Command {

	@Parameter(type = ItemIO.INPUT, required = true)
	ImgPlus<T> inputImage;

	@Parameter(type = ItemIO.INPUT, required = true)
	boolean horizontal; // true = horizontal projection,
						// false = vertical projection

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<IntType> projection;

	@Override
	public void run() {

		long[] dims = new long[inputImage.numDimensions()];
		inputImage.dimensions(dims);

		// La taille du resultat est <largeur, 10> (projection horizontale)
		// ou <10, hauteur> (projection verticale)
		long[] projDims = new long[] { horizontal ? dims[0] : 10, horizontal ? 10 : dims[1] };
		projection = ImgPlus.wrap(ArrayImgs.ints(projDims));

		RandomAccess<T> inputImageCursor = inputImage.randomAccess();
		RandomAccess<IntType> projectionCursor = projection.randomAccess();

		long[] posInputImage = new long[inputImage.numDimensions()];
		long[] posProjection = new long[projection.numDimensions()];

		// Completez ce code:
		// 1. Pour Chaque ligne/colonne de l'image d'entree
		// 2. On somme les intensités de toutes les intensités de la colonne/ligne
		// 3. On affecte la somme au pixel(s) de l'image resultat "projection"

		for (int i = 0; i < inputImage.dimension(horizontal ? 0 : 1); i++) {
			posInputImage[horizontal ? 0 : 1] = i;
			posProjection[horizontal ? 0 : 1] = i;
			double sum = 0;
			for (int j = 0; j < inputImage.dimension(horizontal ? 1 : 0); j++) {
				posInputImage[horizontal ? 1 : 0] = j;
				inputImageCursor.setPosition(posInputImage);
				sum += inputImageCursor.get().getRealDouble();
			}

			for (int j = 0; j < projDims[horizontal ? 1 : 0]; j++) {
				posProjection[horizontal ? 1 : 0] = j;
				projectionCursor.setPosition(posProjection);
				projectionCursor.get().set((int) Math.round(sum));
			}
		}

	}

}
