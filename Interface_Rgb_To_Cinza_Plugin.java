import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Interface_Rgb_To_Cinza_Plugin implements  PlugIn, DialogListener {
	
	double wr = 0;
	double wg = 0;
	double wb = 0;
	
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("RGB to 8 bits");
		interfaceGrafica.addDialogListener(this);
		
		String[] estrategia = {"media", "ld","la"}; 
		interfaceGrafica.addMessage("Esse plugin converte imagem colorida para cinza");
		interfaceGrafica.addRadioButtonGroup("Botões para escolher uma dentre várias estratégias", estrategia, 1, 3, "media");
		interfaceGrafica.addCheckbox("Gerar nova imagem ? ", false);
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				
				Boolean novaImagem = interfaceGrafica.getNextBoolean();
				String resutEstrategia = interfaceGrafica.getNextRadioButton();
				IJ.log("_____________Últimas respostas obtidas_______________");
				IJ.log("Resposta do botão de rádio:" + resutEstrategia);
		        IJ.log("Resposta do checkbox:" + novaImagem);
		        IJ.showMessage("Plugin encerrado com sucesso!");
		        
		        if(resutEstrategia.equals("media")) {
		        	 IJ.log("estrageia selecionada foi a media");
					this.wr = 1;
					this.wg = 1;
					this.wb = 1;
				}
		        if(resutEstrategia.equals("ld")) {
		        	 IJ.log("estrageia selecionada foi a Luminance digital");
						this.wr = 0.299;
						this.wg = 0.587;
						this.wb = 0.114;
		        }
		        if(resutEstrategia.equals("la")) {
		        	 IJ.log("estrageia selecionada foi a Luminance analogica");
						this.wr = 0.2125;
						this.wg = 0.7154;
						this.wb = 0.072;
		        }
				
		        
		      if(novaImagem) {
		        	gerarNovaImage();
		        }else {
		        	TransformarImage();
		        } 
		    	
			}
		}
	}

	private void TransformarImage() {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		ImageProcessor procCinza = new ByteProcessor(processador.getWidth(),processador.getHeight());
		int pixel[]  = new int[3];
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				int result = (int) (((pixel[0] * this.wr) + (pixel[1] * this.wg) + (pixel[2] * this.wb))/3);
				procCinza.putPixel(i, j, result);	
			}
		}
		imagem.setProcessor(procCinza);
		imagem.updateAndDraw();
	}

	private void gerarNovaImage() {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		
		ImagePlus cinza = IJ.createImage("Cinza", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor procCinza = cinza.getProcessor();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				int result = (int) (((pixel[0] * this.wr) + (pixel[1] * this.wg) + (pixel[2] * this.wb))/3);
				procCinza.putPixel(i, j, result);	
			}
		}
		cinza.show();
		
	}

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		IJ.log("Resposta do botão de rádio:" + interfaceGrafica.getNextRadioButton());
        IJ.log("Resposta do checkbox:" + interfaceGrafica.getNextBoolean());
        IJ.log("\n");
        return true;
    }
	
}
