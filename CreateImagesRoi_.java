import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;

public class CreateImagesRoi_ implements PlugIn {

	public void run(String arg) {
		ImagePlus originalImage = IJ.getImage();
		ImagePlus cloneImage = originalImage.duplicate();

		IJ.run("Make Binary");
		IJ.run("Fill Holes");
		IJ.run("Analyze Particles...", "add");

		RoiManager roiManager = RoiManager.getRoiManager();
		Roi[] rois = roiManager.getRoisAsArray();

		int aux = 0;
		
		String path = IJ.getDirectory("");

		for (Roi roi : rois) {
			aux++;
			cloneImage.setRoi(roi.getBounds());
			ImagePlus spriteImage = cloneImage.crop();
			spriteImage.setTitle("Sprite " + aux);
			IJ.save(spriteImage, path + aux + ".png");
		}
		
	}

}
