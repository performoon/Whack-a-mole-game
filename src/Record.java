public class Record {
	private int highScore;
	private int speedRecord;
	public Record() {
		highScore = 0;
		speedRecord = 999999;
	}
	public int getHighScore() {
		return highScore;
	}
	public float getSpeedRecord() {
		return speedRecord;
	}
	public String setText() {
		if(highScore == 0) {
			return "noRecord";
		}else if(speedRecord == 999999) {
			return String.format("Score:%5d", highScore);
		}
		return String.format("Score:%5d;Speed:%6d", highScore, speedRecord);
		
	}
	public void showRecord() {
		System.out.println("highScore:"+highScore+",speedRecord:"+speedRecord);
	}
	public void setScore(int score, int speed, int gameEndCount) {
		if(score >= highScore) {
			highScore = score;
			if((score == gameEndCount) && speed < speedRecord) {
				speedRecord = speed;
			}
		}
	}
	public void setScore(int score) {
		if(score >= highScore) {
			highScore = score;
		}
	}
}
