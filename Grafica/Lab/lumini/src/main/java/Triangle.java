/*
 * Triangle.java
 * Name: Jiazi Cai
 * Email: jiazi@bu.edu
 * 
 * Based on the code provided from lab9 and extended some functions
 * to support floating point by using DDA algorithm, and can now 
 * do normal interpolation for Phong shading. 
 */
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.lang.Math;

public class Triangle {
	public Triangle() {
		// deliberately left blank
	}

	// draw a point
	public static void drawPoint(BufferedImage buff, Point3D p) {
		buff.setRGB(p.x, buff.getHeight() - p.y - 1, p.c.getBRGUint8());
	}

	// ////////////////////////////////////////////////
	// Implement the following two functions
	// ////////////////////////////////////////////////

	// Use floating point arithmetic: DDA algorithm
	
	// draw a line segment
	public static void drawLine(BufferedImage buff, DepthBuffer depthBuff,
			Point3D p1, Point3D p2) {
		float dx = p2.x - p1.x;
		float dy = p2.y - p1.y;
		float dz = p2.z - p1.z;
		float dr = p2.c.r * 255.0f - p1.c.r * 255.0f;
		float dg = p2.c.g * 255.0f - p1.c.g * 255.0f;
		float db = p2.c.b * 255.0f - p1.c.b * 255.0f;

		float steps;

		if (Math.abs(dx) > Math.abs(dy)) {
			steps = Math.abs(dx);
		} else {
			steps = Math.abs(dy);
		}

		float xInc = dx / steps;
		float yInc = dy / steps;
		float zInc = dz / steps;

		Point3D pk = new Point3D(p1.x, p1.y, p1.z, p1.c);

		float slope_r, slope_g, slope_b;

		slope_r = dr / steps;
		slope_g = dg / steps;
		slope_b = db / steps;

		float red = pk.c.r * 255.0f;
		float green = pk.c.g * 255.0f;
		float blue = pk.c.b * 255.0f;

		if (pk.y >= 0 && pk.y < buff.getHeight() && pk.x >= 0
				&& pk.x < buff.getWidth()
				&& pk.z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
			depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
			drawPoint(buff, pk);
		}

		float x = pk.x, y = pk.y, z = pk.z;

		if (p1.x == p2.x) { // Line is vertical
			for (int k = 0; k < steps; k++) {
				y += yInc;
				z += zInc;

				pk.y = Math.round(y);
				pk.z = Math.round(z);

				red += slope_r;
				green += slope_g;
				blue += slope_b;

				int rgb = (int) ((Math.round(red) << 16)
						| (Math.round(green) << 8) | Math.round(blue));
				if (y >= 0 && y < buff.getHeight() && x >= 0
						&& x < buff.getWidth()
						&& z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
					depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
					buff.setRGB(pk.x, buff.getHeight() - pk.y - 1, rgb);
				}

			}
		} else {
			for (int k = 0; k < steps; k++) {
				x += xInc;
				y += yInc;
				z += zInc;

				pk.x = Math.round(x);
				pk.y = Math.round(y);
				pk.z = Math.round(z);

				red += slope_r;
				green += slope_g;
				blue += slope_b;

				int rgb = (int) ((Math.round(red) << 16)
						| (Math.round(green) << 8) | Math.round(blue));

				if (y >= 0 && y < buff.getHeight() && x >= 0
						&& x < buff.getWidth()
						&& z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
					depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
					buff.setRGB(pk.x, buff.getHeight() - pk.y - 1, rgb);
				}

			}
		}

	}

	// draw a triangle
	// either smooth or flat fill
	// fill the right-hand part and left-hand part of the triangle
	//
	// *
	// /|\
	// / | \
	// / | \
	// *---|---*
	// left-hand right-hand
	// part part
	//
	// this routine fills columns of pixels within the left-hand part, and then
	// the right-hand part
	//

