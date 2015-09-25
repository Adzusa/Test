package Map;

import java.util.ArrayList;
import java.util.Random;

public class Map {
	public static int W=Start.numW;
	public static int H=Start.numH;
	public static int zone=Start.zone;
	public static Random random=new Random();
	public static int[][] energymap=new int[W][H];
	public static double[][] thundmap=new double[W][H];
	public static ArrayList<Thund> fec=new ArrayList<Thund>();
	public static void init(){
		for (int i=0; i<W; i++){
			for (int j=0; j<H; j++){
				energymap[i][j]=random.nextInt(510)-255;			
			}
		}
	}
	public static void updateThund(){
		for (int i=0; i<W; i++){
			for (int j=0; j<H; j++){
				thundmap[i][j]=0.0;
			}
		}	
		for (int i=0; i<fec.size(); i++){
			fec.get(i).time();
			if (fec.get(i).close){
				fec.remove(i);
			}
		}
	}
	
}
