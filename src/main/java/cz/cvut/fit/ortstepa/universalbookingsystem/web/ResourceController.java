package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
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
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourcePropertyService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired 
	private ResourceService resourceService;
	@Autowired 
	private ResourcePropertyService resourcePropertyService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authMgr;

	private static final String VN_RES_LIST = "resource/list";
	private static final String VN_RES_DETAIL = "resource/detail";
	private static final String VN_RES_EDIT = "resource/edit";
	private static final String VN_RES_EDIT_OK = "redirect:/resource";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"capacity", "duration", "price", "visible", "values", "propertyValuesMap[*]"
		});
	}
	
	private String page(Model model) {
		List<Resource> list = resourceService.getAll();
		model.addAttribute("resourceList", list);
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		return VN_RES_LIST;
	}
	
	private String form(Model model, Resource resource) {
		model.addAttribute("resource", resource);
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		return VN_RES_EDIT;
	}

	private String detail(Model model, Resource resource) {
		model.addAttribute("resource", resource);
		model.addAttribute("propertyTypeMap", resourcePropertyService.getMap());
		return VN_RES_DETAIL;
	}

	@RequestMapping(method = RequestMethod.GET, params={"form"})
	public String create(Model model) {
		return form(model, resourceService.createEmptyResource());
	}	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RequestParam(required=false) String success, final RedirectAttributes redirectAttributes) {
		if (success != null) redirectAttributes.addFlashAttribute("success", success);
		return page(model);				
	}

	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String detail(Model model, @PathVariable Long id, @RequestParam(required=false) String form) {
		Resource resource = resourceService.get(id);
		if (resource == null) {
			model.addAttribute("error", "resource.notFound");
			return VN_RES_LIST;
		}
		if (form != null) {
			return form(model, resource);
		}
		return detail(model, resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@ModelAttribute Resource resource, BindingResult result, @PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		resource.setId(id);
		resourceService.update(resource, result);
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("success", "resource.edit.success");
			return VN_RES_EDIT_OK;
		}	
		return VN_RES_EDIT;			
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable Long id, Model model,  final RedirectAttributes redirectAttributes) {
		if (resourceService.delete(id)) {
			redirectAttributes.addFlashAttribute("success", "resource.remove.success");
			return VN_RES_EDIT_OK;			
		}
		else {
			model.addAttribute("error", "resource.remove.error");
			return VN_RES_DETAIL;				
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute Resource resource,	BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			resourceService.add(resource);
			redirectAttributes.addFlashAttribute("success", "resource.create.success");
			return VN_RES_EDIT_OK;			
		}
		return VN_RES_EDIT;
	}
}
