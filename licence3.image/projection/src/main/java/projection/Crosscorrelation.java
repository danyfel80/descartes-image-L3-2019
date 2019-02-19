package projection;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = Command.class, menuPath = "Plugins>TD 4 >Correlate images")
public class Crosscorrelation<T extends RealType<T>> implements Command {
	
	@Parameter(type = ItemIO.INPUT, required = true, persist = false)
	ImgPlus<T> inputImage;

	@Parameter(type = ItemIO.INPUT, required = true, persist = false)
	ImgPlus<T> template;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> correlation;

	@Override
	public void run() {
		correlation = computeCrossCorrelation(inputImage, template);
	}

	private ImgPlus<DoubleType> computeCrossCorrelation(ImgPlus<T> image, ImgPlus<T> template) {

		RandomAccess<T> imageCursor = image.randomAccess();
		RandomAccess<T> templateCursor = template.randomAccess();

		long[] imageDimensions = new long[image.numDimensions()];
		long[] templateDimensions = new long[template.numDimensions()];
		image.dimensions(imageDimensions);
		template.dimensions(templateDimensions);

		ImgPlus<DoubleType> correlation = ImgPlus.wrap(ArrayImgs.doubles(imageDimensions));
		RandomAccess<DoubleType> correlationCursor = correlation.randomAccess();

		// variables globales - utilisees dans la fonction correlate
		currentImagePosition = new long[imageCursor.numDimensions()];
		positionTemplate = new long[templateCursor.numDimensions()];
		positionImage = new long[imageCursor.numDimensions()];
		midPositionTemplate = new long[] { templateDimensions[0] / 2, templateDimensions[1] / 2 };
		
		long[] currentPosition = new long[2];
		for (int i = 0; i < imageDimensions[0]; i++) {
			currentPosition[0] = i;
			for (int j = 0; j < imageDimensions[1]; j++) {
				currentPosition[1] = j;
				imageCursor.setPosition(currentPosition);
				correlationCursor.setPosition(currentPosition);

				double correlationAtPosition = correlate(imageCursor, imageDimensions, templateCursor,
						templateDimensions);
				correlationCursor.get().set(correlationAtPosition);
			}
		}

		return correlation;
	}

	long[] currentImagePosition;
	long[] positionTemplate;
	long[] positionImage;
	long[] midPositionTemplate;
	
	public double correlate(RandomAccess<T> imageCursor, long[] imageDimensions, RandomAccess<T> templateCursor,
			long[] templateDimensions) {
		// Recuperer la position d'imageCursor
		imageCursor.localize(currentImagePosition);

		// Completez le code
		double sum = 0; // somme des multiplications
		double div = templateDimensions[0] * templateDimensions[1]; // Pour normaliser la somme

		// 1. pour chaque colonne du template

		// 2. pour chaque ligne du template

		// 3. on multiplie l'intensite de l'image avec l'intensité du template a la
		// position de l'image donnee par i et j. Cette valeur doit etre normalisee
		// par la taille de l'image (quantite de pixels). Puis on ajoute cette
		// multiplication a la somme.

		return sum;
	}

}
