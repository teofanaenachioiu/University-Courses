/*Light.java
 * 
 * Name: Jiazi Cai
 * Mail: jiazi@bu.edu
 * This is the the apply of lights on the objects if is ambient light
 * and can add new light or remove light, it can toggle light effects
 * on the objects, based on the code from Lab9 and make some changes.
 */
import java.util.*;


public class Light {
	
	public List<Light> lights;
	public boolean lightIsOn;
	
	public Light() {
		this.lights = new ArrayList<Light>();
		lightIsOn = true;
	}
	
	public void addLight(Light newLight) {
		lights.add(newLight);
	}
	
	public void removeLight(Light l) {
		lights.remove(l);
	}
	
	public ColorType impLight(Material mat, Vector3D v, Vector3D n, Vector3D p) {
		ColorType res = new ColorType();
		ColorType temp = new ColorType();
		ColorType amb = new ColorType();
		for (Light l : lights) {
			if (l instanceof AmbientLight) {
				if (l.lightIsOn) {
					amb = l.impLight(mat, v, n, p);
				}
			} else {
				if (l.lightIsOn) {
					temp = l.impLight(mat, v, n, p);
					res.r += temp.r;
					res.g += temp.g;
					res.b += temp.b;
				}
			}
		}
		
		res.r = amb.r + res.r;
		res.g = amb.g + res.g;
		res.b = amb.b + res.b;
		
		// Clamp
		res.r = (float) Math.min(1.0, res.r);
		res.g = (float) Math.min(1.0, res.g);
		res.b = (float) Math.min(1.0, res.b);
		
		return res;
	}
	
	public void toggleLight() {
		lightIsOn = !lightIsOn;
	}
	
	
}