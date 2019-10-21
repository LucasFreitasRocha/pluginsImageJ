

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

	 
public class clarear_Plugin_misto implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		/*  8-bit grayscale (unsigned)
		public static final int GRAY8 = 0;

	 	16-bit grayscale (unsigned) 
		public static final int GRAY16 = 1;

		/** 32-bit floating-point grayscale 
		public static final int GRAY32 = 2;

		/** 8-bit indexed color 
		public static final int COLOR_256 = 3;

		/** 32-bit RGB color 
		public static final int COLOR_RGB = 4; */
		
		
		if(imagem.getType() == ImagePlus.COLOR_RGB){
			System.out.println("imagem rgb");
			
			System.out.println(imagem.getTitle());
		}else{
			if(imagem.getType() == ImagePlus.GRAY8 || imagem.getType() == ImagePlus.GRAY16 || imagem.getType() == ImagePlus.GRAY32  ){
				System.out.println("preto e branca");
				
				System.out.println(imagem.getTitle());
				
			}
		}
		
					
	}
}



