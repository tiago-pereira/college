
public class Matriz3x3 {
	float mat[][];
	
	public Matriz3x3() {
		mat = new float[3][3];
		setIdentidade();
	}
	
	public void setIdentidade(){
		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = 0;
		
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
	}
	
	public void setTranslate(float a, float b){
		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = a;
		
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = b;
		
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
	}
	
	public void setRotate(float theta){
		mat[0][0] = (float)Math.cos(theta);
		mat[0][1] = (float)Math.sin(theta);
		mat[0][2] = 0;
		
		mat[1][0] = -(float)Math.sin(theta);
		mat[1][1] = (float)Math.cos(theta);
		mat[1][2] = 0;
		
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
	}	
	
	public void multiplyVector(Vector2D p){
		float x = p.x;
		float y = p.y;
		float w = p.w;
		p.x = mat[0][0]*x + mat[0][1]*y + mat[0][2]*w;
		p.y = mat[1][0]*x + mat[1][1]*y + mat[1][2]*w;
		p.w = mat[2][0]*x + mat[2][1]*y + mat[2][2]*w;
	}
}
