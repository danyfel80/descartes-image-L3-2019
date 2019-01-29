package launchImageJ;

import net.imagej.ImageJ;

public class LaunchImageJ {

	public static void main(String[] args) {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();
	}

}
