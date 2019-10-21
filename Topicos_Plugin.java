

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

	 
public class Topicos_Plugin implements PlugIn {
	public void run(String arg) {
		ImagePlus read = WindowManager.getImage("READ");
		read.show();
					
	}
}



