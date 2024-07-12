import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class Stage_Endless extends PlayStage {
	public Stage_Endless(Record setRecord) {
		super(500, 50, 800, 5);
		super.stageRecord = setRecord;
	}

	@Override
	public void reset() {
		System.out.println(counter + "a" + (score + gameEndCount));
		if (counter++ > super.score + gameEndCount) {

			return; // ゲームオーバー
		}
		// mole_hit = false; // 新しいモグラなのでヒット前
		mole_x = (int) (Math.random() * (windowSize - moleSize));
		mole_y = (int) (Math.random() * (windowSize - moleSize));
		repaint();
	}

	@Override
	public Image drawScreen() {
		Image screen = createImage(windowSize, windowSize);
		Graphics2D g = (Graphics2D) screen.getGraphics();
		setTitle("SCORE : " + score + " COUNT : " + counter);

		if (counter > super.score + gameEndCount) {
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
