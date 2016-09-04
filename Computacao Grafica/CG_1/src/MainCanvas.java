import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Random;

import javax.swing.JPanel;

public class MainCanvas extends JPanel implements Runnable{
	int W = 640;
	int H = 480;
	
	Thread runner;
	boolean ativo = true;
	int paintcounter = 0;
	
	BufferedImage imageBuffer;
	byte bufferDeVideo[];
	
	Random rand = new Random();
	
	byte memoriaPlacaVideo[];
	short paleta[][];
	
	public MainCanvas() {
		setSize(640,480);
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
		memoriaPlacaVideo = new byte[W*H];
		
		paleta = new short[255][3];
		
		for(int i = 0; i < 255;i++){
			paleta[i][0] = (short)rand.nextInt(255);
			paleta[i][1] = (short)rand.nextInt(255);
			paleta[i][2] = (short)rand.nextInt(255);
			
		}
		
//		for(int i = 0; i < bufferDeVideo.length;i+=4){
//			int r = rand.nextInt(255);
//			int g = rand.nextInt(255);
//			int b = rand.nextInt(255);
//			
//			bufferDeVideo[i] = (byte)0x00ff;
//			bufferDeVideo[i+1] = (byte)(0x00ff&b);
//			bufferDeVideo[i+2] = (byte)(0x00ff&g);
//			bufferDeVideo[i+3] = (byte)(0x00ff&r);
//		}
		
//		// 100,20 200,20
//		for(int i = 0; i < 100;i++){
//			int x = 100+i;
//			int y = 20;
//			int bt = x*4+y*640*4;
//			bufferDeVideo[bt] = (byte)0x00ff;
//			bufferDeVideo[bt+1] = (byte)0;
//			bufferDeVideo[bt+2] = (byte)0;
//			bufferDeVideo[bt+3] = (byte)0x00ff;
//		}
		
		for(int y = 0; y < H;y++){
			for(int x = 0; x < W;x++){
				memoriaPlacaVideo[x+y*W] = (byte)((y%255)&0x00ff);
			}
		}
		
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		
//		for(int i = 0; i < bufferDeVideo.length;i+=4){
//			int rr = rand.nextInt(255);
//			int gg = rand.nextInt(255);
//			int bb = rand.nextInt(255);
//			
//			bufferDeVideo[i] = (byte)0x00ff;
//			bufferDeVideo[i+1] = (byte)(0x00ff&bb);
//			bufferDeVideo[i+2] = (byte)(0x00ff&gg);
//			bufferDeVideo[i+3] = (byte)(0x00ff&rr);
//		}
		
		for(int i = 0; i < memoriaPlacaVideo.length;i++){
			int bufferindex = i*4;
			bufferDeVideo[bufferindex] = (byte)0x00ff;
			bufferDeVideo[bufferindex+1] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][2]&0x00ff);
			bufferDeVideo[bufferindex+2] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][1]&0x00ff);
			bufferDeVideo[bufferindex+3] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][0]&0x00ff);
		}
		
		
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 640, 480);
//		g.setColor(Color.black);
//		g.drawLine(0, 0, 640, 480);
		
		g.drawImage(imageBuffer,0,0,null);
		
		g.setColor(Color.black);
		g.drawString(""+paintcounter, 10, 20);
	}
	
	public void start(){
		runner = new Thread(this);
		runner.start();
	}
	
	@Override
	public void run() {
		while(ativo){
			paintImmediately(0, 0, 640, 480);
			paintcounter++;
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
