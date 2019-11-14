/* PointLight.java
 * Name: Jiazi Cai
 * Email: jiazi@bu.edu
 * 
 * This code is based on the lecture of Nov29, Interpolation of Shading
 * and also implements the determination of radial and angular and
 * lights 
 * 
 */
public class PointLight extends Light {
	public Vector3D direction;
	public Vector3D lightPosition;
	public ColorType color;
	public Boolean radial = false;
	public Boolean angular = false;
	public float alpha;
	private float a0,a1,a2;
	
	public PointLight(ColorType color, Vector3D direction, Vector3D lightPosition) {
		this.color = color;
		this.direction = direction;
		this.lightPosition = lightPosition;
		a0 = 1;
		a1 = a2 = .000001f;
		alpha = 45;
		super.lightIsOn = true;
	}
	
	public ColorType impLight(Material mat, Vector3D v, Vector3D n, Vector3D point) {
		ColorType res = new ColorType();
		
		Vector3D L = lightPosition.minus(point); // vobj
		L.normalize();
		double dot = L.dotProduct(n);
		if (dot > 0.0) {
			if (mat.isDiffuse()) {
				res.r = (float)(dot * mat.get_kdiff().r * color.r);
				res.g = (float)(dot * mat.get_kdiff().g * color.g);
				res.b = (float)(dot * mat.get_kdiff().b * color.b);
			}
			
			if (mat.isSpecular()) {
				Vector3D r = L.reflect(n);
				dot = r.dotProduct(v);
				if (dot > 0.0) {
					res.r += (float)Math.pow((dot * mat.get_kspec().r * color.r), mat.get_specularExp());
					res.g += (float)Math.pow((dot * mat.get_kspec().g * color.g), mat.get_specularExp());
					res.r += (float)Math.pow((dot * mat.get_kspec().b * color.b), mat.get_specularExp());
				}
			}
			
			if (radial) {
				float d = (float) Math.sqrt(Math.pow(lightPosition.x - point.x, 2) + Math.pow(lightPosition.y - point.y, 2) + Math.pow(lightPosition.z - point.z, 2));
				float radialFactor = 1 / (a0 + a1 * d + a2 * (float)Math.pow(d, 2));
				res.r *= radialFactor;
				res.g *= radialFactor;
				res.b *= radialFactor;
			}
			
			if (angular) {
				dot = L.dotProduct(direction);
				if (dot < Math.cos(1.57079633)) {
					res.r *= Math.pow(dot, alpha);
					res.g *= Math.pow(dot, alpha);
					res.b *= Math.pow(dot, alpha);
				}
			}
			
			// Clamp
			res.r = (float)Math.min(1.0, res.r);
			res.g = (float)Math.min(1.0, res.g);
			res.b = (float)Math.min(1.0, res.b);
		}
		return(res);
	}
	
	public void toggleRadial() {
		radial = !radial;
	}
	
	public void toggleAngular() {
		angular = !angular;
	}
	
	public void toggleLight() {
		lightIsOn = !lightIsOn;
	}
}