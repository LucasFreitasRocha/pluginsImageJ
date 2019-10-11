import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class subtracao_plugin implements PlugIn {
	int array1[][];
	int array2[][];
	int arraysoma[][];
	public int validar(int aux) {
		if(aux >255) {
			return 255;
		}
		if(aux < 0) {
			return 0;
		}
		return  aux;
	}
	public int subtrair(int pixel1, int pixel2) {
		return validar(pixel1-pixel2);
	}
	
	public void run(String arg) {
		
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador1 = imagem.getProcessor();
		WindowManager.putBehind();
		ImagePlus imagem2 = IJ.getImage();
		ImageProcessor processador2 = imagem2.getProcessor();	
		ImagePlus subtracao = IJ.createImage("SUBTRACAO", "8-bits", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procSubtracao = subtracao.getProcessor();
		
		
		for(int i = 0; i < processador1.getWidth(); i++){
			for(int j= 0; j < processador1.getHeight(); j++){
				int pixelImage1 =   processador1.getPixel(i, j );
				int pixelImage2 = 	processador2.getPixel(i, j );		
				procSubtracao.putPixel(i, j, subtrair(pixelImage1, pixelImage2));
			}
		}
		subtracao.show();
	}
}