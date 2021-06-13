import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;
	 
public class Fragmentar_Rgb_Plugin implements PlugIn {
	

	public byte[] generateLutComValores() {
		byte[] aux = new byte[256];
		for (int i = 0; i < 256; i++) {
			aux[i] = (byte) i;
		}
		return aux;
	}
	public byte[] generateLutComZero() {
		byte[] aux = new byte[256];
		for (int i = 0; i < 256; i++) {
			aux[i] = 0;
		}
		return aux;
	}
	
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		byte[] lut = generateLutComValores();
		byte[] lutZero = generateLutComZero();
		
		ImagePlus red = IJ.createImage("RED", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procRed = red.getProcessor();
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
		
		LUT lutRed = new LUT(lut, lutZero, lutZero);
		LUT lutGreen = new LUT(lutZero, lut, lutZero);
		LUT lutBlue = new LUT(lutZero, lutZero, lut);
		
		red.setLut(lutRed);
		green.setLut(lutGreen);
		blue.setLut(lutBlue);
		
		red.show();
		green.show();
		blue.show();
		
		
		
		
	}
}
