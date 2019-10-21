import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class fragmentar_rgb_plugin implements PlugIn {
	
	public int validar(int aux) {
		if(aux >255) {
			return 255;
		}
		if(aux < 0) {
			return 0;
		}
		return  aux;
	}
	public int avg(int[] pixel) {
		int aux = (pixel[0] + pixel[1] + pixel[2])/3; 
		return validar(aux);
	}
	
	public int lum1(int[] pixel) {
		int aux = (int) (( 0.299 * pixel[0] ) + ( 0.587 * pixel[1] ) + ( 0.114 * pixel[2] )) ;
		return validar(aux);
	}
	public int lum2(int[] pixel) {
		int aux = (int) ( (pixel[0] * 0.2125) + (pixel[1] * 0.7154) + (pixel[2] * 0.072)) ;
		return validar(aux);
	}
	
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
	
		
		ImagePlus read = IJ.createImage("RED", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procRed = read.getProcessor();
		ImagePlus green = IJ.createImage("GREEN", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procGreen = green.getProcessor();
		ImagePlus blue = IJ.createImage("BLUE", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procBlue = blue.getProcessor();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				procRed.putPixel(i, j, pixel[0]);
				procGreen.putPixel(i, j, pixel[1]);
				procBlue.putPixel(i, j, pixel[2]);
			}
		}
		read.show();
		green.show();
		blue.show();
	}
}