	public static void drawTriangle(BufferedImage buff, DepthBuffer depthBuff,
			Point3D p1, Point3D p2, Point3D p3, boolean do_smooth) {
		// sort the triangle vertices by ascending x value
		Point3D p[] = sortTriangleVerts(p1, p2, p3);
		int x;
		float y_a, y_b;
		float z_a, z_b;

		Point3D side_a = new Point3D(p[0]), side_b = new Point3D(p[0]);
		if (!do_smooth) {
			side_a.c = new ColorType(p1.c);
			side_b.c = new ColorType(p1.c);
		}

		y_b = p[0].y;
		z_b = p[0].z;
		float dy_b = ((float) (p[2].y - p[0].y)) / (p[2].x - p[0].x);
		float dz_b = ((float) (p[2].z - p[0].z)) / (p[2].x - p[0].x);

		float dy_a, dz_a, dr_a = 0, dg_a = 0, db_a = 0, dr_b = 0, dg_b = 0, db_b = 0;
		if (do_smooth) {
			// calculate slopes in r, g, b for segment b
			dr_b = ((float) (p[2].c.r - p[0].c.r)) / (p[2].x - p[0].x);
			dg_b = ((float) (p[2].c.g - p[0].c.g)) / (p[2].x - p[0].x);
			db_b = ((float) (p[2].c.b - p[0].c.b)) / (p[2].x - p[0].x);
		}

		// if there is a right-hand part to the triangle then fill it
		if (p[0].x != p[1].x) {
			y_a = p[0].y;
			z_a = p[0].z;
			dy_a = ((float) (p[1].y - p[0].y)) / (p[1].x - p[0].x);
			dz_a = ((float) (p[1].z - p[0].z)) / (p[1].x - p[0].x);

			if (do_smooth) {
				// calculate slopes in r, g, b for segment a
				dr_a = ((float) (p[1].c.r - p[0].c.r)) / (p[1].x - p[0].x);
				dg_a = ((float) (p[1].c.g - p[0].c.g)) / (p[1].x - p[0].x);
				db_a = ((float) (p[1].c.b - p[0].c.b)) / (p[1].x - p[0].x);
			}

			// loop over the columns for right-hand part of triangle
			// filling from side a to side b of the span
			for (x = p[0].x; x < p[1].x; ++x) {
				drawLine(buff, depthBuff, side_a, side_b);

				++side_a.x;
				++side_b.x;
				y_a += dy_a;
				y_b += dy_b;
				z_a += dz_a;
				z_b += dz_b;
				side_a.y = (int) y_a;
				side_b.y = (int) y_b;
				side_a.z = (int) z_a;
				side_b.z = (int) z_b;
				if (do_smooth) {
					side_a.c.r += dr_a;
					side_b.c.r += dr_b;
					side_a.c.g += dg_a;
					side_b.c.g += dg_b;
					side_a.c.b += db_a;
					side_b.c.b += db_b;
				}
			}
		}

		// there is no left-hand part of triangle
		if (p[1].x == p[2].x)
			return;

		// set up to fill the left-hand part of triangle
		// replace segment a
		side_a = new Point3D(p[1]);
		if (!do_smooth)
			side_a.c = new ColorType(p1.c);

		y_a = p[1].y;
		z_a = p[1].z;
		dy_a = ((float) (p[2].y - p[1].y)) / (p[2].x - p[1].x);
		dz_a = ((float) (p[2].z - p[1].z)) / (p[2].x - p[1].x);
		if (do_smooth) {
			// calculate slopes in r, g, b for replacement for segment a
			dr_a = ((float) (p[2].c.r - p[1].c.r)) / (p[2].x - p[1].x);
			dg_a = ((float) (p[2].c.g - p[1].c.g)) / (p[2].x - p[1].x);
			db_a = ((float) (p[2].c.b - p[1].c.b)) / (p[2].x - p[1].x);
		}

		// loop over the columns for left-hand part of triangle
		// filling from side a to side b of the span
		for (x = p[1].x; x <= p[2].x; ++x) {
			drawLine(buff, depthBuff, side_a, side_b);

			++side_a.x;
			++side_b.x;
			y_a += dy_a;
			y_b += dy_b;
			z_a += dz_a;
			z_b += dz_b;
			side_a.y = (int) y_a;
			side_b.y = (int) y_b;
			side_a.z = (int) z_a;
			side_b.z = (int) z_b;
			if (do_smooth) {
				side_a.c.r += dr_a;
				side_b.c.r += dr_b;
				side_a.c.g += dg_a;
				side_b.c.g += dg_b;
				side_a.c.b += db_a;
				side_b.c.b += db_b;
			}
		}
	}
	
