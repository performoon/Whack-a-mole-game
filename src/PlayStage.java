import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PlayStage extends UseWindow {
	protected int moleSize = 0;
	protected int interval = 0;
	protected int gameEndCount = 0;
	protected long startTime;

	protected int score = 0;
	protected int counter = 0;
	protected int mole_x;
	protected int mole_y;
	protected boolean mole_hit = false;
	protected int currentDifficulty;
	protected Record stageRecord;

	// protected final Point RETRY_BUTTON_POS = new Point(50,100);
	// リトライボタン。制御が難しく、実装した際のメリットも薄いと判断し断念。
	protected final Point EXIT_BUTTON_POS = new Point(50, 100);
	protected final Point BUTTONSIZE = new Point(150, 100);
	protected Timer t;
	protected GameMouseAdapter adapter;

	public PlayStage(int setWindowSize, int setSize, int setInterval, int setGameEndCount) {
		super(setWindowSize);
		moleSize = setSize;
		interval = setInterval;
		gameEndCount = setGameEndCount;

		adapter = new GameMouseAdapter();
		addMouseListener(adapter);

		setVisible(true);
		startTime = System.currentTimeMillis();

		t = new Timer();
		t.schedule(new GameTimeTask(), interval, interval);

		mole_x = (int) (Math.random() * (windowSize - moleSize));
		mole_y = (int) (Math.random() * (windowSize - moleSize));
	}

	protected class GameTimeTask extends TimerTask {
		@Override
		public void run() {
			// reset.reset();
			reset();
		}
	}

	// public class MoguraReset{
	public void reset() {
		if (counter++ >= gameEndCount) {

			return; // ゲームオーバー
		}
		// mole_hit = false; // 新しいモグラなのでヒット前
		mole_x = (int) (Math.random() * (windowSize - moleSize));
		mole_y = (int) (Math.random() * (windowSize - moleSize));
		repaint();
	}

	// }
	protected class GameMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			// マウスとモグラの座標計算
			int x = e.getPoint().x;
			int y = e.getPoint().y;
			int hankei = moleSize / 2;
			int mx = mole_x + hankei;
			int my = mole_y + hankei;
			int x_kyori = x - mx;
			int y_kyori = y - my;

			// ヒット判定
			if (x_kyori * x_kyori + y_kyori * y_kyori < hankei * hankei) {
				score++;
				t.cancel();

				reset();
				t = new Timer();
				System.out.println(t.getClass());
				t.schedule(new GameTimeTask(), interval, interval);
			}

			// 描画する
			repaint();
		}
	}

	protected class ButtonMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {

			// ヒット判定
			Point pos = e.getPoint();
			/*
			 * 断念したリトライボタン処理
			 * if((pos.x>RETRY_BUTTON_POS.x&&e.getPoint().x<(RETRY_BUTTON_POS.x+BUTTONSIZE.x
			 * ))&&(pos.y>RETRY_BUTTON_POS.y&&pos.y<(RETRY_BUTTON_POS.y+BUTTONSIZE.y)) ) {
			 * System.out.println(1);
			 * counter =0;
			 * t.cancel();
			 * //t.notifyAll();
			 * t = new Timer();
			 * t.schedule(new GameTimeTask(), interval, interval);
			 * GameMouseAdapter adapter = new GameMouseAdapter();
			 * addMouseListener(adapter);
			 * }
			 */
			if ((pos.x > EXIT_BUTTON_POS.x && e.getPoint().x < (EXIT_BUTTON_POS.x + BUTTONSIZE.x))
					&& (pos.y > EXIT_BUTTON_POS.y && pos.y < (EXIT_BUTTON_POS.y + BUTTONSIZE.y))) {
				setVisible(false);
			}
			return;
		}
	}

	public void paint(Graphics g) {
		g.drawImage(drawScreen(), 0, 0, this);
	}
	// 画面の表示処理

	public Image drawScreen() {
		Image screen = createImage(windowSize, windowSize);
		Graphics2D g = (Graphics2D) screen.getGraphics();
		setTitle("SCORE : " + score + " COUNT : " + counter);

		if (counter >= gameEndCount) {
			int time = (int) (System.currentTimeMillis() - startTime);
			g.setColor(Color.BLACK);
			g.drawString("TIME：" + time, 50, 65);
			stageRecord.setScore(score, time, gameEndCount);
			stageRecord.showRecord();

			g.setColor(Color.RED);
			/*
			 * 断念したリトライボタンの表示
			 * g.fillRect(RETRY_BUTTON_POS.x, RETRY_BUTTON_POS.y, BUTTONSIZE.x,
			 * BUTTONSIZE.y);
			 */
			g.fillRect(EXIT_BUTTON_POS.x, EXIT_BUTTON_POS.y, BUTTONSIZE.x, BUTTONSIZE.y);

			g.setColor(Color.BLACK);
			g.setFont(new Font("HG行書体", Font.PLAIN, 40));
			g.drawString("Exit", EXIT_BUTTON_POS.x + 30, EXIT_BUTTON_POS.y + 60);
			g.setFont(getFont());
			if (score < (int) gameEndCount * 0.6) {
				g.drawString("GAME OVER", 100, 100);
			} else {
				g.drawString("CLEAR", 100, 100);
			}

			t.cancel();
			ButtonMouseAdapter button = new ButtonMouseAdapter();
			removeMouseListener(adapter);
			addMouseListener(button);

			return screen;
		}

		// モグラ描画
		g.setColor(Color.YELLOW);
		g.fillOval(mole_x, mole_y, moleSize, moleSize);
		g.setColor(Color.BLACK);
		g.drawOval(mole_x, mole_y, moleSize, moleSize);

		return screen;
	}
}