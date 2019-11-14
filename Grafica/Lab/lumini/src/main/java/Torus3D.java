public class Torus3D extends Shape {
	private Vector3D center;
	private float r, r_axial;
	private int m, n;
	public Mesh3D mesh;
	
	private final float umin = (float)-Math.PI;
	private final float umax = (float)Math.PI;
	private final float vmin = (float)-Math.PI;
	private final float vmax = (float)Math.PI;
	
	public Torus3D(float x, float y, float z, float r, float r_axial, int m, int n, Material mat) {
		center = new Vector3D(x, y, z);
		this.r = r;
		this.r_axial = r_axial;
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
	
	public void setRadius(float r) {
		this.r = r;
		fillMesh();
	}
	
	public void setAxial(float r_axial) {
		this.r_axial = r_axial;
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
	
	public float getRadius() {
		return this.r;
	}
	
	public float getAxial() {
		return this.r_axial;
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
				mesh.v[i][j].x = center.x + (r_axial + r * c_phi) * c_theta;
				mesh.v[i][j].y = center.y + (r_axial + r * c_phi) * s_theta;
				mesh.v[i][j].z = center.z + r * s_phi;
				
				// Compute unit normal at vertex
				du.x = -(r_axial + r * c_phi) * s_theta;
				du.y = (r_axial + r * c_phi) * c_theta;
				du.z = 0;
				
				dv.x = -r * s_phi * c_theta;
				dv.y = -r * s_phi * s_theta;
				dv.z = r * c_phi;
				
				du.crossProduct(dv, mesh.n[i][j]);
				mesh.n[i][j].normalize();
			}
		}
	}
}