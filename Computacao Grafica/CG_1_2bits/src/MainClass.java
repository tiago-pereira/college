import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args) {
		MainCanvas meuCanvas = new MainCanvas();
		
		JFrame f = new JFrame();
		f.setSize(640, 480);
		f.setVisible(true);
		f.getContentPane().add(meuCanvas);
	
		
		meuCanvas.start();
	}
}
