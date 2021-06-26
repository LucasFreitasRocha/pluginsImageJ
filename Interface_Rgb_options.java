import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Interface_Rgb_options implements  PlugIn, DialogListener {

	// incompleto professor
	private ImagePlus imagem;
	private ImageProcessor processador;
	private ImageProcessor duplicate;
	

	public void run(String arg) {
		apresentarInterfaceGrafica();
		
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("RGB options");
		
		this.imagem = IJ.getImage();
		this.processador = this.imagem.getProcessor();
		
		interfaceGrafica.addDialogListener(this);
		
		interfaceGrafica.addMessage("Publin para trabalhar com brilho, constrate ");
		interfaceGrafica.addCheckbox("Gerar nova imagem ?", true);
		interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Contraste", 0, 255, 0, 1);
		interfaceGrafica.addSlider("Solarização  No Pixel mínimo: ", 0, 255, 0, 1);
        interfaceGrafica.addSlider("Solarização  No Pixel No máximo: ", 0, 255, 0, 1);
        interfaceGrafica.addSlider("Configurar Desaturação: ", 0, 1, 1, 0.1);
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
			
			resetimagem();
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				
				int sliderBrilho = (int) interfaceGrafica.getNextNumber();
				int sliderContraste = (int) interfaceGrafica.getNextNumber();
				Boolean novaImagem = interfaceGrafica.getNextBoolean();
				
				IJ.log("_____________Últimas respostas obtidas_______________");
				IJ.log("slider 2: "  + sliderContraste);
		        IJ.log("slider 1: "  +  sliderBrilho );
		        
		        if(novaImagem) {
		        	gerarNovaImagem();
		        }
		        
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	
	private void resetimagem() {
		this.imagem.setProcessor(this.processador);
		this.imagem.updateAndDraw();
		
	}

	private void gerarNovaImagem() {
		ImagePlus rgb = IJ.createImage("RGB", "RGB", this.processador.getWidth(), this.processador.getHeight(), 1);
		rgb.setProcessor(this.duplicate);
		rgb.show();
		
	}

	
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		
		if (interfaceGrafica.wasCanceled()) return false;
		int sliderBrilho = (int) interfaceGrafica.getNextNumber();
		int sliderContraste = (int) interfaceGrafica.getNextNumber();
		int sliderSolarizacaoMinima = (int) interfaceGrafica.getNextNumber();
	    int sliderSolarizacaoMaxima = (int) interfaceGrafica.getNextNumber();
	    float sliderDesolarizacao = (float) interfaceGrafica.getNextNumber();
	
		alterarImagem(sliderBrilho,sliderContraste, sliderSolarizacaoMinima, sliderSolarizacaoMaxima, sliderDesolarizacao );
		/* juntar alteracoes em um metodo só */
        
        IJ.log("slider 1: "  + sliderBrilho );
        IJ.log("slider 2: "  + sliderContraste);
        IJ.log("\n");
        return true;
    }
	
	private int validarPixel(int pixel) {
		 if(pixel < 0){
			 pixel = 0;
		 }
		 if(pixel > 255){
			 pixel = 255;
		 }
		 return pixel;
	}

	private void alterarImagem(int sliderBrilho, int sliderContraste, int sliderSolarizacaoMinima, int sliderSolarizacaoMaxima, float sliderDesolarizacao) {
		// TODO Auto-generated method stub
		this.imagem.setProcessor(this.processador);
		int c = sliderContraste;
		int pixel[]  = new int[3];
		int f  = (259 * (c + 255))/(255*(259-c));
		duplicate = this.processador.duplicate();
		
		// constrate
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					 pixel[rgb] = (f * (pixel[rgb] - 128)) + 128; 
					 pixel[rgb] = validarPixel(pixel[rgb]);
				 }	 
				duplicate.putPixel(i, j, pixel);
			}
		}
		
		// brilho
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   duplicate.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					 pixel[rgb] += sliderBrilho;  
					 pixel[rgb] = validarPixel(pixel[rgb]);
				 }	 
				duplicate.putPixel(i, j, pixel);
			}
		}
		// Solarizacao
		for (int i = 0; i < processador.getWidth(); i++) {
            for (int j = 0; j < processador.getHeight(); j++) {
                pixel = duplicate.getPixel(i, j, pixel);

                for (int rgb = 0; rgb < 3; rgb++) {
                    if ((pixel[rgb] > sliderSolarizacaoMinima) && (pixel[rgb] < sliderSolarizacaoMaxima)) {
                        pixel[rgb] = 255 - pixel[rgb];
                    }
                }
                duplicate.putPixel(i, j, pixel);
            }
        }
		
		//Dessaturacao
		 for (int i = 0; i < processador.getWidth(); i++) {
	            for (int j = 0; j < processador.getHeight(); j++) {
	                pixel = duplicate.getPixel(i, j, pixel);

	                float mediaPixels = (pixel[0] + pixel[1] + pixel[2]) / 3;

	                for(int rgb = 0; rgb < pixel.length; rgb++) {
	                    pixel[rgb] = (int) mediaPixels + (int) ( sliderDesolarizacao * ( pixel[rgb] - (int) mediaPixels ) ) ;
	                }

	                duplicate.putPixel(i, j, pixel);
	            }
	        }
		this.imagem.setProcessor(duplicate);
		this.imagem.updateAndDraw();

	}

}
