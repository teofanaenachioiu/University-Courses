import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.*;

//import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl
public class PA4 extends JFrame implements GLEventListener, KeyListener,
		MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_WINDOW_WIDTH = 512;
	private final int DEFAULT_WINDOW_HEIGHT = 512;
	private final float DEFAULT_LINE_WIDTH = 1.0f;

	private GLCapabilities capabilities;
	private GLCanvas canvas;
	private FPSAnimator animator;

	final private int numTestCase;
	private int testCase;
	private BufferedImage buff;
	@SuppressWarnings("unused")
	private ColorType color;
	private Random rng;

	// specular exponent for materials
	private int ns = 5;

	private ArrayList<Point2D> lineSegs;
	private ArrayList<Point2D> triangles;
	private boolean doSmoothShading;
	private boolean phong;
	private boolean flat;
	private boolean gouraud;
	private int Nsteps;

	/** The quaternion which controls the rotation of the world. */
	private Quaternion viewing_quaternion = new Quaternion();
	private Vector3D viewing_center = new Vector3D(
			(float) (DEFAULT_WINDOW_WIDTH / 2),
			(float) (DEFAULT_WINDOW_HEIGHT / 2), (float) 0.0);
	/** The last x and y coordinates of the mouse press. */
	private int last_x = 0, last_y = 0;
	/** Whether the world is being rotated. */
	private boolean rotate_world = false;
	
	private List<Shape> objects;
	
	private Light light;
	private Light selectedLight;
	private boolean toggleLights;
	private boolean lightsInitialized;
	private ColorType ka, kd, ks, kdtemp, katemp,ka_t,kd_t,ks_t;
	private Boolean specular, diffuse, ambient;
	private boolean objects_scaled = false;
	private boolean objects_translated = false;
	private boolean objects_rotated = false;
	private int global_x=0;
	private int global_y=0;
	private int scale_l=0;
	private int scale_s=0;
	private int kat = 0;
	private int kdt = 0;
	private int kst = 0;
	

	public PA4() {
		capabilities = new GLCapabilities(null);
		capabilities.setDoubleBuffered(true);

		canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
		canvas.setAutoSwapBufferMode(true);
		canvas.setFocusable(true);
		getContentPane().add(canvas);

		animator = new FPSAnimator(canvas, 60);

		numTestCase = 5;
		testCase = 0;
		Nsteps = 12;

		setTitle("CS480 PA4");
		setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		rng = new Random();
		color = new ColorType(1.0f, 0.0f, 0.0f);
		lineSegs = new ArrayList<Point2D>();
		triangles = new ArrayList<Point2D>();
		doSmoothShading = false;
		
		gouraud = true;
		phong = flat = false;
		
		objects = new ArrayList<Shape>();
		
		light = new Light();
		selectedLight = null;
		toggleLights = false;
		
		float r = rng.nextFloat();
		float g = rng.nextFloat();
		float b = rng.nextFloat();
		ka = new ColorType(r/6, g/6, b/6);
		ks = new ColorType(1.0, 1.0, 1.0);
		kd = new ColorType(r,g,b);
		
		// Temps for toggles
		kdtemp = new ColorType(kd);
		katemp = new ColorType(ka);
		specular = diffuse = ambient = true;
	}

	public void run() {
		animator.start();
	}

	public static void main(String[] args) {
		PA4 P = new PA4();
		P.run();
	}

	// ***********************************************
	// GLEventListener Interfaces
	// ***********************************************
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glLineWidth(DEFAULT_LINE_WIDTH);
		Dimension sz = this.getContentPane().getSize();
		buff = new BufferedImage(sz.width, sz.height,
				BufferedImage.TYPE_3BYTE_BGR);
		clearPixelBuffer();
	}

	// Redisplaying graphics
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		WritableRaster wr = buff.getRaster();
		DataBufferByte dbb = (DataBufferByte) wr.getDataBuffer();
		byte[] data = dbb.getData();

		gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
		gl.glDrawPixels(buff.getWidth(), buff.getHeight(), GL2.GL_BGR,
				GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(data));
		
		drawTestCase();
	}

	// Window size change
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		// deliberately left blank
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// deliberately left blank
	}

	void clearPixelBuffer() {
		lineSegs.clear();
		triangles.clear();
		Graphics2D g = buff.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
		g.dispose();
	}

	// drawTest
	void drawTestCase() {
		/* clear the window and vertex state */
		clearPixelBuffer();

		// System.out.printf("Test case = %d\n",testCase);

		switch (testCase) {
		case 0:
			testOne();
			break;
		case 1:
			testTwo();
			break;
		case 2:
			testThree();
			break;
		case 3:
			testFour();
			break;
		}
		
	}

	// ***********************************************
	// KeyListener Interfaces
	// ***********************************************
	public void keyTyped(KeyEvent key) {
		// Q,q: quit
		// C,c: clear pixel buffer
		// R,r: randomly change the color
		// P,p: toggle phong shading
		// F,f: toggle flat shading
		// G,g: toggle gouraud shading
		// A,a: toggle ambient term
		// S,s: toggle specular term
		// D,d: toggle diffuse term
		// L,l: toggle light control
		// 1,2,3: toggle lights on and off
		// T,t: show testing examples
		// >: increase the step number for examples
		// <: decrease the step number for examples
		// +,-: increase or decrease spectral exponent
		// U,u: toggle to scale all objects
		// V,v: toggle to translate all objects
		// X,x: toggle to scale all objects;
		// Z,z: toggle to 
		// ;: globally translate up
		// .: globally translate down
		// ,: globally translate left
		// /: globally translate right
		// 4: change k_a value, increment
		// 5: change k_a value, decrement
		// 6: change k_d value, increment
		// 7: change k_d value, decrement
		// 8: change k_s value, increment
		// 9: change k_s value, decrement
		switch (key.getKeyChar()) {
		case 'Q':
		case 'q':
			new Thread() {
				public void run() {
					animator.stop();
				}
			}.start();
			System.exit(0);
			break;
		case 'L':
		case 'l':
			toggleLights = !toggleLights;
			break;
		case '1':
			if (toggleLights) {
				if (light.lights.size() >= 1) {
					light.lights.get(0).toggleLight();
				}
			}
			break;
			
		case '2':
			if (toggleLights) {
				if (light.lights.size() >= 2) {
					light.lights.get(1).toggleLight();
				}
			}
			break;
			
		case '3':
			if (toggleLights) {
				if (light.lights.size() >= 3) {
					light.lights.get(2).toggleLight();
				}
			}
		
			break;
		case 'R':
		case 'r':
			kd = new ColorType(rng.nextFloat(), rng.nextFloat(),
					rng.nextFloat());
			ka = new ColorType(kd.r/6, kd.g/6, kd.b/6);
			kdtemp = new ColorType(kd);
			katemp = new ColorType(ka);
			break;
		case 'C':
		case 'c':
			clearPixelBuffer();
			break;
		case 'S':
		case 's':
			if (specular) {
				specular = false;
				ks = new ColorType(0.0, 0.0, 0.0);
			} else {
				specular = true;
				ks = new ColorType(1.0, 1.0, 1.0);
			}
			break;
		case 'D':
		case 'd':
			if (diffuse) {
				diffuse = false;
				kdtemp = new ColorType(kd);
				kd = new ColorType(0.0, 0.0, 0.0);
			} else {
				diffuse = true;
				kd = new ColorType(kdtemp);
			}
			break;
		case 'A':
		case 'a':
			if (ambient) {
				ambient = false;
				katemp = new ColorType(ka);
				ka = new ColorType(0.0, 0.0, 0.0);
			} else {
				ambient = true;
				ka = new ColorType(katemp);
			}
			break;
		case 'G':
		case 'g':
			gouraud = true;
			flat = phong = false;
			drawTestCase();
			break;
		case 'F':
		case 'f':
			flat = true;
			gouraud = phong = false;
			drawTestCase();
			break;
		case 'P':
		case 'p':
			phong = true;
			flat = gouraud = false;
			drawTestCase();
			break;
		case 'T':
		case 't':
			testCase = (testCase + 1) % numTestCase;
			lightsInitialized = false;
			drawTestCase();
			break;
		case '<':
			Nsteps = Nsteps < 4 ? Nsteps : Nsteps / 2;
			System.out.printf("Nsteps = %d \n", Nsteps);
			drawTestCase();
			break;
		case '>':
			Nsteps = Nsteps > 190 ? Nsteps : Nsteps * 2;
			System.out.printf("Nsteps = %d \n", Nsteps);
			drawTestCase();
			break;
		case '+':
			ns++;
			drawTestCase();
			break;
		case '-':
			if (ns > 0)
				ns--;
			drawTestCase();
			break;
		case 'U':
			scale_l+=1;
			break;
		case 'u':
			scale_s-=1;
			break;
		case 'V':
		case 'v':
			objects_translated = !objects_translated;
			break;
		case 'X':
		case 'x':
			objects_rotated = !objects_rotated;
			break;
		case ',':
	    	global_x-=10;
	    	break;
	    case '.':
	    	global_y+=10;
	    	break;
	    case ';':
	    	global_y-=10;
	    	break;
	    case '/':
	    	global_x+=10;
	    	break;
	    case '4':
	    	ka = ka_t;
	    	if(kat<10)
	    		kat+=1;
	    	break;
	    case '5':
	    	ka = ka_t;
	    	if(kat>0)
	    		kat-=1;
	    	break;
	    case '6':
	    	kd = kd_t;
	    	if(kdt<10)
	    		kdt+=1;
	    	break;
	    case '7':
	    	kd = kd_t;
	    	if(kdt>0)
	    		kdt-=1;
	    	break;
	    case '8':
	    	ks = ks_t;
	    	if(kst<10)
	    		kst+=1;
	    	break;
	    case '9':
	    	ks = ks_t;
	    	if(kst>0)
	    		kst-=1;
	    	break;
	    		
	    		
		default:
			break;
		}
	}

	public void keyPressed(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			new Thread() {
				public void run() {
					animator.stop();
				}
			}.start();
			System.exit(0);
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent key) {
		// deliberately left blank
	}

	// **************************************************
	// MouseListener and MouseMotionListener Interfaces
	// **************************************************
	public void mouseClicked(MouseEvent mouse) {
		// deliberately left blank
	}

	public void mousePressed(MouseEvent mouse) {
		int button = mouse.getButton();
		if (button == MouseEvent.BUTTON1) {
			last_x = mouse.getX();
			last_y = mouse.getY();
			rotate_world = true;
		}
	}

	public void mouseReleased(MouseEvent mouse) {
		int button = mouse.getButton();
		if (button == MouseEvent.BUTTON1) {
			rotate_world = false;
		}
	}

	public void mouseMoved(MouseEvent mouse) {
		// Deliberately left blank
	}

	/**
	 * Updates the rotation quaternion as the mouse is dragged.
	 * 
	 * @param mouse
	 *            The mouse drag event object.
	 */
	public void mouseDragged(final MouseEvent mouse) {
		if (this.rotate_world) {
			// get the current position of the mouse
			final int x = mouse.getX();
			final int y = mouse.getY();

			// get the change in position from the previous one
			final int dx = x - this.last_x;
			final int dy = y - this.last_y;

			// create a unit vector in the direction of the vector (dy, dx, 0)
			final float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
			if (magnitude > 0.0001) {
				// define axis perpendicular to (dx,-dy,0)
				// use -y because origin is in upper lefthand corner of the
				// window
				final float[] axis = new float[] { -(float) (dy / magnitude),
						(float) (dx / magnitude), 0 };

				// calculate appropriate quaternion
				final float viewing_delta = 3.1415927f / 180.0f;
				final float s = (float) Math.sin(0.5f * viewing_delta);
				final float c = (float) Math.cos(0.5f * viewing_delta);
				final Quaternion Q = new Quaternion(c, s * axis[0],
						s * axis[1], s * axis[2]);
				this.viewing_quaternion = Q.multiply(this.viewing_quaternion);

				// normalize to counteract acccumulating round-off error
				this.viewing_quaternion.normalize();

				// save x, y as last x, y
				this.last_x = x;
				this.last_y = y;
				drawTestCase();
			}
		}

	}

	public void mouseEntered(MouseEvent mouse) {
		// Deliberately left blank
	}

	public void mouseExited(MouseEvent mouse) {
		// Deliberately left blank
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	// **************************************************
	// Test Cases
	// **************************************************

	void testOne() {

		//scaling of all the objects
		float radius;
		radius = (float) 50;
			 radius+=(float)scale_l;
			 radius+=(float)scale_s;
		if (objects_scaled)
			radius = (float) 30.0;
		Material mat = new Material(ka, kd, ks, ns);
		ka_t = new ColorType(0.1*kat,0.1*kat,0.1*kat);
		kd_t = new ColorType(0.1*kdt,0.1*kdt,0.1*kdt);
		//kd = kd_t;
		ks_t = new ColorType(0.1*kst,0.2*kst,0.2*kst);
		//ks = ks_t;
		float x;
		//objects translation
		if (objects_translated)
			x = 1.4f;
		else
			x = 1;
		
		
		Sphere3D sphere = new Sphere3D((float) 128.0/x, (float) 128.0, (float) 128.0,
				(float) 1.5 * radius, Nsteps, Nsteps, mat);
		Ellipsoid ellipsoid = new Ellipsoid((float)128.0*3/x, (float)128.0, (float)128.0, (float)1.5*radius, (float)2*radius, (float)radius, Nsteps, Nsteps, mat);
		Cylinder cylinder = new Cylinder(256.0f*2f/x, 384.0f, 128.0f, (float)1.5*radius, (float)1.5*radius, Nsteps, Nsteps, radius, mat);
		Torus3D torus = new Torus3D((float) 256.0/x, (float) 384.0, (float) 128.0,
				(float) 0.8 * radius, (float) 1.25 * radius, Nsteps, Nsteps, mat);
		Box box = new Box(128.0f*5/x, 128.0f, 128.0f, 1.5f*radius, Nsteps, Nsteps, mat);
		
		objects = new ArrayList<Shape>();
		
		objects.add(sphere);
		objects.add(ellipsoid);
		objects.add(cylinder);
		objects.add(torus);
		objects.add(box);
		
		//Rotation of the objects
		if (objects_rotated){
			ellipsoid.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), ellipsoid.getCenter());
			sphere.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), sphere.getCenter());
			cylinder.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), cylinder.getCenter());
			torus.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), torus.getCenter());
			Quaternion box_= new Quaternion((float).3, (float)0, (float)1, (float)0);
			box_.normalize();
		}

		// view vector is defined along z axis
		// this example assumes simple othorgraphic projection
		// view vector is used in
		// (a) calculating specular lighting contribution
		// (b) backface culling / backface rejection
		Vector3D view_vector = new Vector3D((float) 0.0, (float) 0.0,
				(float) 1.0);
		if (!lightsInitialized) {
			light = new Light();
			ColorType light_color = new ColorType(1.0, 1.0, 1.0);
			Vector3D light_direction = new Vector3D((float) 0.0,
					(float) (-1.0 / Math.sqrt(2.0)), (float) (1.0 / Math.sqrt(2.0)));
			InfiniteLight infLight = new InfiniteLight(light_color, light_direction);
			AmbientLight ambLight = new AmbientLight(light_color, light_direction);
			light.addLight(infLight);
			light.addLight(ambLight);
			lightsInitialized = true;
		}

		// a triangle mesh
		Mesh3D mesh;

		int n, m;
		
		DepthBuffer depthBuff = new DepthBuffer(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, buff);

		for (int k = 0; k < 5; ++k)
		{
			if (k == 0) {
				mesh = sphere.mesh;
				n = sphere.getN();
				m = sphere.getM();
				drawObject(mesh, sphere, n, m, view_vector, light, depthBuff);
			} else if (k == 1) {
				mesh = torus.mesh;
				n = torus.getN();
				m = torus.getM();
				drawObject(mesh, torus, n, m, view_vector, light, depthBuff);
			} else if (k == 2) {
				mesh = ellipsoid.mesh;
				n = ellipsoid.getN();
				m = ellipsoid.getM();
				drawObject(mesh, ellipsoid, n, m, view_vector, light, depthBuff);
			} else if (k == 3) {
				mesh = cylinder.mesh;
				n = cylinder.getN();
				m = cylinder.getM();
				drawObject(mesh, cylinder, n, m, view_vector, light, depthBuff);
			} else {
				n = box.getN();
				m = box.getM();
				for (Mesh3D boxMesh : box.mesh) {
					drawObject(boxMesh, box, n, m, view_vector, light, depthBuff);
				}
			}
		}
	}
	
	private ColorType ColorType(int ka_t2, int ka_t3, int ka_t4) {
		// TODO Auto-generated method stub
		return null;
	}

	void testTwo() {
		
		//scaling of the objects
		float radius;
		radius = (float) 50;
		 radius+=(float)scale_l;
		 radius+=(float)scale_s;
	if (objects_scaled)
		radius = (float) 30.0;
		
		Material mat = new Material(ka, kd, ks, ns);
		ka_t = new ColorType(0.1*kat,0.1*kat,0.1*kat);
		ka = ka_t;
		//translation of objects;
		float x;
		if (objects_translated)
			x = 1.5f;
		else
			x = 1;
		
		Sphere3D sphere = new Sphere3D((float) 128.0/x, (float) 128.0, (float) 128.0,
				(float) 1.5 * radius, Nsteps, Nsteps, mat);	
		//Sphere.mesh.rotateMesh 
		Ellipsoid ellipsoid = new Ellipsoid((float)128.0*3/x, (float)128.0, (float)128.0, (float)1.5*radius, 
				(float)2*radius, (float)radius, Nsteps, Nsteps, mat);
		Torus3D torus = new Torus3D((float) 256.0/x, (float) 384.0, (float) 128.0,
				(float) 0.8 * radius, (float) 1.25 * radius, Nsteps, Nsteps, mat);
		objects = new ArrayList<Shape>();
		objects.add(ellipsoid);
		objects.add(torus);
		objects.add(sphere);
		
		//rotation of objects;
		if (objects_rotated){
			ellipsoid.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), ellipsoid.getCenter());
			sphere.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), sphere.getCenter());
			torus.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), torus.getCenter());
		}
		// view vector is defined along z axis
		// this example assumes simple othorgraphic projection
		// view vector is used in
		// (a) calculating specular lighting contribution
		// (b) backface culling / backface rejection
		Vector3D view_vector = new Vector3D((float) 0.0, (float) 0.0,
				(float) 1.0);
		if (!lightsInitialized) {
			light = new Light();
			ColorType light_color = new ColorType(1.0, 1.0, 1.0);
			Vector3D light_direction = new Vector3D((float) 0.0,
					(float) -1.0f, (float) 1f);
			Vector3D light_position = new Vector3D(0.0f, 0f, 500f);
			PointLight pLight = new PointLight(light_color, light_direction, light_position);
			pLight.toggleAngular();
			pLight.toggleRadial();
			AmbientLight ambLight = new AmbientLight(light_color, light_direction);
			light.addLight(pLight);
			light.addLight(ambLight);
			lightsInitialized = true;
		}

		// a triangle mesh
		Mesh3D mesh;

		int n, m;
		
		DepthBuffer depthBuff = new DepthBuffer(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, buff);
		for (int k = 0; k < 2; ++k)
		{
			if (k == 0) {
				mesh = ellipsoid.mesh;
				n = ellipsoid.getN();
				m = ellipsoid.getM();
				drawObject(mesh, ellipsoid, n, m, view_vector, light, depthBuff);
			} else if (k == 1) {
				mesh = torus.mesh;
				n = torus.getN();
				m = torus.getM();
				drawObject(mesh, torus, n, m, view_vector, light, depthBuff);
			} else {
				mesh = sphere.mesh;
				n = sphere.getN();
				m = sphere.getM();
				drawObject(mesh, sphere, n, m, view_vector, light, depthBuff);
			}
		}
		mesh = sphere.mesh;
		n = sphere.getN();
		m = sphere.getM();
		drawObject(mesh, sphere, n, m, view_vector, light, depthBuff);
	}
	
	void testThree() {
		
		//scaling of the objects;
		float radius;
		radius = (float) 50;
		 radius+=(float)scale_l;
		 radius+=(float)scale_s;
	if (objects_scaled)
		radius = (float) 30.0;
		
		Material mat = new Material(ka, kd, ks, ns);
		//tranlation of objects;
		ka_t = new ColorType(0.1*kat,0.1*kat,0.1*kat);
		ka = ka_t;
		float x;
		if (objects_translated)
			x = 1.8f;
		else
			x = 1;
		
		Sphere3D sphere = new Sphere3D((float) 512.0/x, (float) 256.0, (float) 128.0,
				(float) 3 * radius, Nsteps, Nsteps, mat);
		Box box1 = new Box(256.0f/x, 50f, 128.0f, radius, Nsteps, Nsteps, mat);
		Box box2 = new Box(768.0f/x, 50f, 128.0f, radius, Nsteps, Nsteps, mat);
		Cylinder cylinder = new Cylinder(256.0f/x, 400f, 128f, radius, radius, Nsteps, Nsteps, radius*2.0f, mat);
		objects = new ArrayList<Shape>();
		
		objects.add(sphere);
		objects.add(box1);
		objects.add(box2);
		objects.add(cylinder);
		
		//rotation of objects;
		if (objects_rotated){
			sphere.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), sphere.getCenter());
			cylinder.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), cylinder.getCenter());
		//	box.mesh.rotateMesh();
        //	box.mesh.rotateMesh(box, box.getCenter());
		}
		// view vector is defined along z axis
		// this example assumes simple othorgraphic projection
		// view vector is used in
		// (a) calculating specular lighting contribution
		// (b) backface culling / backface rejection
		Vector3D view_vector = new Vector3D((float) 0.0, (float) 0.0,
				(float) 1.0);
		if (!lightsInitialized) {
			light = new Light();
			ColorType light_color = new ColorType(.6f, .6f, .6f);
			Vector3D light_direction = new Vector3D((float) 0.0,
					(float) -1.0f, (float) 1f);
			Vector3D light_position = new Vector3D(0.0f, 700.0f, 500f);
			InfiniteLight infLight = new InfiniteLight(light_color, light_direction);
			AmbientLight ambLight = new AmbientLight(light_color, light_direction);
			light_color = new ColorType(1.0f, 0.0f, 0.0f);
			light_direction = new Vector3D((float) -1.0,
					(float) 1.0f, (float) 1f);
			PointLight pLight = new PointLight(light_color, light_direction, light_position);
			light.addLight(pLight);
			light.addLight(infLight);
			light.addLight(ambLight);
			lightsInitialized = true;
		}

		// a triangle mesh
		Mesh3D mesh;

		int n, m;
		
		DepthBuffer depthBuff = new DepthBuffer(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, buff);
		for (int k = 0; k < 4; ++k)
		{
			if (k == 0) {
				mesh = sphere.mesh;
				n = sphere.getN();
				m = sphere.getM();
				drawObject(mesh, sphere, n, m, view_vector, light, depthBuff);
			} else if (k == 1) {
				n = box1.getN();
				m = box1.getM();
				for (Mesh3D boxMesh : box1.mesh) {
					drawObject(boxMesh, box1, n, m, view_vector, light, depthBuff);
				}
			} else if (k == 2) {
				n = box2.getN();
				m = box2.getM();
				for (Mesh3D boxMesh : box2.mesh) {
					drawObject(boxMesh, box2, n, m, view_vector, light, depthBuff);
				}
			} else {
				mesh = cylinder.mesh;
				n = cylinder.getN();
				m = cylinder.getM();
				drawObject(mesh, cylinder, n, m, view_vector, light, depthBuff);
			}
		}
	}
	
	void testFour() {
		//scaling of objects;
		float radius;
		radius = (float) 50;
		 radius+=(float)scale_l;
		 radius+=(float)scale_s;
	if (objects_scaled)
		radius = (float) 30.0;
		
		Material mat = new Material(ka, kd, ks, ns);
		//translation of objects;
		ka_t = new ColorType(0.1*kat,0.1*kat,0.1*kat);
		ka = ka_t;
		float x;
		if (objects_translated)
			x = 1.4f;
		else
			x = 1;
		
		Ellipsoid ellipsoid = new Ellipsoid((float) 512.0/x, (float) 256.0, (float) 128.0,
				(float) 3 * radius, radius, radius, Nsteps, Nsteps, mat);
		Torus3D torus = new Torus3D(200f/x, 256.0f, 300f, radius, radius*2f, Nsteps, Nsteps, mat);
		Cylinder cylinder = new Cylinder(768.0f/x, 256.0f, 128f, radius, radius, Nsteps, Nsteps, radius*2.0f, mat);
		objects = new ArrayList<Shape>();
		
		objects.add(ellipsoid);
		objects.add(torus);
		objects.add(cylinder);
		
		//rotation of objects;
		if (objects_rotated){
			ellipsoid.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), ellipsoid.getCenter());
			cylinder.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), cylinder.getCenter());
			torus.mesh.rotateMesh(new Quaternion((float).3, (float)0, (float)1, (float)0), torus.getCenter());
		}
		

		// view vector is defined along z axis
		// this example assumes simple othorgraphic projection
		// view vector is used in
		// (a) calculating specular lighting contribution
		// (b) backface culling / backface rejection
		Vector3D view_vector = new Vector3D((float) 0.0, (float) 0.0,
				(float) 1.0);
		if (!lightsInitialized) {
			light = new Light();
			ColorType light_color = new ColorType(.6f, .6f, .6f);
			Vector3D light_direction = new Vector3D((float) 0.0,
					(float) -1.0f, (float) 1f);
			Vector3D light_position = new Vector3D(0.0f, 0.0f, 1000f);
			InfiniteLight infLight = new InfiniteLight(light_color, light_direction);
			AmbientLight ambLight = new AmbientLight(light_color, light_direction);
			light_direction = new Vector3D((float) 1.0,
					(float) -1.0f, (float) 1f);
			PointLight pLight = new PointLight(light_color, light_direction, light_position);
			pLight.toggleRadial();
			light.addLight(pLight);
			light.addLight(infLight);
			light.addLight(ambLight);
			lightsInitialized = true;
		}

		// a triangle mesh
		Mesh3D mesh;

		int n, m;
		
		DepthBuffer depthBuff = new DepthBuffer(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, buff);
		for (int k = 0; k < 3; ++k)
		{
			if (k == 0) {
				mesh = ellipsoid.mesh;
				n = ellipsoid.getN();
				m = ellipsoid.getM();
				drawObject(mesh, ellipsoid, n, m, view_vector, light, depthBuff);
			} else if (k == 1) {
				mesh = torus.mesh;
				n = torus.getN();
				m = torus.getM();
				drawObject(mesh, torus, n, m, view_vector, light, depthBuff);
			} else {
				mesh = cylinder.mesh;
				n = cylinder.getN();
				m = cylinder.getM();
				drawObject(mesh, cylinder, n, m, view_vector, light, depthBuff);
			}
		}
	}
	
	
	private void drawObject(Mesh3D mesh, Shape obj, int n, int m, Vector3D view_vector, Light light, DepthBuffer depthBuff) {
		int i, j;
		// temporary variables for triangle 3D vertices and 3D normals
		Vector3D v0, v1, v2, n0, n1, n2;

		// projected triangle, with vertex colors
		Point3D[] tri = { new Point3D(), new Point3D(), new Point3D() };
		
		Vector3D point = new Vector3D();
		
		// normal to the plane of a triangle
		// to be used in backface culling / backface rejection
		Vector3D triangle_normal = new Vector3D();
		
		// rotate the surface's 3D mesh using quaternion
		mesh.rotateMesh(viewing_quaternion, viewing_center);
		
		for (i = 0; i < m - 1; ++i) {
			for (j = 0; j < n - 1; ++j) {
				v0 = mesh.v[i][j];
				v1 = mesh.v[i][j + 1];
				v2 = mesh.v[i + 1][j + 1];
				triangle_normal = computeTriangleNormal(v0, v1, v2);

				if (view_vector.dotProduct(triangle_normal) > 0.0) // front-facing
																	// triangle?
				{
					if (phong) {
						n0 = mesh.n[i][j];
						n1 = mesh.n[i][j + 1];
						n2 = mesh.n[i + 1][j + 1];
					}
					else if (gouraud) {
						// vertex colors for Gouraud shading
						n0 = mesh.n[i][j];
						n1 = mesh.n[i][j + 1];
						n2 = mesh.n[i + 1][j + 1];
						tri[0].c = light.impLight(obj.mat, view_vector,
								n0, v0);
						tri[1].c = light.impLight(obj.mat, view_vector,
								n1, v1);
						tri[2].c = light.impLight(obj.mat, view_vector,
								n2, v2);
						
					} else {
						// flat shading: use the normal to the triangle
						// itself
						n2 = n1 = n0 = triangle_normal;
						point = new Vector3D((v0.x + v1.x + v2.x)/3, 
								(v0.y + v1.y + v2.y)/3, 
								(v0.z + v1.z + v2.z)/3);
						tri[2].c = tri[1].c = tri[0].c = light.impLight(
								obj.mat, view_vector, triangle_normal,
								point);
					}

					tri[0].x = (int) v0.x+global_x;
					tri[0].y = (int) v0.y+global_y;
					tri[0].z = (int) v0.z;
					tri[1].x = (int) v1.x+global_x;
					tri[1].y = (int) v1.y+global_y;
					tri[1].z = (int) v1.z;
					tri[2].x = (int) v2.x+global_x;
					tri[2].y = (int) v2.y+global_y;
					tri[2].z = (int) v2.z;

					if (phong) {
						Triangle.drawTriangleWithPhong(buff, depthBuff, tri[0], tri[1], tri[2], n0, n1, n2, light, obj.mat, view_vector);
					} else {
						Triangle.drawTriangle(buff, depthBuff, tri[0], tri[1], tri[2], gouraud);
					}
				}

				v0 = mesh.v[i][j];
				v1 = mesh.v[i + 1][j + 1];
				v2 = mesh.v[i + 1][j];
				triangle_normal = computeTriangleNormal(v0, v1, v2);

				if (view_vector.dotProduct(triangle_normal) > 0.0) // front-facing
																	// triangle?
				{
					if (phong) {
						n0 = mesh.n[i][j];
						n1 = mesh.n[i + 1][j + 1];
						n2 = mesh.n[i + 1][j];
					}
					else if (gouraud) {
						// vertex colors for Gouraud shading
						n0 = mesh.n[i][j];
						n1 = mesh.n[i + 1][j + 1];
						n2 = mesh.n[i + 1][j];
						tri[0].c = light.impLight(obj.mat, view_vector,
								n0, v0);
						tri[1].c = light.impLight(obj.mat, view_vector,
								n1, v1);
						tri[2].c = light.impLight(obj.mat, view_vector,
								n2, v2);
					} else {
						// flat shading: use the normal to the triangle
						// itself
						n2 = n1 = n0 = triangle_normal;
						point = new Vector3D((v0.x + v1.x + v2.x)/3, 
								(v0.y + v1.y + v2.y)/3, 
								(v0.z + v1.z + v2.z)/3);
						tri[2].c = tri[1].c = tri[0].c = light.impLight(
								obj.mat, view_vector, triangle_normal,
								point);
					}

					tri[0].x = (int) v0.x+global_x;
					tri[0].y = (int) v0.y+global_y;
					tri[0].z = (int) v0.z;
					tri[1].x = (int) v1.x+global_x;
					tri[1].y = (int) v1.y+global_y;
					tri[1].z = (int) v1.z;
					tri[2].x = (int) v2.x+global_x;
					tri[2].y = (int) v2.y+global_y;
					tri[2].z = (int) v2.z;

					if (phong) {
						Triangle.drawTriangleWithPhong(buff, depthBuff, tri[0], tri[1], tri[2], n0, n1, n2, light, obj.mat, view_vector);
					} else {
						Triangle.drawTriangle(buff, depthBuff, tri[0], tri[1], tri[2], gouraud);
					}
				}
			}
		}
	}
	// helper method that computes the unit normal to the plane of the triangle
	// degenerate triangles yield normal that is numerically zero
	private Vector3D computeTriangleNormal(Vector3D v0, Vector3D v1, Vector3D v2) {
		Vector3D e0 = v1.minus(v2);
		Vector3D e1 = v0.minus(v2);
		Vector3D norm = e0.crossProduct(e1);

		if (norm.magnitude() > 0.000001)
			norm.normalize();
		else
			// detect degenerate triangle and set its normal to zero
			norm.set((float) 0.0, (float) 0.0, (float) 0.0);

		return norm;
	}

}