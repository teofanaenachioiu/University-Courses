import java.awt.image.BufferedImage;
import java.util.Arrays;
public class DepthBuffer {
	
	public float[][] depthBuff;
	
	public DepthBuffer(int width, int height, BufferedImage buff) {
		depthBuff = new float[width][height];
		for (float[] row : depthBuff) {
			Arrays.fill(row, -999.0f);
		}
	}

}