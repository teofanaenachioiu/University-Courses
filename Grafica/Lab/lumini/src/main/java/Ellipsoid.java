/*
 * Ellipsoid.java
 * Name: Jiazi Cai
 * Email: jiazi@bu.edu
 * 
 * Adapted from Sphere3D.java
 */
public class Ellipsoid extends Shape{
	private Vector3D center;
	private float rx, ry, rz;
	private int m, n;
	public Mesh3D mesh;
	
	private final float umin = (float)-Math.PI/2;
	private final float umax = (float)Math.PI/2;
	private final float vmin = (float)-Math.PI;
	private final float vmax = (float)Math.PI;
	
	
	public Ellipsoid(float x, float y, float z, float rx, float ry, float rz, int m, int n, Material mat) {
		this.center = new Vector3D(x, y, z);
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.m = m;
		this.n = n;
		super.mat = mat;
		initMesh();
	}
	
	public void setCenter(float x, float y, float z) {
		this.center.x = x;
		this.center.y = y;
		this.center.z = z;
		fillMesh();
	}
	
	public void setRadiusX(float rx) {
		this.rx = rx;
		fillMesh();
	}
	
	public void setRadiusY(float ry) {
		this.ry = ry;
		fillMesh();
	}
	
	public void setRadiusZ(float rz) {
		this.rz = rz;
		fillMesh();
	}
	
	public void setM(int m) {
		this.m = m;
		initMesh();
	}
	
	public void setN(int n) {
		this.n = n;
		initMesh();
	}
	
	public Vector3D getCenter() {
		return this.center;
	}
	
	public float getRadiusX() {
		return this.rx;
	}
	
	public float getRadiusY() {
		return this.ry;
	}
	
	public float getRadiusZ() {
		return this.rz;
	}
	
	public int getM(){
		return this.m;
	}
	
	public int getN(){
		return this.n;
	}
	
	private void initMesh() {
		mesh = new Mesh3D(m,n);
		fillMesh();
	}
	
	private void fillMesh() {
		int i, j;
		float theta, phi;
		float d_phi = (umax-umin)/((float)n-1);
		float d_theta = (vmax-vmin)/((float)m-1);
		
		float c_theta, s_theta, c_phi, s_phi;
		
		Vector3D du = new Vector3D();
		Vector3D dv = new Vector3D();
		
		for (i = 0, theta = vmin; i < m; ++i, theta += d_theta){
			c_theta = (float)Math.cos(theta);
			s_theta = (float)Math.sin(theta);
			
			for (j = 0, phi = umin; j < n; ++j, phi += d_phi) {
				c_phi = (float)Math.cos(phi);
				s_phi = (float)Math.sin(phi);
				
				// Compute vertex
				mesh.v[i][j].x = center.x + rx * c_phi * c_theta;
				mesh.v[i][j].y = center.y + ry * c_phi * s_theta;
				mesh.v[i][j].z = center.z + rz * s_phi;
				
				// Compute unit normal at vertex
				du.x = -(center.x + rx * c_phi) * s_theta;
				du.y = (center.y + ry * c_phi) * c_theta;
				du.z = 0;
				
				dv.x = -rx * s_phi * c_theta;
				dv.y = -ry * s_phi * s_theta;
				dv.z = rz * c_phi;
				
				du.crossProduct(dv, mesh.n[i][j]);
				mesh.n[i][j].normalize();
			}
		}
	}
}