import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Interface_Max_Imagem implements PlugIn {

	ImagePlus image = IJ.getImage();
	ImageProcessor processor = image.getProcessor();
	
	ImagePlus secondImage = IJ.getImage();
	ImageProcessor secondProcessor = secondImage.getProcessor();

	@Override
	public void run(String arg) {

		int[] pixel = new int[2];
		WindowManager.putBehind();
		
		ImagePlus resultImage = IJ.createImage("Imagem Resultante", "8-bit", image.getWidth(), image.getHeight(), 1);
		ImageProcessor resultProcessor = resultImage.getProcessor();

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				resultProcessor.putPixel(x, y, validatePixel(pixel));
			}
		}
		
		resultImage.updateAndDraw();
		resultImage.show();
	}


	private int validatePixel(int[] pixel) {
		int maximum = 0;

		if (pixel[0] >= pixel[1]) {
			maximum = pixel[0];
		} else {
			maximum = pixel[1];
		}

		return maximum;
	}
}