import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class contraste_Plugin_colorido implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		int c = 159;
		int f  = (259 * (c + 255))/(255*(259-c));
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					 pixel[rgb] = (f * (pixel[rgb] - 128)) + 128; 
					 if(pixel[rgb] < 0){
						 pixel[rgb] = 0;
					 }
					 if(pixel[rgb] > 255){
						 pixel[rgb] = 255;
					 }
					 if(pixel[rgb] > 0 && pixel[rgb] < 255){
						 pixel[rgb] = pixel[rgb];
					 }
				 }	 
				processador.putPixel(i, j, pixel);
			}
		}
		imagem.updateAndDraw();
	}
}



