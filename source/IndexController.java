public class IndexController{
	private Index model;
	private IndexView view;

	public IndexController(Index model, IndexView view){
		this.model = model;
		this.view = view;
	}

	public void printIndexDetail(){
		view.printIndexDetail(model.getCourseId(), model.getIndexNumber(), model.getVacancy(), model.getTutorialTime(), model.getLabTime());
	}

	public void printVacancy(){
		view.printVacancy(model.getCourseId(), model.getIndexNumber(), model.getVacancy(), model.getWaitListLength());
	}
}