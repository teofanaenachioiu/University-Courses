public class Cylinder extends Shape{
	private Vector3D center;
	private float rx, ry;
	private int m, n;
	public Mesh3D mesh;
	public Mesh3D bot, top;
	
	private float umin, umax;
	private final float vmin = (float)-Math.PI;
	private final float vmax = (float)Math.PI;
	
	public Cylinder(float x, float y, float z, float rx, float ry, int m, int n, float u, Material mat) {
		center = new Vector3D(x, y, z);
		this.rx = rx;
		this.ry = ry;
		this.m = m;
		this.n = n;
		this.umin = -u;
		this.umax = u;
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
	
	public int getM(){
		return this.m;
	}
	
	public int getN(){
		return this.n;
	}
	
	private void initMesh() {
		mesh = new Mesh3D(m,n);
		bot = new Mesh3D(1,m);
		top = new Mesh3D(1,m);
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
				mesh.v[i][j].x = center.x + rx * c_theta;
				mesh.v[i][j].y = center.y + ry * s_theta;
				mesh.v[i][j].z = center.z + phi;
				
				// Compute unit normal at vertex
				du.x = -rx * s_theta;
				du.y = ry * c_theta;
				du.z = 0;
				
				dv.x = 0;
				dv.y = 0;
				dv.z = 1;
				
				du.crossProduct(dv, mesh.n[i][j]);
				mesh.n[i][j].normalize();
			}
		}
		
		// Endcaps
		for (i = 0, theta = vmin; i < m; ++i, theta += d_theta){
			mesh.n[i][0] = new Vector3D(0,0,-1);
			mesh.v[i][0] = new Vector3D(center.x,center.y,center.z+umin);
			
			mesh.n[i][n-1] = new Vector3D(0,0,1);
			mesh.v[i][n-1] = new Vector3D(center.x,center.y,center.z+umax);
		}
	}
}