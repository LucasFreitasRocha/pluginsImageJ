import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class clarear_Plugin_colorido implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
	
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				 pixel =   processador.getPixel(i, j, pixel );
				 for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					 if((pixel[rgb] * 1.5) > 255){
						 pixel[rgb] = 255;
						 
					 }else{
						 pixel[rgb] =  (int) (pixel[rgb] * 1.5);
						 
					 } 
				 }
				 processador.putPixel(i, j, pixel);
				 
			}
		}
		imagem.updateAndDraw();
	}
}



