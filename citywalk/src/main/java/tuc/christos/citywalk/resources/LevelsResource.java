package tuc.christos.citywalk.resources;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import tuc.christos.citywalk.model.Content;
import tuc.christos.citywalk.model.Level;
import tuc.christos.citywalk.model.Link;
import tuc.christos.citywalk.model.Location;
import tuc.christos.citywalk.model.Period;
import tuc.christos.citywalk.model.Scene;
import tuc.christos.citywalk.service.LevelsService;
import tuc.christos.citywalk.service.PeriodService;
import tuc.christos.citywalk.service.SceneService;

@Path("/levels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LevelsResource {

	@GET
	public List<Level> getAllLevels(@Context UriInfo uriInfo){
		List<Level> levels = new ArrayList<>();
		levels = LevelsService.getLevels();
		return levels;
	}
	
	@GET
	@Path("/loc")
	public Level getLevelForLocation(@Context UriInfo uriInfo, @QueryParam("lat")double latitude, @QueryParam("lon")double longitude){
		Level level = null;
		Location location = new Location(latitude, longitude);
		level = LevelsService.getLevelFromLocation(location);
		return level;
	}
	
	@GET
	@Path("/{admin_area_id}")
	public Level getLevel(@Context UriInfo uriInfo, @PathParam("admin_area_id") long id){
		Level level = LevelsService.getLevel(id);
		return level;
	}
	
	@GET
	@Path("/{admin_area_id}/content")
	public Content getContentForLevel(@Context UriInfo uriInfo, @PathParam("admin_area_id") long id){
		Content levelContent = new Content();
		Level level = this.getLevel(uriInfo, id);
		levelContent.setLevel(level);
		levelContent.setScenes(SceneService.getScenesForLocation(level.getCountry_name(), level.getAdmin_area_name()));
		
		for(Scene scene: levelContent.getScenes()){
			scene.addLink(new Link(sceneUriForSelf(uriInfo, scene),"self"));
			if(!scene.getImgLoc().isEmpty()){
				scene.addLink(new Link(sceneUriThumbnail(uriInfo, scene),"thumbnail"));
				scene.addLink(new Link(sceneUriImages(uriInfo, scene),"images"));
			}
		}
		levelContent.setPeriods(PeriodService.getPeriodsForLocation(level.getCountry_name(), level.getAdmin_area_name()));
		for(Period period: levelContent.getPeriods()){
			period.getLinks().clear();
			period.addLink(new Link(periodUriForSelf(uriInfo, period),"self"));
			period.addLink(new Link(uriForScenes(uriInfo,period),"scenes"));
			period.addLink(new Link(uriForLogo(uriInfo, period),"logo"));
			period.addLink(new Link(uriForMapOver(uriInfo, period),"map"));
			period.addLink(new Link(uriForImages(uriInfo, period),"images"));
		}
		return levelContent;
	}
	
	private String sceneUriForSelf(UriInfo uriInfo, Scene scene) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ScenesResource.class)
				.path(ScenesResource.class,"getScene")
				.resolveTemplate("id", scene.getId())
				.build()
				.toString();
		return uri;
	}
	 
	private String sceneUriImages(UriInfo uriInfo, Scene scene) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ScenesResource.class)
				.path(ScenesResource.class,"getScene")
				.resolveTemplate("id", scene.getId())
				.path("images")
				.build()
				.toString();
		return uri;
	}
	
	private String sceneUriThumbnail(UriInfo uriInfo, Scene scene) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ScenesResource.class)
				.path(ScenesResource.class,"getScene")
				.resolveTemplate("id", scene.getId())
				.path("images/thumb.jpg")
				.build()
				.toString();
		return uri;
	}

	private String periodUriForSelf(UriInfo uriInfo, Period period) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(PeriodsResource.class)
				.path(PeriodsResource.class,"getPeriod")
				.resolveTemplate("periodid", period.getId())
				.build()
				.toString();
		return uri;
	}
	
	private String uriForScenes(UriInfo uriInfo, Period period) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(PeriodsResource.class)
				.path(PeriodsResource.class,"getPeriod")
				.resolveTemplate("periodid", period.getId())
				.path("scenes")
				.build()
				.toString();
		return uri;
	}
	
	private String uriForImages(UriInfo uriInfo, Period period) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(PeriodsResource.class)
				.path(PeriodsResource.class,"getPeriod")
				.resolveTemplate("periodid", period.getId())
				.path("images")
				.build()
				.toString();
		return uri;
	}
	
	private String uriForLogo(UriInfo uriInfo, Period period) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(PeriodsResource.class)
				.path(PeriodsResource.class,"getPeriod")
				.resolveTemplate("periodid", period.getId())
				.path("images/logo.jpg")
				.build()
				.toString();
		return uri;
	}
	
	private String uriForMapOver(UriInfo uriInfo, Period period) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(PeriodsResource.class)
				.path(PeriodsResource.class,"getPeriod")
				.resolveTemplate("periodid", period.getId())
				.path("images/map.jpg")
				.build()
				.toString();
		return uri;
	}
}
