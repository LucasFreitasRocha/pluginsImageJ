import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class contraste_Plugin implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel;
		int c = 159;
		int f  = (259 * (c + 255))/(255*(259-c));
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				 pixel =  processador.getPixel(i, j);
				 pixel = (f * (pixel - 128)) + 128; 
				 if(pixel < 0){
					 processador.putPixel(i, j, 0);
				 }
				 if(pixel > 255){
					 processador.putPixel(i, j, 255);
				 }
				 if(pixel > 0 && pixel < 255){
					 processador.putPixel(i,j, (int) pixel);
				 }
			}
		}
		imagem.updateAndDraw();
	}
}



