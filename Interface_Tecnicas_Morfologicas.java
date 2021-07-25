import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;

public class Interface_Tecnicas_Morfologicas implements PlugIn, DialogListener {
	
	ImagePlus originalImage = IJ.getImage();
	ImageProcessor originalProcessor = originalImage.getProcessor();	
	
	ImagePlus dilateImage = IJ.createImage("Imagem Resultante da Dilatação", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(), 1);
	ImageProcessor dilateProcessor = dilateImage.getProcessor();
	
	ImagePlus erodeImage = IJ.createImage("Imagem Resultante da Erosão", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(), 1);
	ImageProcessor erodeProcessor = erodeImage.getProcessor();
	
	ImagePlus auxImage = IJ.createImage("Imagem Auxiliar para Fechamento", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(),1);
	ImageProcessor auxProcessor = auxImage.getProcessor();
	
	ImagePlus closeImage = IJ.createImage("Imagem Resultante do Fechamento", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(),1);
	ImageProcessor closeProcessor = closeImage.getProcessor();
	
	ImagePlus openImage = IJ.createImage("Imagem Resultante da Abertura", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(),1);
	ImageProcessor openProcessor = openImage.getProcessor();
	
	ImagePlus outlineImage = IJ.createImage("Outline", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(),1);
	ImageProcessor outlineProcessor = outlineImage.getProcessor();
	
	public void run(String arg) {
		showInterface();
	}

	private void showInterface() {
		GenericDialog interfaceGraphic = new GenericDialog("Técnicas Morfológicas");
		interfaceGraphic.addDialogListener(this);
		String[] strategy = {"Dilatação", "Erosão", "Fechamento", "Abertura", "Borda (Outline)"}; 
		interfaceGraphic.addRadioButtonGroup("Botões para escolher uma dentre várias estratégias", strategy, 1, 3, "Dilatação");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(strategy[0])) {
					dilateStrategy();
				} else if (option.equalsIgnoreCase(strategy[1])) {
					erodeStrategy();
				} else if (option.equalsIgnoreCase(strategy[2])) {
					closeStrategy();
				} else if (option.equalsIgnoreCase(strategy[3])) {
					openStrategy();
				} else {
					outlineStrategy();
				}
				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void outlineStrategy() {
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyErode(x, y, originalProcessor, erodeProcessor);
				applyOutline(x, y, originalProcessor, erodeProcessor, outlineProcessor);
			}
		}
		outlineImage.show();
	}

	private void applyOutline(int xPixel, int yPixel, ImageProcessor originalProcessor, ImageProcessor erodeProcessor, ImageProcessor outlineProcessor) {
		outlineProcessor.putPixel(xPixel, yPixel, (erodeProcessor.getPixel(xPixel, yPixel) - originalProcessor.getPixel(xPixel, yPixel)));
	}

	private void openStrategy() {
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyErode(x, y, originalProcessor, auxProcessor);
			}
		}
		
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyDilate(x, y, auxProcessor, openProcessor);
			}
		}
		
		openImage.show();
	}

	private void closeStrategy() {
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyDilate(x, y, originalProcessor, auxProcessor);
			}
		}
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyErode(x, y, auxProcessor, closeProcessor);
			}
		}
		closeImage.show();
	}

	private void dilateStrategy() {
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyDilate(x, y, originalProcessor, dilateProcessor);
			}
		}
		dilateImage.show();
	}
	
	public void applyDilate(int xPixel, int yPixel, ImageProcessor originalProcessor, ImageProcessor dilateProcessor) {
		if ((yPixel > 0 && yPixel < originalProcessor.getHeight() - 1) && (xPixel > 0 && xPixel < originalProcessor.getWidth() - 1)) {
			if (originalProcessor.getPixel(xPixel, yPixel) == 0) {
				for (int xImagem = xPixel - 1; xImagem <= xPixel + 1; xImagem++) {
					for (int yImagem = yPixel - 1; yImagem <= yPixel + 1; yImagem++) {
						dilateProcessor.putPixel(xImagem, yImagem, 0);
					}
				}
			}
		}
	}
	
	private void erodeStrategy() {
		for (int x = 0; x < originalProcessor.getWidth(); x++) {
			for (int y = 0; y < originalProcessor.getHeight(); y++) {
				applyErode(x, y, originalProcessor, erodeProcessor);
			}
		}
		erodeImage.show();
	}
	
	private void applyErode(int xPixel, int yPixel, ImageProcessor originalProcessor, ImageProcessor erodeProcessor) {
		int element = 0;
		if ((yPixel > 0 && yPixel < originalProcessor.getHeight() - 1) && (xPixel > 0 && xPixel < originalProcessor.getWidth() - 1)) {
			if (originalProcessor.getPixel(xPixel, yPixel) == 0) {
				for (int xImage = xPixel - 1; xImage <= xPixel + 1; xImage++) {
					for (int yImage = yPixel - 1; yImage <= yPixel + 1; yImage++) {
						element += validateElement(originalProcessor.getPixel(xImage, yImage));
					}
				}
				if (element == 9) erodeProcessor.putPixel(xPixel, yPixel, 0);
			}
		}
	}
	
	public int validateElement(int validPixel) {
		if (validPixel == 0) return 1;
		return 0;
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
