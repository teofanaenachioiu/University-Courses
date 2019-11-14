/* AmbientLight.java
 * Name: Jiazi Cai
 * Email: jiazi@bu.edu
 * This is based on the code from lab9 and implement the ambientlight from
 * the lecture and ppt.
 */
public class AmbientLight extends Light {

	public Vector3D direction;
	public ColorType color;
	
	public AmbientLight(ColorType color, Vector3D direction) {
		this.color = color;
		this.direction = direction;
		super.lightIsOn = true;
	}
	
	public ColorType impLight(Material mat, Vector3D v, Vector3D n, Vector3D p) {
		ColorType res = new ColorType();
		
		if (mat.isAmbient()) {
			res.r = (float) (color.r * mat.get_kamb().r);
			res.g = (float) (color.g * mat.get_kamb().g);
			res.b = (float) (color.b * mat.get_kamb().b);
		}

		// Clamp
		res.r = (float) Math.min(1.0, res.r);
		res.g = (float) Math.min(1.0, res.g);
		res.b = (float) Math.min(1.0, res.b);
		return(res);
	}
	
	public void toggleLight() {
		lightIsOn = !lightIsOn;
	}
}