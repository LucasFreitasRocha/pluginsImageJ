import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class RGB_TO_HSV implements PlugIn {

	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		
		ImagePlus ImagemH = IJ.createImage("hue", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorH = ImagemH.getProcessor();
		
		ImagePlus ImagemS = IJ.createImage("saturation", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorS = ImagemS.getProcessor();
		
		ImagePlus ImagemV = IJ.createImage("value", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorV = ImagemV.getProcessor();

		

		for(int x = 0 ; x < processador.getWidth() ; x++){
			for(int y = 0 ; y < processador.getHeight() ; y++){
				processadorH.putPixel(x, y, hue(processador.getPixel(x, y, null)));
				processadorS.putPixel(x, y, saturation(processador.getPixel(x, y, null)));
				processadorV.putPixel(x, y, value(processador.getPixel(x, y, null)));
			}
		}
		
		ImagemH.show();
		ImagemS.show();
		ImagemV.show();
		
	}
	
	public int hue(int[] pixel) {
		
		float result = 0;
		float auxPixel[] = new float[3];
		
		for(int i = 0 ; i < 3 ; i++) {
			auxPixel[i] = normalization(pixel[i], 0, 255, 1.0F, 0.0F);
		}
		
		float maior = auxPixel[0], menor = auxPixel[0];
		
		for(int i = 0 ; i < 3 ; i++) {
			if( maior < auxPixel[i]) {
				maior = auxPixel[i];
			}
			if(menor > auxPixel[i]) {
				menor = auxPixel[i];
			}
		}
				
		
		if( maior == auxPixel[0] && auxPixel[1] >= auxPixel[2]) {
			result = 60 * ((auxPixel[1] - auxPixel[2])/(maior - menor)) + 0;
	
		} else if (maior == auxPixel[0] && auxPixel[1] < auxPixel[2]) {
			result = 60 * ((auxPixel[1] - auxPixel[2])/(maior - menor)) + 360;
		
		} else if (maior == auxPixel[1]) {
			result = 60 * ((auxPixel[2] - auxPixel[0])/(maior - menor)) + 120;
			
		} else if (maior == auxPixel[2]) {
			result = 60 * ((auxPixel[0] - auxPixel[1])/(maior - menor)) + 240;

		}
		
		
		int result2 = (int)  normalization(result, 0, 360, 255, 0);
		return result2;
	}
	
	
	public int saturation(int[] pixel) {
		float maior = 0, menor = 255;
		float auxPixel[] = new float[3];

		
		for(int i = 0 ; i < 3 ; i++) {
			auxPixel[i] = normalization(pixel[i], 0, 255, 1.0F, 0.0F);
		}
		
		for(int i = 0 ; i < 3 ; i++) {
			if( auxPixel[i] > maior) {
				maior = auxPixel[i];
			}
			if(auxPixel[i] < menor) {
				menor = auxPixel[i];
			}
		}
		
		float result = (float) ((maior - menor)/maior); 
		result = normalization(result, 0.0F, 1.0F, 255, 0);
		return (int) result;
	}
	
	public int value(int[] pixel) {
		
		
		int maior = 0;
		for(int i = 0 ; i < 3 ; i++) {
			if( pixel[i] > maior) {
				maior = pixel[i];
			}
		}
		return maior;
	}
	
	public float normalization(float result, float min, float max, float novoMaximo, float novoMinimo) {
		float ret = (result - min) * ( (novoMaximo - novoMinimo)/(max - min) ) + novoMinimo;
		return ret;	
	}
}
