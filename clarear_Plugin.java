import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class clarear_Plugin implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		float pixel;
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				 pixel = (float) processador.getPixel(i, j);
				 if((pixel * 1.5) > 255){
					 processador.putPixel(i, j, 255);
				 }else{
					 pixel =  (float) (pixel * 1.5);
					 processador.putPixel(i, j, (int) pixel );
				 }
			}
		}
		imagem.updateAndDraw();
	}
}



