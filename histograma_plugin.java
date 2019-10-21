import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
	 
public class histograma_plugin implements PlugIn {
	
	ImagePlus imagem = IJ.getImage();
	ImageProcessor processador = imagem.getProcessor();
	int pixel, old_maximo = 0, old_minimo = 256, new_maximo = 255, new_minimo = 0;
	ImagePlus  histograma = IJ.createImage("Histograma", "8-bit", processador.getWidth(), processador.getHeight(), 1);
	ImageProcessor procHistograma = histograma.getProcessor();
	
	
	
	private void get_maximo_minimo() {
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				if(this.processador.getPixel(i, j) > this.old_maximo) {
					this.old_maximo = this.processador.getPixel(i, j);
				}
				if(this.processador.getPixel(i, j) < this.old_minimo) {
					this.old_minimo = this.processador.getPixel(i, j);
				}
			}
		}
	}
	private int calcular() {
		int newpixel = (this.pixel - this.old_minimo) * (this.new_maximo / (this.old_maximo - this.old_minimo));
		return newpixel;
	}

	public void run(String arg) {
	
		get_maximo_minimo();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				this.pixel = processador.getPixel(i, j);
				procHistograma.putPixel(i, j, calcular());
				
			}
			
		}
		this.histograma.show();
		
		
	}




	



	
	
}