	public static void drawLineWithPhong(BufferedImage buff, DepthBuffer depthBuff,
			Point3D p1, Point3D p2, Light light, Material mat, Vector3D v) {
		float dx = p2.x - p1.x;
		float dy = p2.y - p1.y;
		float dz = p2.z - p1.z;
		float dnx = p2.n.x - p1.n.x;
		float dny = p2.n.y - p1.n.y;
		float dnz = p2.n.z - p1.n.z;

		float steps;

		if (Math.abs(dx) > Math.abs(dy)) {
			steps = Math.abs(dx);
		} else {
			steps = Math.abs(dy);
		}

		float xInc = dx / steps;
		float yInc = dy / steps;
		float zInc = dz / steps;

		Point3D pk = new Point3D(p1.x, p1.y, p1.z, p1.c);
		pk.n = new Vector3D(p1.n);

		float slope_nx, slope_ny, slope_nz;

		slope_nx = dnx / steps;
		slope_ny = dny / steps;
		slope_nz = dnz / steps;
		
		Vector3D new_n = new Vector3D(pk.n);
		pk.c = light.impLight(mat, v, new_n, pk.toVector());

		if (pk.y >= 0 && pk.y < buff.getHeight() && pk.x >= 0
				&& pk.x < buff.getWidth()
				&& pk.z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
			depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
			drawPoint(buff, pk);
		}

		float x = pk.x, y = pk.y, z = pk.z;

		if (p1.x == p2.x) { // Line is vertical
			for (int k = 0; k < steps; k++) {
				y += yInc;
				z += zInc;

				pk.y = Math.round(y);
				pk.z = Math.round(z);

				new_n.x += slope_nx;
				new_n.y += slope_ny;
				new_n.z += slope_nz;
				
				ColorType result = light.impLight(mat, v, new_n, pk.toVector());

				int rgb = (int) ((Math.round(result.getRUint8()) << 16)
						| (Math.round(result.getGUint8()) << 8) | Math.round(result.getBUint8()));
				if (y >= 0 && y < buff.getHeight() && x >= 0
						&& x < buff.getWidth()
						&& z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
					depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
					buff.setRGB(pk.x, buff.getHeight() - pk.y - 1, rgb);
				}

			}
		} else {
			for (int k = 0; k < steps; k++) {
				x += xInc;
				y += yInc;
				z += zInc;

				pk.x = Math.round(x);
				pk.y = Math.round(y);
				pk.z = Math.round(z);

				new_n.x += slope_nx;
				new_n.y += slope_ny;
				new_n.z += slope_nz;
				
				ColorType result = light.impLight(mat, v, new_n, pk.toVector());

				int rgb = (int) ((Math.round(result.getRUint8()) << 16)
						| (Math.round(result.getGUint8()) << 8) | Math.round(result.getBUint8()));

				if (y >= 0 && y < buff.getHeight() && x >= 0
						&& x < buff.getWidth()
						&& z > depthBuff.depthBuff[(int) pk.x][(int) pk.y]) {
					depthBuff.depthBuff[(int) pk.x][(int) pk.y] = pk.z;
					buff.setRGB(pk.x, buff.getHeight() - pk.y - 1, rgb);
				}

			}
		}

	}
	
