package ubs.core.actions;

public abstract class Action {
	
	private ActionPlace actionPlace = ActionPlace.AFTER_DEFAULT;

	public ActionPlace getActionPlace() {
		return actionPlace;
	}
	
	public void setActionPlace(ActionPlace actionPlace) {
		this.actionPlace = actionPlace;
	}
	
	public abstract void execute() throws Exception;
}
