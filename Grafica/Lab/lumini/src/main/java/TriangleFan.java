import java.awt.image.BufferedImage;

public class TriangleFan {
	public Vector3D center;
	public Mesh3D mesh;
	
	public TriangleFan(float x, float y, float z, Mesh3D mesh) {
		this.center = new Vector3D(x, y, z);
		this.mesh = mesh;
	}
	
	public void drawTriangleFan(BufferedImage buff, DepthBuffer depthBuff, Boolean do_smooth, Point3D[] points, ColorType centerColor) {
		Point3D previous = points[points.length-1];
		Point3D centerPoint = new Point3D((int)center.x, (int)center.y, (int)center.z, centerColor);
		for (Point3D point : points) {
			Triangle.drawTriangle(buff, depthBuff, centerPoint, previous, point, do_smooth);
			previous = point;
		}
	}
}