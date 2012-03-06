package engine.classes;

public class Cube {
	public float x1, y1, z1, width, height, depth;
	
	public Cube (float X1, float widthX, float Y1, float heightX, float Z1, float depthX) {
		x1 = X1;
		width = widthX;
		y1 = Y1;
		height = heightX;
		z1 = Z1;
		depth = depthX;
	}
	
	public boolean intersects(Cube otherCube) {
		
		//If either line is inside each other for X
		boolean XIntersect = (
				( (x1 >= otherCube.x1) && (x1 <= otherCube.x1 + otherCube.width) ) || ( (x1+width >= otherCube.x1) && (x1+width <= otherCube.x1 + otherCube.width) )
				||
				( (otherCube.x1 >= x1) && (otherCube.x1 <= x1 + width) ) || ( (otherCube.x1+otherCube.width >= x1) && (otherCube.x1+otherCube.width <= x1 + width) )
				);
		
		boolean YIntersect = (
				( (y1 >= otherCube.y1) && (y1 <= otherCube.y1 + otherCube.height) ) || ( (y1+height >= otherCube.y1) && (y1+height <= otherCube.y1 + otherCube.height) )
				||
				( (otherCube.y1 >= y1) && (otherCube.y1 <= y1 + height) ) || ( (otherCube.y1+otherCube.height >= y1) && (otherCube.y1+otherCube.height <= y1 + height) )
				);
		
		
		boolean ZIntersect = (
				( (z1 >= otherCube.z1) && (z1 <= otherCube.z1 + otherCube.depth) ) || ( (z1+depth >= otherCube.z1) && (z1+depth <= otherCube.z1 + otherCube.depth) )
				||
				( (otherCube.z1 >= z1) && (otherCube.z1 <= z1 + depth) ) || ( (otherCube.z1+otherCube.depth >= z1) && (otherCube.z1+otherCube.depth <= z1 + depth) )
				);
		
		return XIntersect && YIntersect && ZIntersect;
	}
	
}