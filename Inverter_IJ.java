import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class Inverter_IJ implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		for(int x = 0; x < processador.getWidth(); x++){
			for(int y = 0; y < processador.getHeight(); y++){
				processador.putPixel(x, y, 255 - processador.getPixel(x, y));
			}
		}
		imagem.updateAndDraw();
	}
}



