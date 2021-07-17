import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.util.ArrayUtil;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import java.util.Arrays;
import java.util.Collections;

import ij.IJ;
import ij.ImagePlus;

public class interface_filtros_nao_lineares implements PlugIn, DialogListener  {

	ImagePlus originalImage = IJ.getImage();
	ImageProcessor originalProcessor = originalImage.getProcessor();	
	
	ImagePlus imageLinear = IJ.createImage("Imagem Resultante", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(), 1);
	ImageProcessor processorLinear = imageLinear.getProcessor();
	
	public void run(String arg) {
		showInterface();
	}

	private void showInterface() {
		GenericDialog interfaceGraphic = new GenericDialog("Filtros não Lineares");
		interfaceGraphic.addDialogListener(this);
		String[] strategy = {"Mediana", "Moda","Máximo"}; 
		interfaceGraphic.addRadioButtonGroup("Botões para escolher uma dentre várias estratégias", strategy, 1, 3, "Estratégia 2");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(strategy[0])) {
					medianaStrategy(processorLinear);
				} else if (option.equalsIgnoreCase(strategy[1])) {
					modaStrategy(processorLinear);
				} else {
					maxStrategy(processorLinear);
				}
				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void medianaStrategy(ImageProcessor processorLinear) {
		int[] vector = new int[9];
		double median;
		
		for (int x = 1; x< originalProcessor.getWidth()-1; x++) {
            for (int y = 1; y < originalProcessor.getHeight()-1; y++) {
            	vector[0] = originalProcessor.getPixel(x-1,y+1);
	         	vector[1] = originalProcessor.getPixel(x+1,y+1);
	         	vector[2] = originalProcessor.getPixel(x, y+1);
	         	vector[3] = originalProcessor.getPixel(x-1, y-1);
	         	vector[4] = originalProcessor.getPixel(x+1,y-1);
	         	vector[5] = originalProcessor.getPixel(x-1, y);
	         	vector[6] = originalProcessor.getPixel(x+1, y);
	         	vector[7] = originalProcessor.getPixel(x, y-1);
	         	vector[8] = originalProcessor.getPixel(x, y);
	         	Arrays.sort(vector);
	         	if (vector.length % 2 == 0) {
	         	    median = ((double)vector[vector.length/2] + (double)vector[vector.length/2 - 1])/2;
	         	} else {
	         	    median = (double) vector[vector.length/2];
	         	}
	         	
	         	processorLinear.putPixel(x, y, (int) median);
              
            }
        }
        imageLinear.show();
	}
	
	private void modaStrategy(ImageProcessor processor) {
		int[] vector = new int[9];
		
		for (int x = 1; x< originalProcessor.getWidth()-1; x++) {
            for (int y = 1; y < originalProcessor.getHeight()-1; y++) {
            	vector[0] = originalProcessor.getPixel(x-1,y+1);
	         	vector[1] = originalProcessor.getPixel(x+1,y+1);
	         	vector[2] = originalProcessor.getPixel(x, y+1);
	         	vector[3] = originalProcessor.getPixel(x-1, y-1);
	         	vector[4] = originalProcessor.getPixel(x+1,y-1);
	         	vector[5] = originalProcessor.getPixel(x-1, y);
	         	vector[6] = originalProcessor.getPixel(x+1, y);
	         	vector[7] = originalProcessor.getPixel(x, y-1);
	         	vector[8] = originalProcessor.getPixel(x, y);
	         	Arrays.sort(vector);
	         	
	         	processorLinear.putPixel(x, y, getPopularElement(vector));
              
            }
        }
        imageLinear.show();
	}
	
	private int getPopularElement(int[] vector) {
		int count = 1, tempCount;
		int popular = vector[0];
		int temp = 0;
		for (int i = 0; i < (vector.length - 1); i++) {
			temp = vector[i];
			tempCount = 0;
			for (int j = 1; j < vector.length; j++) {
				if (temp == vector[j]) tempCount++;
			}
			if (tempCount > count) {
				popular = temp;
				count = tempCount;
			}
		}
		return popular;
	}

	private void maxStrategy(ImageProcessor processor) {
		int[] vector = new int[9];
		
		for (int x = 1; x < originalProcessor.getWidth()-1; x++) {
            for (int y = 1; y < originalProcessor.getHeight()-1; y++) {
            	vector[0] = originalProcessor.getPixel(x-1,y+1);
	         	vector[1] = originalProcessor.getPixel(x+1,y+1);
	         	vector[2] = originalProcessor.getPixel(x, y+1);
	         	vector[3] = originalProcessor.getPixel(x-1, y-1);
	         	vector[4] = originalProcessor.getPixel(x+1,y-1);
	         	vector[5] = originalProcessor.getPixel(x-1, y);
	         	vector[6] = originalProcessor.getPixel(x+1, y);
	         	vector[7] = originalProcessor.getPixel(x, y-1);
	         	vector[8] = originalProcessor.getPixel(x, y);
	         	Arrays.sort(vector);
	         	
	         	processorLinear.putPixel(x, y, Arrays.stream(vector).max().getAsInt());
              
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
