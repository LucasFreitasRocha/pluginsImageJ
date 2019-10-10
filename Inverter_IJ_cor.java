import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class Inverter_IJ_cor implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int cor[];
		for(int x = 0; x < processador.getWidth(); x++){
			for(int y = 0; y < processador.getHeight(); y++){
				 int[] iArray = null;
				cor = processador.getPixel(x, y, iArray);
				int nova_cor[] = new int[3];
				nova_cor[0] = 255 - cor[0];
				nova_cor[1] = 255 - cor[1];
				nova_cor[2] = 255 - cor[2];
				
				processador.putPixel(x, y, nova_cor);
			}
		}
		imagem.updateAndDraw();
	}
}



