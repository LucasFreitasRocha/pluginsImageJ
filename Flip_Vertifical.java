import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class Flip_Vertifical implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		for(int y = 0; y < processador.getHeight() / 2; y++){
			for(int x = 0; x < processador.getWidth(); x++){
				int aux = processador.getPixel(x, y);
				processador.putPixel(x, y, processador.getPixel(x, processador.getHeight() - y));
				processador.putPixel(x,  processador.getHeight() - y, aux);
			}
		}
		imagem.updateAndDraw();
	}
}



