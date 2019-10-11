import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class maximo_minimo_plugin implements PlugIn {
	int array1[][];
	int array2[][];
	int arraysoma[][];
	
	public int maximo(int pixel1, int pixel2) {
		if(pixel1 > pixel2) {
			return pixel1;
		}else {
			return pixel2;
		}
	}
	public int minimo(int pixel1, int pixel2) {
		if(pixel1 < pixel2) {
			return pixel1;
		}else {
			return pixel2;
		}
	}
	
	public void run(String arg) {
		
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador1 = imagem.getProcessor();
		WindowManager.putBehind();
		ImagePlus imagem2 = IJ.getImage();
		ImageProcessor processador2 = imagem2.getProcessor();	
		ImagePlus maximo = IJ.createImage("maximo", "8-bits", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procMaximo = maximo.getProcessor();
		ImagePlus minimo = IJ.createImage("minimo", "8-bits", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procMinimo = minimo.getProcessor();
		
		
		for(int i = 0; i < processador1.getWidth(); i++){
			for(int j= 0; j < processador1.getHeight(); j++){
				int pixelImage1 =   processador1.getPixel(i, j );
				int pixelImage2 = 	processador2.getPixel(i, j );	
				procMaximo.putPixel(i,j, maximo(pixelImage1,pixelImage2));
				procMinimo.putPixel(i,j, minimo(pixelImage1,pixelImage2));
				
			}
		}
		maximo.show();
		minimo.show();
	}
}
