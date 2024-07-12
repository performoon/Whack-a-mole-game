import javax.swing.JFrame;

public abstract class UseWindow extends JFrame {
	protected int windowSize;
	public UseWindow(int setWindowSize) {
		windowSize = setWindowSize;
		setSize(windowSize,windowSize);
	}
}
