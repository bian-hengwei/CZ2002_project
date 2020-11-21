import java.util.Set;

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

	public Index getModel() {
		return model;
	}

	public void setModel(Index i) {
		model = i;
	}

	public boolean checkTimeClash(Index targetIndex){
		int modelStartTime;
		int modelEndTime;
		int targetStartTime;
		int targetEndTime;
		String modelDay;
		String targetDay;
		String modelOddEven = "";
		String targetOddEven = "";

		// targetSet: all taken time
		// modelSet: all taken time

		String[] targetArray = new String[5];
		String[] modelArray = new String[5];

		targetArray[0] = targetIndex.getLectureTime()[0];
		targetArray[1] = targetIndex.getLectureTime()[1];
		targetArray[2] = targetIndex.getExamTime();
		targetArray[3] = targetIndex.getTutorialTime();
		targetArray[4] = targetIndex.getLabTime();

		modelArray[0] = model.getLectureTime()[0];
		modelArray[1] = model.getLectureTime()[1];
		modelArray[2] = model.getExamTime();
		modelArray[3] = model.getTutorialTime();
		modelArray[4] = model.getLabTime();
		int i;
		int j;
		for(i = 0; i < 5; i++){
			if(modelArray[i].equals("")){
				continue;
			}
			if(i == 4){
				modelOddEven = modelArray[i].substring(0, 3);
				modelStartTime = Integer.parseInt(modelArray[i].substring(6, 10));
				modelEndTime = Integer.parseInt(modelArray[i].substring(11, 15));
				modelDay = modelArray[i].substring(3, 6);
			}
			else{
				modelStartTime = Integer.parseInt(modelArray[i].substring(3, 7));
				modelEndTime = Integer.parseInt(modelArray[i].substring(8, 12));
				modelDay = modelArray[i].substring(0, 3);
			}
			for(j = 0; j < 5; j++){
				if(targetArray[j].equals("")){
					continue;
				}
				if(i == 4){
					targetOddEven = targetArray[i].substring(0, 3);
					targetStartTime = Integer.parseInt(targetArray[i].substring(6, 10));
					targetEndTime = Integer.parseInt(targetArray[i].substring(11, 15));
					targetDay = targetArray[i].substring(3, 6);
				}
				else{
					targetStartTime = Integer.parseInt(targetArray[i].substring(3, 7));
					targetEndTime = Integer.parseInt(targetArray[i].substring(8, 12));
					targetDay = targetArray[i].substring(0, 3);
				}

				if(i == 4 && j == 4 && !modelOddEven.equals(targetOddEven)){
					continue;
				}

				if(modelStartTime > targetStartTime && modelStartTime < targetEndTime){
					return false;
				}
				else if(modelEndTime > targetStartTime && modelEndTime < targetEndTime){
					return false;
				}
				else if(modelStartTime > targetStartTime && modelEndTime < targetEndTime){
					return false;
				}
				else if(targetStartTime > modelStartTime && targetEndTime < modelEndTime){
					return false;
				}
			}
		}
		return true;
	}

	public void addIndex(int idxNo) {
		model.setIndexNumber(idxNo);
		System.out.println("Adding new index " + idxNo + " to system");
		System.out.printf("Enter course id: ");
		model.setCourseId(scan.nextLine());
		System.out.printf("Enter course name: ");
		model.setCourseName(scan.nextLine());
		System.out.printf("Enter school: ");
		model.school(scan.nextLine());
		System.out.printf("Enter course id: ");
		model.setCourseId(scan.nextLine());
	}

	public void updateIndex() {
		System.out.println("Updating index " + model.getIndexNumber());
	}
}

     