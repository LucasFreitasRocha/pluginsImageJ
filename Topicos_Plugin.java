import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;

	 
public class Topicos_Plugin implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
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
		
			System.out.println(imagem.getType());
			
		
	}
}



