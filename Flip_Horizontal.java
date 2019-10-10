import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class Flip_Horizontal implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		//ImagePlus nova = IJ.createImage("nova", processador., max_width, max_height, 1);
		for(int x = 0; x < processador.getWidth() / 2; x++){
			for(int y = 0; y < processador.getHeight(); y++){
				int aux = processador.getPixel(x, y);
				processador.putPixel(x, y, processador.getPixel(processador.getWidth() - x, y));
				processador.putPixel(processador.getWidth() - x, y, aux);
			}
		}
		imagem.updateAndDraw();
	}
}



