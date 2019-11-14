public class Quaternion {
  /** The threshold below which to renormalize this quaternion, if necessary. */
  public static final float ROUND_OFF_THRESHOLD = 0.0001f;
  /** The scalar component of this quaternion. */
  private float s;

  /** The vector components of this quaternion. */
  //private final float[] v = new float[3];
  private Vector3D v = new Vector3D();

  /**
   * Instantiates this quaternion with an initial value of (1, 0, 0, 0).
   */
  public Quaternion() {
    this.set(1, 0, 0, 0);
  }

  /**
   * Instantiates this quaternion with the specified component values.
   * 
   * 
   * @param s
   *          The scalar component of this quaternion.
   * @param v0
   *          The first vector component of this quaternion.
   * @param v1
   *          The second vector component of this quaternion.
   * @param v2
   *          The third vector component of this quaternion.
   */
  public Quaternion(float s, float v0, float v1, float v2) {
    this.set(s, v0, v1, v2);
  }

  public Quaternion(float s, Vector3D v) {
	    this.set(s, v.x, v.y, v.z);
	  }

  /**
   * Returns a new quaternion representing the product of this and the specified
   * other quaternion.
   * 
   * @param that
   *          The other quaternion with which to multiply this one.
   * @return The product of this and the specified other quaternion.
   */
  public Quaternion multiply(final Quaternion that) {

	    // s = s1*s2 - v1.v2
	    final float newS = this.s * that.s - this.v.x * that.v.x - this.v.y
	        * that.v.y - this.v.z * that.v.z;

	    // v = s1 v2 + s2 v1 + v1 x v2
	    float i = (this.s * that.v.x) + (that.s * this.v.x)
	        + (this.v.y * that.v.z - this.v.z * that.v.y);
	    float j = (this.s * that.v.y) + (that.s * this.v.y)
	        + (this.v.z * that.v.x - this.v.x * that.v.z);
	    float k = (this.s * that.v.z) + (that.s * this.v.z)
	        + (this.v.x * that.v.y - this.v.y * that.v.x);

	    return new Quaternion(newS, i, j, k);
  }

  public Quaternion conjugate()
  {
	return(new Quaternion(s,v.scale((float)-1.0)));
  }
  
  /**
   * Returns the norm (magnitude) of this quaternion.
   * 
   * @return The norm (magnitude) of this quaternion.
   */
  private float norm() {
    return (float) Math.sqrt(this.s * this.s + this.v.x*this.v.x + this.v.y*this.v.y + this.v.z*this.v.z);
  }

  /**
   * Normalizes this quaternion so that it is truly unit.
   * 
   * It may be necessary to call this method after accumulating round-off error.
   */
  public void normalize() {
    final float mag = this.norm();

    if (mag > ROUND_OFF_THRESHOLD) {
      this.s /= mag;
      this.v.x /= mag;
      this.v.y /= mag;
      this.v.z /= mag;
    }
  }

  /**
   * Resets this quaternion to (1, 0, 0, 0).
   */
  public void reset() {
    this.set(1f, 0f, 0f, 0f);
  }

  /**
   * Sets the components of this quaternion to the specified values.
   * 
   * @param s
   *          The scalar component of this quaternion.
   * @param v0
   *          The first vector component of this quaternion.
   * @param v1
   *          The second vector component of this quaternion.
   * @param v2
   *          The third vector component of this quaternion.
   */
  private void set(float s, float v0, float v1, float v2) {
    this.s = s;
    this.v.x = v0;
    this.v.y = v1;
    this.v.z = v2;
  }

  public Vector3D get_v() {
	    Vector3D res = new Vector3D(this.v);
	    return(res);
	  }


/* Returns a 4 by 4 matrix which represents a transformation equivalent to
   * that of this quaternion.
   * 
   * Note: OpenGL uses column major order when specifying a matrix.
   * 
   * Algorithm: follows equation 5-107 on page 273 of Hearn and Baker.
   * 
   * @return
   */
  public float[] toMatrix() {
    final float[] M = new float[16];

    final float a = this.v.x;
    final float b = this.v.y;
    final float c = this.v.z;

    // Specify the matrix in column major
    M[0] = 1 - 2 * b * b - 2 * c * c; // M[0][0]
    M[1] = 2 * a * b + 2 * this.s * c; // M[1][0]
    M[2] = 2 * a * c - 2 * this.s * b; // M[2][0]
    M[3] = 0.0f; // M[3][0]

    M[4] = 2 * a * b - 2 * this.s * c; // M[0][1]
    M[5] = 1 - 2 * a * a - 2 * c * c; // M[1][1]
    M[6] = 2 * b * c + 2 * this.s * a; // M[2][1]
    M[7] = 0.0f; // M[3][1]

    M[8] = 2 * a * c + 2 * this.s * b; // M[0][2]
    M[9] = 2 * b * c - 2 * this.s * a; // M[1][2]
    M[10] = 1 - 2 * a * a - 2 * b * b; // M[2][2]
    M[11] = 0.0f; // M[3][2]

    M[12] = 0.0f; // M[0][3]
    M[13] = 0.0f; // M[1][3]
    M[14] = 0.0f; // M[2][3]
    M[15] = 1.0f; // M[3][3]

    return M;
  }

}