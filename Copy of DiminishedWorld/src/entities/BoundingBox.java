package entities;

import org.lwjgl.util.vector.Vector3f;

public class BoundingBox {

	public Vector3f p1;
	private Vector3f p2;
	
	public BoundingBox(Vector3f p1, Vector3f p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public BoundingBox(Vector3f p1, float width, float height, float depth){
		this.p1 = p1;
		this.p2 = new Vector3f(p1.x + width, p1.y + height, p1.z+depth);
	}
	
	public boolean contains(Vector3f p){
		if(p == null){
			return false;
		}
		if((p.x > p1.x && p.x < p2.x) || (p.x < p1.x && p.x > p2.x)){
			if((p.y > p1.y && p.y < p2.y) || (p.y < p1.y && p.y > p2.y)){
				if((p.z > p1.z && p.z < p2.z) || (p.z < p1.z && p.z > p2.z)){
					return true;
				}	
			}
		}
		return false;
	}
}
