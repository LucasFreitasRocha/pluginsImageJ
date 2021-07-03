import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class SumImages_ implements PlugIn, DialogListener {

	ImagePlus image = IJ.getImage();
	ImageProcessor processor = image.getProcessor();
	ImagePlus secondImage = IJ.getImage();
	ImageProcessor secondProcessor = secondImage.getProcessor();
	ImagePlus resultImage = IJ.createImage("Imagem Resultante", "8-bit", image.getWidth(), image.getHeight(), 1);
	ImageProcessor resultProcessor = resultImage.getProcessor();
	int[] pixel = new int[2];
	float lowest = 256;
	float highest = -1;
	
	@Override
	public void run(String arg) {

		GenericDialog interfaceGraphic = new GenericDialog("Soma de Imagens");
		interfaceGraphic.addDialogListener(this);
		String[] strategy = { "1 - Truncamento", "2 - Normalização", "3 - Wrapping" };
		interfaceGraphic.addRadioButtonGroup("Tipos de Tratamento", strategy, 3, 1, strategy[0]);
		interfaceGraphic.showDialog();
		WindowManager.putBehind();

		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		} else {
			String option = interfaceGraphic.getNextRadioButton();
			highest = findHighestPixel();
			lowest = findLowestPixel();
			if (option.equalsIgnoreCase(strategy[0])) {
				executeFirstStrategy();
			} else if (option.equalsIgnoreCase(strategy[1])) {
				executeSecondStrategy();
			} else {
				executeThirdStrategy();
			}
			resultImage.updateAndDraw();
			resultImage.show();
		}
	}

	private void executeThirdStrategy() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateThirdStrategy(sum));
			}
		}
	}

	private int validateThirdStrategy(int sum) {
		sum = sum > 255 ? sum - 255 : (-sum) - 255;
		return sum;
	}

	private void executeSecondStrategy() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateSecondStrategy(sum));
			}
		}
	}

	private int validateSecondStrategy(int sum) {
		sum = (int) ( (255 / (highest - lowest)) * (sum - lowest) );
		return sum;
	}

	private void executeFirstStrategy() {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixel[0] = processor.getPixel(x, y);
				pixel[1] = secondProcessor.getPixel(x, y);
				int sum = pixel[0] + pixel[1];
				
				resultProcessor.putPixel(x, y, validateFirstStrategy(sum));
			}
		}
	}

	private int validateFirstStrategy(int sum) {
		sum = sum > 255 ? 255 : (-sum) - 255;		
		return sum;
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

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGraphic, AWTEvent e) {
		if (interfaceGraphic.wasCanceled()) {
			return false;
		} else {
			return true;
		}
	}
}
