import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class maximo_minimo_colorido_plugin implements PlugIn {
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
		ImagePlus maximo = IJ.createImage("maximo", "RGB", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procMaximo = maximo.getProcessor();
		ImagePlus minimo = IJ.createImage("minimo", "RGB", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procMinimo = minimo.getProcessor();
		int totalMaximo[] = new int[3];
		int totalMinimo[] = new int[3];
		int pixelImage1[] = new int[3];
		int pixelImage2[] = new int[3];
		
		for(int i = 0; i < processador1.getWidth(); i++){
			for(int j= 0; j < processador1.getHeight(); j++){
				pixelImage1 =   processador1.getPixel(i, j , pixelImage1);
				pixelImage2 = 	processador2.getPixel(i, j , pixelImage2);	
				for(int rgb = 0; rgb<3;rgb++) {
					totalMaximo[rgb] =  maximo(pixelImage1[rgb], pixelImage2[rgb] );
					totalMinimo[rgb] = minimo(pixelImage1[rgb], pixelImage2[rgb] );
				}
				procMaximo.putPixel(i,j,totalMaximo);
				procMinimo.putPixel(i,j, totalMinimo);
				
				
				
			}
		}
		maximo.show();
		minimo.show();
	}
}
