package tuc.christos.citywalk.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Content {
	private Level level;
	private ArrayList<Scene> scenes = new ArrayList<>();
	private ArrayList<Period> periods = new ArrayList<>();
	
	public Content(){}
	public Content(Level level, ArrayList<Scene> scenes, ArrayList<Period> periods) {
		super();
		this.level = level;
		this.scenes = scenes;
		this.periods = periods;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public ArrayList<Scene> getScenes() {
		return scenes;
	}
	public void setScenes(ArrayList<Scene> scenes) {
		this.scenes = scenes;
	}
	public ArrayList<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(ArrayList<Period> periods) {
		this.periods = periods;
	}
	
	public void addScene(Scene scene){
		this.scenes.add(scene);
	}
	
	public void removeScene(Scene scene){
		this.scenes.remove(scene);
	}
	
	public void addPeriod(Period period){
		this.periods.add(period);
	}
	
	public void removePeriod(Period period){
		this.periods.remove(period);
	}
	
}
