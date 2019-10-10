import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class colorida_para_cinza_plugin implements PlugIn {
	
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
		
		ImagePlus avg = IJ.createImage("avg", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procAvg = avg.getProcessor();
		ImagePlus lum0 = IJ.createImage("lum0", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procLum0 = lum0.getProcessor();
		ImagePlus lum1 = IJ.createImage("lum1", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procLum1 = lum1.getProcessor();
		
		
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				
				procAvg.putPixel(i, j, avg(pixel));
				
				procLum0.putPixel(i, j,lum1(pixel));
				
				procLum1.putPixel(i, j, lum2(pixel));
			}
		}
		avg.show();
		lum0.show();
		lum1.show();
	}
}






