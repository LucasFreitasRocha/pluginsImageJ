import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Interface_Soma_imagens  implements  PlugIn, DialogListener {
	
	ImagePlus image = IJ.getImage();
	ImageProcessor processor = image.getProcessor();
	ImagePlus secondImage = IJ.getImage();
	ImageProcessor secondProcessor = secondImage.getProcessor();
	ImagePlus resultImage = IJ.createImage("Imagem Resultante", "8-bit", image.getWidth(), image.getHeight(), 1);
	ImageProcessor resultProcessor = resultImage.getProcessor();
	int[] pixel = new int[2];
	float lowest = 256;
	float highest = -1;

	public void run(String arg) {
		apresentarInterfaceGrafica();
		
	}
	
	public void apresentarInterfaceGrafica() {
	
		
		GenericDialog interfaceGraphic = new GenericDialog("Soma de Imagens");
		interfaceGraphic.addDialogListener(this);
		String[] strategy = { "1 - Truncamento", "2 - Normalização", "3 - Wrapping" };
		interfaceGraphic.addRadioButtonGroup("Tipos de Tratamento", strategy, 3, 1, strategy[0]);
		interfaceGraphic.showDialog();
		WindowManager.putBehind();
		
	
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				String option = interfaceGraphic.getNextRadioButton();
				if(option.equals("Truncamento")) {
					IJ.log("Truncamento");
					trucamento();
				}
				if(option.equals("Normalização")) {
					IJ.log("Normalização");
					normalizacao();
				}
				if(option.equals("Wrapping")) {
					IJ.log("Wrapping");
					wrapping();
				}
				resultImage.updateAndDraw();
				resultImage.show();
			
		        
		      
		        
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}



	
	private void wrapping() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateWrapping(sum));
			}
		}
		
	}

	private int validateWrapping(int sum) {
		if( sum > 255) {
			sum =  sum - 255;
		}
		return sum;
	}

	private void normalizacao() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateNormalizacao(sum));
			}
		}
		
	}
	private float findHighestPixel() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				if (sum > highest) {
					highest = sum;
				}
			}
		}
		return highest;
	}

	private float findLowestPixel() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				if (sum < lowest) {
					lowest = sum;
				}
			}
		}
		return lowest;
	}

	private int validateNormalizacao(int sum) {
		// TODO Auto-generated method stub
		return  (int) ( (255 / (findHighestPixel() -findLowestPixel())) * (sum - findLowestPixel()) );
	}

	private void trucamento() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateTrucamento(sum));
			}
		}
		
	}

	private int validateTrucamento(int sum) {
		// TODO Auto-generated method stub
		if(sum > 255) {
			return 255;
		}
		if(sum < 0) {
			return 0;
		}
		return sum;
	}

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		
		if (interfaceGrafica.wasCanceled()) return false;

        IJ.log("\n");
        return true;
    }
	


	


}
