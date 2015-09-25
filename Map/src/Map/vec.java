package Map;

public class vec {
	public double x;
	public double y;
	public vec(double a,double b){
		x=a; y=b;
	}
	public vec(double a){
		x=a; y=a;
	}
	public double len(){
		return Math.hypot(x, y);
	}
	public static vec min(vec a,vec b){
		return new vec(a.x-b.x,a.y-b.y);
	}
	public static vec plus(vec a,vec b){
		return new vec(a.x+b.x,a.y+b.y);
	}
	public static vec umn(vec a,double b){
		return new vec(a.x*b,a.y*b);
	}
	public static vec del(vec a,double b){
		return new vec(a.x/b,a.y/b);
	}
	public vec norm(){
		return del(this,this.len());
	}
	public vec abs(){
		return new vec(Math.abs(x),Math.abs(y));
	}
	public static double ang(vec a,vec b){
		return (a.x*b.x+a.y*b.y)/a.len()/b.len();
	}
	public static vec dir(vec a){
		return new vec(Math.signum(a.x),Math.signum(a.y));	
	}
	
}
