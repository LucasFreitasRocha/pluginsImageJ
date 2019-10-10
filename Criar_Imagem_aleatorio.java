import java.util.Random;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

	 
public class Criar_Imagem_aleatorio implements PlugIn {
	public void run(String arg) {
		Random random = new Random();
		int max_width = 1000;
		int max_height = 1000;
		ImagePlus nova = IJ.createImage("nova", "8-bit", max_width, max_height, 1);
		ImageProcessor proc = nova.getProcessor();
		
		int largura = 0;
		
		while(largura < proc.getWidth()){
			int tam_barra = random.nextInt(100);
			int tom = random.nextInt(255);
			
			for(int x = largura; x < tam_barra + largura; x++){
				for(int y = 0; y < proc.getHeight(); y++){
					proc.putPixel(x, y, tom);
				}
			}
			largura = largura + tam_barra;

		}
		
		nova.show();
	}
}



