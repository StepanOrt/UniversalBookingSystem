package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Resource;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourcePropertyService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourceService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ScheduleService;

@Controller
@RequestMapping("/resource/{resourceId}/schedule")
public class ScheduleController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleService scheduleService;
	@Autowired 
	private ResourceService resourceService;
	@Autowired 
	private ResourcePropertyService resourcePropertyService;
	
	private static final String VN_SCH_LIST = "resource/schedule/list";
	//private static final String VN_SCH_DETAIL = "resource/schedule/detail";
	private static final String VN_SCH_EDIT = "resource/schedule/edit";
	private static final String VN_SCH_REDIRECT = "redirect:/resource/{resourceId}/schedule";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"capacity", "duration", "start", "visible", "note"
		});
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(
	            dateFormat, false));
	}
	
	private String page(Model model) {
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		return VN_SCH_LIST;
	}
	
	private String form(Model model, Schedule schedule) {
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		model.addAttribute("schedule", schedule);
		return VN_SCH_EDIT;
	}
/*
	private String detail(Model model, Schedule schedule) {
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		model.addAttribute("schedule", schedule);
		return VN_SCH_DETAIL;
	}
	*/
	@RequestMapping(method = RequestMethod.GET, params={"form"})
	public String create(Model model, @PathVariable Long resourceId) {
		Resource resource = resourceService.get(resourceId);
		Schedule schedule = scheduleService.createEmptySchedule();
		schedule.setResource(resource);
		return form(model, schedule);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @PathVariable Long resourceId, @RequestParam(required=false) String success,final RedirectAttributes redirectAttributes) {
		if (success != null) redirectAttributes.addFlashAttribute("success", success);
		Resource resource = resourceService.getEager(resourceId);
		model.addAttribute("resource", resource);
		return page(model);				
	}
	

	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	public String item(Model model, @PathVariable Long resourceId, @PathVariable Long id, @RequestParam(required=false) String form, final RedirectAttributes redirectAttributes) {
		Resource resource = resourceService.get(resourceId);
		Schedule schedule = scheduleService.get(id);
		if (schedule == null) {
			redirectAttributes.addFlashAttribute("error", "schedule.notFound");
			return VN_SCH_REDIRECT;
		}
		schedule.setResource(resource);
		return form(model, schedule);
/*		if (form != null) {
		}
		return detail(model, schedule);
*/
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@Valid @ModelAttribute Schedule schedule, BindingResult result, @PathVariable Long resourceId, @PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			schedule.setId(id);
			schedule.setResource(resourceService.get(resourceId));
			scheduleService.update(schedule, result);
			if (!result.hasErrors()) {
				redirectAttributes.addFlashAttribute("success", "schedule.edit.success");
				return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
			}
		}
		return VN_SCH_EDIT;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(Model model, @PathVariable Long resourceId, @PathVariable Long id, final RedirectAttributes redirectAttributes) {
		if (scheduleService.delete(id)) {
			redirectAttributes.addFlashAttribute("success", "schedule.remove.success");
			return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
		}
		model.addAttribute("error", "schedule.remove.error");
		return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Schedule schedule, BindingResult result, @PathVariable Long resourceId, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			schedule.setResource(resourceService.get(resourceId));
			scheduleService.add(schedule, result);
			if (!result.hasErrors()) {
				redirectAttributes.addFlashAttribute("success", "schedule.create.success");
				return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
			}
		}
		return VN_SCH_EDIT;
	}
}
