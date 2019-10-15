import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class soma_colorido_plugin implements PlugIn {
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
	public int soma(int pixel1, int pixel2) {
		return validar(pixel1+pixel2);
	}
	
	public void run(String arg) {
		
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador1 = imagem.getProcessor();
		WindowManager.putBehind();
		ImagePlus imagem2 = IJ.getImage();
		ImageProcessor processador2 = imagem2.getProcessor();	
		ImagePlus soma = IJ.createImage("SOMA", "RGB", processador1.getWidth(), processador1.getHeight(), 1);
		ImageProcessor procSoma = soma.getProcessor();
		int total[] = new int[3];
		int pixelImage1[] = new int[3];
		int pixelImage2[] = new int[3];
		
		for(int i = 0; i < processador1.getWidth(); i++){
			for(int j= 0; j < processador1.getHeight(); j++){
				 pixelImage1 =   processador1.getPixel(i, j, pixelImage1 );
				 pixelImage2 = 	processador2.getPixel(i, j, pixelImage2 );
				for(int rgb = 0; rgb<3;rgb++) {
					total[rgb] =  soma(pixelImage1[rgb], pixelImage2[rgb] );
				}
				procSoma.putPixel(i, j, total);
			}
		}
		soma.show();
	}
}