	public static void drawTriangleWithPhong(BufferedImage buff,
			DepthBuffer depthBuff, Point3D p1, Point3D p2, Point3D p3,
			Vector3D n1, Vector3D n2, Vector3D n3, Light light, Material mat, Vector3D v) {
		// sort the triangle vertices by ascending x value
		p1.n = n1;
		p2.n = n2;
		p3.n = n3;
		Point3D p[] = sortTriangleVerts(p1, p2, p3);
		int x;
		float y_a, y_b;
		float z_a, z_b;

		Point3D side_a = new Point3D(p[0]), side_b = new Point3D(p[0]);
		side_a.n = new Vector3D(p[0].n);
		side_b.n = new Vector3D(p[0].n);
		side_a.c = new ColorType(p1.c);
		side_b.c = new ColorType(p1.c);

		y_b = p[0].y;
		z_b = p[0].z;
		float dy_b = ((float) (p[2].y - p[0].y)) / (p[2].x - p[0].x);
		float dz_b = ((float) (p[2].z - p[0].z)) / (p[2].x - p[0].x);

		float dy_a, dz_a, dnx_a = 0, dny_a = 0, dnz_a = 0, dnx_b = 0, dny_b = 0, dnz_b = 0;
		// calculate slopes in r, g, b for segment b
		dnx_b = ((float) (p[2].n.x - p[0].n.x)) / (p[2].x - p[0].x);
		dny_b = ((float) (p[2].n.y - p[0].n.y)) / (p[2].x - p[0].x);
		dnz_b = ((float) (p[2].n.z - p[0].n.z)) / (p[2].x - p[0].x);

		// if there is a right-hand part to the triangle then fill it
		if (p[0].x != p[1].x) {
			y_a = p[0].y;
			z_a = p[0].z;
			dy_a = ((float) (p[1].y - p[0].y)) / (p[1].x - p[0].x);
			dz_a = ((float) (p[1].z - p[0].z)) / (p[1].x - p[0].x);

			dnx_a = ((float) (p[1].n.x - p[0].n.x)) / (p[1].x - p[0].x);
			dny_a = ((float) (p[1].n.y - p[0].n.y)) / (p[1].x - p[0].x);
			dnz_a = ((float) (p[1].n.z - p[0].n.z)) / (p[1].x - p[0].x);

			// loop over the columns for right-hand part of triangle
			// filling from side a to side b of the span
			for (x = p[0].x; x < p[1].x; ++x) {
				drawLineWithPhong(buff, depthBuff, side_a, side_b, light, mat, v);

				++side_a.x;
				++side_b.x;
				y_a += dy_a;
				y_b += dy_b;
				z_a += dz_a;
				z_b += dz_b;
				side_a.y = (int) y_a;
				side_b.y = (int) y_b;
				side_a.z = (int) z_a;
				side_b.z = (int) z_b;
				
				// Phong normal interpolation
				side_a.n.x += dnx_a;
				side_b.n.x += dnx_b;
				side_a.n.y += dny_a;
				side_b.n.y += dny_b;
				side_a.n.z += dnz_a;
				side_b.n.z += dnz_b;
				
				side_a.c = light.impLight(mat, v, side_a.n, side_a.toVector());
				side_b.c = light.impLight(mat, v, side_b.n, side_b.toVector());
			}
		}

		// there is no left-hand part of triangle
		if (p[1].x == p[2].x)
			return;

		// set up to fill the left-hand part of triangle
		// replace segment a
		side_a = new Point3D(p[1]);
		side_a.c = new ColorType(p1.c);
		side_a.n = new Vector3D(p[1].n);

		y_a = p[1].y;
		z_a = p[1].z;
		dy_a = ((float) (p[2].y - p[1].y)) / (p[2].x - p[1].x);
		dz_a = ((float) (p[2].z - p[1].z)) / (p[2].x - p[1].x);
		// calculate slopes in r, g, b for replacement for segment a
		dnx_a = ((float) (p[2].n.x - p[1].n.x)) / (p[2].x - p[1].x);
		dny_a = ((float) (p[2].n.y - p[1].n.y)) / (p[2].x - p[1].x);
		dnz_a = ((float) (p[2].n.z - p[1].n.z)) / (p[2].x - p[1].x);

		// loop over the columns for left-hand part of triangle
		// filling from side a to side b of the span
		for (x = p[1].x; x <= p[2].x; ++x) {
			drawLineWithPhong(buff, depthBuff, side_a, side_b, light, mat, v);

			++side_a.x;
			++side_b.x;
			y_a += dy_a;
			y_b += dy_b;
			z_a += dz_a;
			z_b += dz_b;
			side_a.y = (int) y_a;
			side_b.y = (int) y_b;
			side_a.z = (int) z_a;
			side_b.z = (int) z_b;
			
			// Phong normal interpolation
			side_a.n.x += dnx_a;
			side_b.n.x += dnx_b;
			side_a.n.y += dny_a;
			side_b.n.y += dny_b;
			side_a.n.z += dnz_a;
			side_b.n.z += dnz_b;
			
			side_a.c = light.impLight(mat, v, side_a.n, side_a.toVector());
			side_b.c = light.impLight(mat, v, side_b.n, side_b.toVector());
		}
	}

	// helper function to bubble sort triangle vertices by x value
	private static Point3D[] sortTriangleVerts(Point3D p1, Point3D p2,
			Point3D p3) {
		Point3D pts[] = { p1, p2, p3 };
		Point3D tmp;
		int j = 0;
		boolean swapped = true;

		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < 3 - j; i++) {
				if (pts[i].x > pts[i + 1].x) {
					tmp = pts[i];
					pts[i] = pts[i + 1];
					pts[i + 1] = tmp;
					swapped = true;
				}
			}
		}
		return (pts);
	}
}