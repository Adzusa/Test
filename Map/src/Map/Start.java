package Map;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Start extends Canvas implements Runnable {
	
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	public static final int CELL_WIDTH = 20;
	public static final int CELL_HEIGHT = 20;
	public static final int numW = WIDTH/CELL_WIDTH;
	public static final int numH = HEIGHT/CELL_HEIGHT;
	public static int zone=30;
	public static BufferedImage a=new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int m[]= ((DataBufferInt) a.getRaster().getDataBuffer()).getData();
	public boolean running=false;

	public static int buffer=0;
	public Graphics g;
	public static int Color;
	public BufferStrategy bs;
	public static void main(String[] args) throws IOException{
		Start f=new Start();
		f.setLocation(0, 0);
		f.setBounds(0, 0, WIDTH,HEIGHT);
		JFrame frame = new JFrame("Test!");
		f.addMouseListener(new Mouse1());
		f.addMouseMotionListener(new Mouse2());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.add(f);
		frame.setLocation(300, 300);
		frame.setSize(WIDTH+5,HEIGHT+28);
		//frame.pack();
		frame.setResizable(false);
		frame.setLocation(10,10);
		frame.setVisible(true);
		Map.init();
		Map.fec.add(new Thund(1,1,numW-1,numH-1,30));
		f.start(); 
		
	}
	public static void update(){
		
	}
	public void start() {
		g=this.getGraphics();
		running = true;
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void render(int b){
		/*try {
			   Thread.sleep(2);
			 } catch (InterruptedException e) {
			   e.printStackTrace();
			}*/
		int y=1;
		int k=0;
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
		}
		Map.updateThund();
		drawEnergy();
		drawThund();
		
		
		g.drawImage(a,0,0, null);
		//g.dispose();
		//bs.show();
	}
	
	public static int ColorGet(int a,int b,int c, int Alpha){
		return (Alpha<<24)+(a<<16)+(b<<8)+c;
	}
	

	public void run() {

	long lastTime = System.nanoTime();
	double unprocessed = 0;
	double nsPerTick = 1000000000.0 / 60;
	int frames = 0;
	int ticks = 0;
	long lastTimer1 = System.currentTimeMillis();

	//init();

	while (running) {
		long now = System.nanoTime();
		unprocessed += (now - lastTime) / nsPerTick;
		lastTime = now;
		boolean shouldRender = true;
		while (unprocessed >= 1) {
			ticks++;
			//tick();
			unprocessed -= 1;
			shouldRender = true;
		}

		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (shouldRender) {
			frames++;
			render(frames);
		}

		if (System.currentTimeMillis() - lastTimer1 > 1000) {
			lastTimer1 += 1000;
			System.out.println(ticks + " ticks, " + frames + " fps");
			frames = 0;
			ticks = 0;
		}
	}
}	
	public static void drawEnergy(){
		for (int i=0; i<numW; i++){
			for (int j=0; j<numH; j++){	
				buffer=Map.energymap[i][j];
				if (buffer>zone){
					Color=ColorGet(buffer,0,buffer,255);
				}else{
					if (buffer<-zone){
						Color=ColorGet(0,-buffer,-buffer,255);
					}else{
						Color=ColorGet(128+buffer,128+buffer,128+buffer,255);
					}	
				}
				for (int a=0; a<CELL_WIDTH; a++){
					for (int b=0; b<CELL_HEIGHT; b++){
						m[i*CELL_WIDTH+a+(j*CELL_HEIGHT+b)*WIDTH]=Color;
					}
				}
			}	
		}
	}
	public static void drawThund(){
		for (int i=0; i<numW; i++){
			for (int j=0; j<numH; j++){	
				buffer=(int)(Map.thundmap[i][j]*12)*10+128;
				Color=ColorGet(buffer,0,0,255);
				for (int a=0; a<CELL_WIDTH; a++){
					for (int b=0; b<CELL_HEIGHT; b++){
						m[i*CELL_WIDTH+a+(j*CELL_HEIGHT+b)*WIDTH]=Color;
					}
				}
			}	
		}
	}
	public static class Mouse1 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Map.fec.add(new Thund(e.getX()/CELL_WIDTH,e.getY()/CELL_HEIGHT,numW/2,numH/2,40));
	    }
		public void mouseReleased(MouseEvent e){
			Mouse2.Find=false;
		}
	}
	public static class Mouse2 extends MouseMotionAdapter {
		public static boolean Find=false;
		public static int Found;
		public static int dx;
		public static int dy;
		public void mouseDragged(MouseEvent e) { 

		}
		public static boolean test(int x,int y,Rectangle a){
			return (x>dx+3 && x-dx+a.width<WIDTH-3 && y>dy+3 && y-dy+a.height<HEIGHT-3);
		}
	}	
}
