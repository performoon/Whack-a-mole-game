public class Stage_Easy extends PlayStage{
	public Stage_Easy(Record setRecord) {
		// それぞれ、ウィンドウサイズ、モグラサイズ、モグラが出てる時間、モグラ総数
		super(1000, 400, 2000, 2);
		super.stageRecord = setRecord;
	}
}