public class ColorType
{
	public float r, g, b;

	public ColorType( float _r, float _g, float _b)
	{
		r = _r;
		g = _g;
		b = _b;
	}
	public ColorType(double _r, double _g, double _b)
	{
		r = (float)_r;
		g = (float)_g;
		b = (float)_b;
	}
	
	public ColorType(ColorType c)
	{
		r = c.r;
		g = c.g;
		b = c.b;
	}
	public ColorType()
	{
		r = g = b = (float)0.0;
	}

	public int getBRGUint8()
	{
		int _b = Math.round(b*255.0f); 
		int _g = Math.round(g*255.0f); 
		int _r = Math.round(r*255.0f);
		int bb = (_r<<16) | (_g<<8) | _b;
		return bb;
	}
	
	public void setRUint8(int _r)
	{
		r = ((float)_r)/255.0f;
	}

	public void setGUint8(int _g)
	{
		g = ((float)_g)/255.0f;
		
	}	
	
	public void setBUint8(int _b)
	{
		b = ((float)_b)/255.0f;
	}
	
	public int getRUint8()
	{
		return Math.round(r*255.0f);
	}
	
	public int getGUint8()
	{
		return Math.round(g*255.0f);
	}
	
	public int getBUint8()
	{
		return Math.round(b*255.0f);
	}

}