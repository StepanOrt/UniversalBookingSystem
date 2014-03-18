package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourcePropertyService;

@Controller
@RequestMapping("/resource/property")
public class ResourcePropertyController {
	
	@Autowired
	private ResourcePropertyService resourcePropertyService;
	
	private static final String VN_RES_PROP_LIST = "resource/property/list";
	private static final String VN_RES_PROP_DETAIL = "resource/property/detail";
	private static final String VN_RES_PROP_EDIT = "resource/property/edit";
	private static final String VN_RES_PROP_REDIRECT = "redirect:/resource/property";
	
	private static final Logger logger = LoggerFactory.getLogger(ResourcePropertyController.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"name", "type", "defaultValue"
		});
	}
	
	private String page(Model model) {
		model.addAttribute("resourcePropertyList", resourcePropertyService.getAll());
		return VN_RES_PROP_LIST;
	}

	private String detail(Model model, ResourceProperty resourceProperty) {
		model.addAttribute("resourceProperty", resourceProperty);
		return VN_RES_PROP_DETAIL;
	}
	
	private String form(Model model, ResourceProperty resourceProperty) {
		model.addAttribute("resourceProperty", resourceProperty);
		return VN_RES_PROP_EDIT;
	}
	
	@RequestMapping(method = RequestMethod.GET, params={"form"})
	public String create(Model model) {
		return form(model, new ResourceProperty());				
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return page(model);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) 
	public String item(Model model, @PathVariable Long id, @RequestParam(required=false) String form, final RedirectAttributes redirectAttributes) {
		ResourceProperty resourceProperty = resourcePropertyService.getById(id);
		if (resourceProperty == null) {
			redirectAttributes.addFlashAttribute("error", "resourceProperty.notFound");
			return VN_RES_PROP_REDIRECT;
		}
		if (form != null) {
			return form(model, resourceProperty);
		}
		return detail(model, resourceProperty);
	}
	
			
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute ResourceProperty resourceProperty, BindingResult result, @PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			resourceProperty.setId(id);
			resourcePropertyService.update(resourceProperty, result);
			if (!result.hasErrors()) {
				redirectAttributes.addFlashAttribute("success", "resourceProperty.edit.success");
				return VN_RES_PROP_REDIRECT;
			}
		}
		return VN_RES_PROP_EDIT;
	}
	
		
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, params={"delete"})
	public String remove(Model model, @PathVariable Long id, final RedirectAttributes redirectAttributes) {
		if (resourcePropertyService.delete(id)) {
			redirectAttributes.addFlashAttribute("success", "resourceProperty.remove.success");
			return VN_RES_PROP_REDIRECT;
		}
		model.addAttribute("error", "resourceProperty.remove.error");
		return VN_RES_PROP_DETAIL;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute ResourceProperty resourceProperty, BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			resourcePropertyService.add(resourceProperty, result);
			if (!result.hasErrors()) {
				redirectAttributes.addFlashAttribute("success", "resourceProperty.create.success");
				return VN_RES_PROP_REDIRECT;
			}
		}
		return VN_RES_PROP_EDIT;
	}
}
