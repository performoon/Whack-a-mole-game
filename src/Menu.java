import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends UseWindow implements ActionListener, IMenu {
	private final int STAGE_NUMBER = 5;
	private final static int WINDOW_SIZE = 600;
	private final static String[] STAGE_NAMES = { "Easy", "Normal", "Hard", "Lunatic", "Endless", "Exit" };

	private int useButtonNumber;
	private int useLabelNumber;
	private int buttonStartLocation_x;
	private int buttonStartLocation_y;
	private int buttonSize_y;
	private int buttonSize_x;
	private int buttonInterval_y;

	private int textStartLocation_x;
	private JButton[] stageSelectButtons; // それぞれのステージ開始ボタン
	private JLabel[] stageRecordText; // ステージの記録を表示
	PlayStage stageWindow;
	JPanel p = new JPanel();

	Container contentPane;

	private Record stageRecords[] = new Record[STAGE_NUMBER];

	Menu() {
		super(WINDOW_SIZE);
		setResizable(false);
		useButtonNumber = STAGE_NUMBER + 1;
		useLabelNumber = STAGE_NUMBER;
		p.setLayout(null);
		stageSelectButtons = new JButton[useButtonNumber];
		stageRecordText = new JLabel[useLabelNumber];
		setDefaultCloseOperation(UseWindow.EXIT_ON_CLOSE);
		contentPane = getContentPane();

		// Controlのそれぞれの要素の計算
		buttonStartLocation_x = (int) ((double) windowSize * 0.1);
		buttonStartLocation_y = (int) ((double) windowSize * 0.05);
		buttonSize_x = (int) ((double) windowSize * 0.3);
		buttonSize_y = (int) ((double) windowSize * 0.65 / useButtonNumber);
		buttonInterval_y = (int) ((double) windowSize * 0.2 / useButtonNumber);
		textStartLocation_x = (int) ((double) windowSize * 0.5);

		System.out.println(buttonStartLocation_x);
		System.out.println(buttonStartLocation_y);
		System.out.println(buttonSize_x);
		System.out.println(buttonSize_y);

		for (int i = 0; i < useButtonNumber; i++) {
			stageSelectButtons[i] = new JButton(STAGE_NAMES[i]);
			stageSelectButtons[i].setName(Integer.toString(i));
			stageSelectButtons[i].setBounds(buttonStartLocation_x,
					buttonStartLocation_y + i * (buttonInterval_y + buttonSize_y), buttonSize_x, buttonSize_y);
			stageSelectButtons[i].setPreferredSize(new Dimension(300, 100));
			stageSelectButtons[i].setEnabled(true);
			stageSelectButtons[i].addActionListener(this);
			p.add(stageSelectButtons[i]);
		}

		for (int i = 0; i < useLabelNumber; i++) {
			stageRecordText[i] = new JLabel("No Record");
			stageRecordText[i].setBounds(textStartLocation_x, buttonStartLocation_y + i * (buttonInterval_y + buttonSize_y),
					buttonSize_x, buttonSize_y);
			p.add(stageRecordText[i]);
		}
		for (int i = 0; i < stageRecords.length; i++) {
			stageRecords[i] = new Record();
			stageRecordText[i].setText(stageRecords[i].setText());
		}

		stageSelectButtons[useButtonNumber - 1].setText("Exit");
		contentPane.add(p);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (stageWindow != null) {
			stageWindow.setVisible(false);
		}
		switch (e.getActionCommand()) {
			case "Easy":
				stageWindow = new Stage_Easy(stageRecords[0]);
				stageWindow.addWindowListener(new MyWindowListener());
				break;
			case "Normal":
				stageWindow = new Stage_Normal(stageRecords[1]);
				stageWindow.addWindowListener(new MyWindowListener());
				break;
			case "Hard":
				stageWindow = new Stage_Hard(stageRecords[2]);
				stageWindow.addWindowListener(new MyWindowListener());
				break;
			case "Lunatic":
				stageWindow = new Stage_Lunatic(stageRecords[3]);
				stageWindow.addWindowListener(new MyWindowListener());
				break;
			case "Endless":
				stageWindow = new Stage_Endless(stageRecords[4]);
				stageWindow.addWindowListener(new MyWindowListener());
				break;
			case "Exit":
				setVisible(false);
				break;
			default:
				break;

		}
		/*
		 * case "Stage3":
		 * stageWindow = new Stage_3();
		 * break;
		 * case "lunatic":
		 * stageWindow = new
		 * }
		 */
		/*
		 * for(int i = 0; i < stageSelectButtons.length; i++) {
		 * if(e.getSource() == stageSelectButtons[1]) {
		 * 
		 * 
		 * stageWindow = new Stage_1(1000);
		 * stageWindow.setVisible(true);
		 * System.out.println(i);
		 * break;
		 * }
		 * }
		 */
	}

	/*
	 * このコメントアウトの処理をすると、なぜかボタンが2つしか表示されないうえに１つづつ表示されるボタンの添え字がずれていく。
	 * public Image drawScreen() {
	 * Image screen = createImage(windowSize, windowSize);
	 * Graphics2D g = (Graphics2D)screen.getGraphics();
	 * g.setColor(Color.BLACK);
	 * return screen;
	 * }
	 * public void paint(Graphics g) {
	 * 
	 * buttonRepaint();
	 * 
	 * g.drawImage(drawScreen(), 0, 0, this);
	 * }
	 * 
	 * @SuppressWarnings("deprecation")
	 * public void buttonRepaint() {
	 * for(int i = 0; i<useButtonNumber; i++) {
	 * p.add(stageSelectButtons[i]);
	 * }
	 * contentPane.add(p);
	 * }
	 */
	class MyWindowListener extends WindowAdapter {
		public void windowDeactivated(WindowEvent e) {
			System.out.println("1:" + e.getSource().getClass().getName());

			for (int i = 0; i < stageRecords.length; i++) {
				stageRecordText[i].setText(stageRecords[i].setText());
			}
		}

		public void windowClosing(WindowEvent e) {
			System.out.println("2:" + e.getSource().getClass().getName());

			for (int i = 0; i < stageRecords.length; i++) {
				stageRecordText[i].setText(stageRecords[i].setText());
			}

		}
	}
}