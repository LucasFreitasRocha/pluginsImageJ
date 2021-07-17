import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;


public class Interface_Filtros_Lineares  implements PlugIn, DialogListener {
	
	ImagePlus originalImage = IJ.getImage();
	ImageProcessor originalProcessor = originalImage.getProcessor();	
	
	ImagePlus imageLinear = IJ.createImage("Imagem Resultante", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(), 1);
	ImageProcessor processorLinear = imageLinear.getProcessor();
	
	public void run(String arg) {
		showInterface();
	}

	private void showInterface() {
		GenericDialog interfaceGraphic = new GenericDialog("Filtros Lineares");
		interfaceGraphic.addDialogListener(this);
		String[] strategy = {"Filtro Passa Baixa - Média", "Filtro Passa Alta","Filtro de Borda"}; 
		interfaceGraphic.addRadioButtonGroup("Botões para escolher uma dentre várias estratégias", strategy, 1, 3, "Estratégia 2");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(strategy[0])) {
					passaBaixaStrategy(processorLinear);
				} else if (option.equalsIgnoreCase(strategy[1])) {
					passaAltaStrategy(processorLinear);
				} else {
					bordaStrategy(processorLinear);
				}
				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void passaBaixaStrategy(ImageProcessor processorLinear) {
		int newPixel;
		
		for (int x = 1; x< originalProcessor.getWidth() - 1; x++) {
            for (int y = 1; y < originalProcessor.getHeight() - 1; y++) {
            	newPixel = 0;
            	
            	for(int k = x-1; k <= x+1; k++) {
					for(int l = y-1; l <= y+1; l++) {
						newPixel += originalProcessor.getPixel(k, l);
					}
				}
            	
         	   processorLinear.putPixel(x, y, (newPixel / 9));
              
            }
        }
        imageLinear.show();
	}
	
	private void passaAltaStrategy(ImageProcessor processor) {
		int newPixel = 0;
		int[] vector = new int[9];
		
		
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
            for (int y = 0; y < originalProcessor.getHeight(); y++) {
            	newPixel = 0;
            	
            	vector[0] = originalProcessor.getPixel(x-1,y+1);
	         	vector[1] = originalProcessor.getPixel(x+1,y+1);
	         	vector[2] = originalProcessor.getPixel(x, y+1);
	         	vector[3] = originalProcessor.getPixel(x-1, y-1);
	         	vector[4] = originalProcessor.getPixel(x+1,y-1);
	         	vector[5] = originalProcessor.getPixel(x-1, y);
	         	vector[6] = originalProcessor.getPixel(x+1, y);
	         	vector[7] = originalProcessor.getPixel(x, y-1);
	         	vector[8] = originalProcessor.getPixel(x, y);
	         	
	         	for (int i = 0; i < 9; i++) {
	         		if (i == 0) {
	         			newPixel = newPixel + (vector[i] * -1);
	         		} else if (i > 0 && i < 8) {
	         			newPixel = newPixel + (vector[i] * -1);
	         		} else {
	         			newPixel = newPixel + (vector[i] * 9);
	         		}
	         	}
            	
         	   	processorLinear.putPixel(x, y, newPixel);
              
            }
        }
        imageLinear.show();
	}
	
	private void bordaStrategy(ImageProcessor processor) {
		int newPixel;
		int[] vector = new int[9];
      
        for (int x = 0; x< originalProcessor.getWidth(); x++) {
            for (int y = 0; y < originalProcessor.getHeight(); y++) {
            	newPixel = 0;
	         	vector[0] = originalProcessor.getPixel(x-1,y+1);
	         	vector[1] = originalProcessor.getPixel(x+1,y+1);
	         	vector[2] = originalProcessor.getPixel(x, y+1);
	         	vector[3] = originalProcessor.getPixel(x-1, y-1);
	         	vector[4] = originalProcessor.getPixel(x+1,y-1);
	         	vector[5] = originalProcessor.getPixel(x-1, y);
	         	vector[6] = originalProcessor.getPixel(x+1, y);
	         	vector[7] = originalProcessor.getPixel(x, y-1);
	         	vector[8] = originalProcessor.getPixel(x, y);
         	   
	         	for (int i = 0; i < 9; i++) {
	         		if (i < 3) {
	         			newPixel = newPixel + (vector[i] * -1);
	         		} else if (i >= 3 && i < 8) {
	         			newPixel = newPixel + (vector[i] * 1);
	         		} else {
	         			newPixel = newPixel + (vector[i] * -2);
	         		}
	         	}
	         	
	         	processorLinear.putPixel(x, y, newPixel);
            }
        }
        
        imageLinear.show();
	}

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGraphic, AWTEvent e) {
		if (interfaceGraphic.wasCanceled()) {
			return false;
		} else {
			return true;
		}
    } 

}
