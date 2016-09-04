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
	
	public MainCanvas() {
		setSize(640,480);
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
		for(int i = 0; i < bufferDeVideo.length;i+=4){
			bufferDeVideo[i] = (byte)0x00ff;
			bufferDeVideo[i+1] = (byte)(0x00ff);
			bufferDeVideo[i+2] = (byte)(0x00ff);
			bufferDeVideo[i+3] = (byte)(0x00ff);
		}
	
	}
	
	public void setPixel(int x,int y,int r,int g,int b,int a){
		int index = (x+y*W)*4;
		
		bufferDeVideo[index] = (byte)(0x00ff&a);
		bufferDeVideo[index+1] = (byte)(0x00ff&b);
		bufferDeVideo[index+2] = (byte)(0x00ff&g);
		bufferDeVideo[index+3] = (byte)(0x00ff&r);
	}
	
	public void setPixel(int x, int y) {
		setPixel(x, y, 0, 0, 0, 1);
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		
		for(int y = 0; y < H; y++){
			for(int x = 0; x < W; x++){
				setPixel(x,y,rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),255);
			}
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
