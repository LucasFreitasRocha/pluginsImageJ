import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class desfragmentar_rgb_plugin implements PlugIn {
	
	
	
	public void run(String arg) {
	
	
		int pixel[] = new int[3];
		ImagePlus red = WindowManager.getImage("RED");
		ImageProcessor procRed = red.getProcessor();
		ImagePlus green = WindowManager.getImage("GREEN");
		ImageProcessor procGreen = green.getProcessor();
		ImagePlus blue = WindowManager.getImage("BLUE");
		ImageProcessor procBlue = blue.getProcessor();
		ImagePlus RGB = IJ.createImage("RGB", "RGB", procRed.getWidth(), procRed.getHeight(), 1);
		ImageProcessor procRgb = RGB.getProcessor();
		
		for(int i = 0; i < procRed.getWidth(); i++){
			for(int j= 0; j < procRed.getHeight(); j++){
				pixel[0] = procRed.getPixel(i, j);
				pixel[1] = procGreen.getPixel(i, j);
				pixel[2]= procBlue.getPixel(i,j);
				procRgb.putPixel(i, j, pixel);
				
			}
		}
		RGB.show();
	}
}






