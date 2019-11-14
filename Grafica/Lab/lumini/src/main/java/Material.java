//****************************************************************************
//       material class
//****************************************************************************
// History :
//   Nov 6, 2014 Created by Stan Sclaroff
//
public class Material {
	private ColorType kamb, kspec, kdiff;
	private int specularExp;
	public boolean specular, ambient, diffuse;
	
	public Material(ColorType kamb, ColorType kdiff, ColorType kspec, int specularExp) {
		this.kamb = new ColorType(kamb);
		this.kspec = new ColorType(kspec);
		this.kdiff = new ColorType(kdiff);
		this.specularExp = specularExp;
		
		specular = (specularExp>0 && (kspec.r > 0.0 || kspec.g > 0.0 || kspec.b > 0.0));
		diffuse = (kdiff.r > 0.0 || kdiff.g > 0.0 || kdiff.b > 0.0);
		ambient = (kamb.r > 0.0 || kamb.g > 0.0 || kamb.b > 0.0);
	}
	
	public ColorType get_kamb() {
		return this.kamb;
	}
	
	public ColorType get_kdiff() {
		return this.kdiff;
	}
	
	public ColorType get_kspec() {
		return this.kspec;
	}
	
	public int get_specularExp() {
		return this.specularExp;
	}
	
	public boolean isDiffuse() {
		return diffuse;
	}
	
	public boolean isAmbient() {
		return ambient;
	}
	
	public boolean isSpecular() {
		return specular;
	}
}