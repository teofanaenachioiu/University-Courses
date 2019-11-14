public class Point3D
{
	public int x, y, z;
	public float u, v, w; // uv coordinates for texture mapping
	public ColorType c;
	public Vector3D n;
	public Point3D(int _x, int _y, int _z, ColorType _c)
	{
		u = 0;
		v = 0;
		w = 0;
		x = _x;
		y = _y;
		z = _z;
		c = _c;
	}
	public Point3D(int _x, int _y, int _z, ColorType _c, float _u, float _v, float _w)
	{
		u = _u;
		v = _v;
		w = _w;
		x = _x;
		y = _y;
		z = _z;
		c = _c;
	}
	public Point3D()
	{
		c = new ColorType(1.0f, 1.0f, 1.0f);
	}
	public Point3D( Point3D p)
	{
		u = p.u;
		v = p.v;
		w = p.w;
		x = p.x;
		y = p.y;
		z = p.z;
		c = new ColorType(p.c.r, p.c.g, p.c.b);
	}
	public Vector3D toVector() {
		return new Vector3D(x, y, z);
	}
}