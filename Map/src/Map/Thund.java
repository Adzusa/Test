package Map;

public class Thund {
	public vec me;
	public vec to;
	public double dx;
	public double dy;
	public vec dir;
	public vec dir1;
	public int power;
	public boolean close=false;
	public Thund(int a,int b,int c,int d,int e){
		me=new vec(a,b); to=new vec(c,d); power=e;
		dir=vec.min(to,me);
		dir1=vec.dir(dir);
		genOcclusion();
	}
	public void genOcclusion(){
		double l=Math.min(Math.sqrt(power)/1.2,dir.len());
		vec t=vec.umn(dir.norm(), l).abs();
		for (int i=0; i<Math.floor(t.x); i++){
			for (int j=0; j<Math.floor(t.y); j++){
				Map.thundmap[(int) (me.x+i*dir1.x)][(int) (me.y+j*dir1.y)]+=l-Math.hypot(i, j);
			}
		}
	}
	public void time(){
		me=vec.plus(me,vec.umn(dir.norm(),Math.sqrt(power)/150));
		dir=vec.min(to,me);
		if (dir.len()<Math.sqrt(power)/30){close=true;}

		//System.out.println(dir.len());
		//System.out.println(me.x);
		//System.out.println(me.y);
		genOcclusion();
	}
}
