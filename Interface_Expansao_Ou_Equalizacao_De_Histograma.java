import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Interface_Expansao_Ou_Equalizacao_De_Histograma implements PlugIn, DialogListener {

	private ImagePlus imagem;
	private ImageProcessor processador;
	private ImageProcessor duplicate;
	private ImageProcessor processadorCinza;
	private int vetExpanssao[] = new int[256];
	private int vetEqualizacao[] = new int[256];
	int menorValorPossivel = 0;
	int maiorValorPossivel = 255;
	int menorValorImagem = 255;
	int maiorValorImagem = 0;
	private ImagePlus cinza;

	public void run(String arg) {
		apresentarInterfaceGrafica();

	}

	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("RGB options");

		this.imagem = IJ.getImage();
		this.processador = this.imagem.getProcessor();
		GerarProcessadorCinza();

		interfaceGrafica.addDialogListener(this);

		interfaceGrafica.addMessage("Publin para trabalhar com brilho, constrate ");
		
		String[] estrategia = { "Expansao", "Equalizacao" };
		interfaceGrafica.addRadioButtonGroup("Botões para escolher uma dentre várias estratégias", estrategia, 1, 2,
				"");
		interfaceGrafica.showDialog();

		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");

			resetimagem();
		} else {
			if (interfaceGrafica.wasOKed()) {

				int sliderBrilho = (int) interfaceGrafica.getNextNumber();
				int sliderContraste = (int) interfaceGrafica.getNextNumber();
				

				IJ.log("_____________Últimas respostas obtidas_______________");
				IJ.log("slider 2: " + sliderContraste);
				IJ.log("slider 1: " + sliderBrilho);

				this.cinza = WindowManager.getImage("Cinza");
				cinza.show();
				  IJ.run("Histogram");
				IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void GerarProcessadorCinza() {
		int mediaPixels;
		int pixel[] = new int[3];
		cinza = IJ.createImage("Cinza", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		this.processadorCinza = cinza.getProcessor();
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processador.getPixel(i, j, pixel);
				mediaPixels = (pixel[0] + pixel[1] + pixel[2]) / 3;
				processadorCinza.putPixel(i, j, mediaPixels);
			}
		}
		cinza.show();

	}

	private void resetimagem() {
		this.cinza.setProcessor(this.processadorCinza);
		this.imagem.updateAndDraw();

	}

	

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {

		if (interfaceGrafica.wasCanceled())
			return false;

		Boolean novaImagem = interfaceGrafica.getNextBoolean();
		String resutEstrategia = interfaceGrafica.getNextRadioButton();

		IJ.log("Resposta do botão de rádio:" + resutEstrategia);
		IJ.log("Resposta do checkbox:" + novaImagem);
		IJ.log("\n");
		if (resutEstrategia.equals("Expansao")) {
			aplicarExpansao();
		} else if (resutEstrategia.equals("Equalizacao")) {
			aplicarEqualizacao();
		}

		return true;
	}

	private void aplicarEqualizacao() {
		
		this.cinza.setProcessor(processadorCinza);
		duplicate = this.processadorCinza.duplicate();
		float totalElementosEqualizacao = 0;
		float vetProbability[] = new float[256];
		float cumulativeProbability[] = new float[256];
		int pixel[] = { 0 };
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);
				vetEqualizacao[pixel[0]]++;
				totalElementosEqualizacao++;
			}
		}
		
		for (int i = 0; i < vetEqualizacao.length; i++) {
			
			
			vetProbability[i] = vetEqualizacao[i]/totalElementosEqualizacao;
		
			if(i == 0) cumulativeProbability[i] = vetProbability[i];
			if(i > 0) {
				
				cumulativeProbability[i] = cumulativeProbability[i-1] + vetProbability[i];
			}
			System.out.println("cumulativeProbability " + i + ": " + cumulativeProbability[i]);
		}
		
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);
				int result = (int) (cumulativeProbability[pixel[0]] * 20);
				duplicate.putPixel(i, j, result);
			}
		}
		this.cinza.setProcessor(duplicate);
		this.cinza.updateAndDraw();
	
	}

	private void aplicarExpansao() {
		maiorEMenorPixelImagem();
		this.cinza.setProcessor(processadorCinza);
		duplicate = this.processadorCinza.duplicate();
		int pixel[] = { 0 };
		int result = 0;
		for (int i = 0; i < vetExpanssao.length; i++) {
			vetExpanssao[i] = 0;
		}

		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);
				result = ((pixel[0] - menorValorImagem) * (maiorValorPossivel / (maiorValorImagem - menorValorImagem)));
				vetExpanssao[result]++;
				duplicate.putPixel(i, j, result);
			}
		}
		for (int i = 0; i < vetExpanssao.length; i++) {
			//System.out.println(i + " Expanssao : " + vetExpanssao[i]);
		}
		this.cinza.setProcessor(duplicate);
		this.cinza.updateAndDraw();
	}

	private void maiorEMenorPixelImagem() {
		int pixel[] = { 0 };
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);

				if (pixel[0] < menorValorImagem) {
					menorValorImagem = pixel[0];
				}

				if (pixel[0] > maiorValorImagem) {
					maiorValorImagem = pixel[0];
				}
			}
		}
		//System.out.println("menor valor imagem: " + menorValorImagem + " e maior valor imagem: " + maiorValorImagem);

	}

}
