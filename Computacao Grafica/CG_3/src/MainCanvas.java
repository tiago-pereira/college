import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import obj.ObjModel;

public class MainCanvas extends JPanel implements Runnable, KeyListener, MouseListener{
	int W = 640;
	int H = 480;
	
	Thread runner;
	boolean ativo = true;
	int paintcounter = 0;
	
	BufferedImage imageBuffer;
	byte bufferDeVideo[];
	
	Random rand = new Random();
	
	ArrayList<Vector2D> listaDePontos;
	
	ObjModel objtank = new ObjModel();
	
	public MainCanvas() {
		setSize(640,480);
		setPreferredSize( new Dimension(640, 480));
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		

		
		for(int i = 0; i < bufferDeVideo.length;i+=4){
			bufferDeVideo[i] = (byte)0x00ff;
			bufferDeVideo[i+1] = (byte)(0x00ff);
			bufferDeVideo[i+2] = (byte)(0x00ff);
			bufferDeVideo[i+3] = (byte)(0x00ff);
		}
		
		listaDePontos = new ArrayList<Vector2D>();
//		listaDePontos.add(new Vector2D(10,50));
//		listaDePontos.add(new Vector2D(100,50));
//		listaDePontos.add(new Vector2D(10,200));
//		listaDePontos.add(new Vector2D(10,300));
//		listaDePontos.add(new Vector2D(56,120));
		
		addMouseListener(this);
		addKeyListener(this);
		
		objtank.loadObj("/tank.obj");

		
		setFocusable(true);
		requestFocus(); 
	
	}
	
	public void setPixel(int x,int y,int r,int g,int b,int a){
		int index = (x+y*W)*4;
		
		bufferDeVideo[index] = (byte)(0x00ff&a);
		bufferDeVideo[index+1] = (byte)(0x00ff&b);
		bufferDeVideo[index+2] = (byte)(0x00ff&g);
		bufferDeVideo[index+3] = (byte)(0x00ff&r);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D dbg = (Graphics2D)g;
		// TODO Auto-generated method stub
		//super.paint(g);
		
//		for(int y = 0; y < H; y++){
//			for(int x = 0; x < W; x++){
//				setPixel(x,y,rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),255);
//			}
//		}
		
		
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, 640, 480);
//		g.setColor(Color.black);
//		g.drawLine(0, 0, 640, 480);
		
		dbg.setColor(Color.black);
		
		AffineTransform trans = dbg.getTransform();
		dbg.scale(3, 3);
		
		dbg.translate(100, 100);
		objtank.desenhase(dbg);
		
		
		dbg.setTransform(trans);
		
		//g.drawImage(imageBuffer,0,0,null);
//		for(int i = 0; i < listaDePontos.size()-1;i++){
//			Vector2D p =  listaDePontos.get(i);
//			Vector2D p1 =  listaDePontos.get(i+1);
//			g.drawLine((int)p.x,(int)p.y,(int)p1.x,(int)p1.y);
//		}
//		if(listaDePontos.size()>=2){
//			Vector2D p =  listaDePontos.get(0);
//			Vector2D p1 =  listaDePontos.get(listaDePontos.size()-1);
//			g.drawLine((int)p.x,(int)p.y,(int)p1.x,(int)p1.y);
//		}
		
		
		dbg.setColor(Color.black);
		dbg.drawString(""+paintcounter, 10, 20);
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
	
//	public void translate2d(int a,int b,Vector2D p){
//		p.x = p.x+a;
//		p.y = p.y+b;
//	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton()==1){
			for(int i = 0; i < listaDePontos.size();i++){
				Vector2D p = listaDePontos.get(i);
				Matriz3x3 m = new Matriz3x3();
				m.setRotate((float)(Math.PI/8));
				m.multiplyVector(p);
			}
		}
		if(e.getButton()==3){
			listaDePontos.add(new Vector2D(e.getX(), e.getY()));
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println("fdsfsdfsfsfs");
		if(key == e.VK_SPACE){
			System.out.println("Quero ir embora");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
