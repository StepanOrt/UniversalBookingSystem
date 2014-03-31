package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import java.util.List;

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

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.PriceChange;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.PriceChangeService;

@Controller
@RequestMapping("/priceChange")
public class PriceChangeController {
	
private static final Logger log = LoggerFactory.getLogger(PriceChangeController.class);
	
	private static final String VN_PRICE_CHANGE_LIST = "priceChange/list";
	private static final String VN_PRICE_CHANGE_EDIT = "priceChange/edit";
	private static final String VN_PRICE_CHANGE_REDIRECT = "redirect:/priceChange";

	@Autowired 
	private PriceChangeService priceChangeService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"value", "type", "name"
		});
	}
	
	private String page(Model model) {
		List<PriceChange> priceChangeList = priceChangeService.getAll();
		model.addAttribute("priceChangeList", priceChangeList);
		return VN_PRICE_CHANGE_LIST;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RequestParam(required=false) String success, final RedirectAttributes redirectAttributes) {
		if (success != null) redirectAttributes.addFlashAttribute("success", success);
		return page(model);
	}
	
	private String form(Model model, PriceChange priceChange) {
		model.addAttribute("priceChange", priceChange);
		return VN_PRICE_CHANGE_EDIT;
	}
	
	@RequestMapping(method = RequestMethod.GET, params={"form"})
	public String create(Model model) {
		PriceChange priceChange = priceChangeService.createEmptyRule();
		return form(model, priceChange);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params= { "form" })
	public String edit(Model model, @PathVariable Long id) {
		PriceChange priceChange = priceChangeService.get(id);
		return form(model, priceChange);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable Long id, Model model,  final RedirectAttributes redirectAttributes) {
		if (priceChangeService.delete(id))
			redirectAttributes.addFlashAttribute("success", "priceChange.remove.success");
		else
			model.addAttribute("error", "rule.remove.error");
		return VN_PRICE_CHANGE_REDIRECT;				
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@ModelAttribute PriceChange priceChange, BindingResult result, @PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		priceChange.setId(id);
		priceChangeService.update(priceChange, result);
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("success", "priceChange.edit.success");
			return VN_PRICE_CHANGE_REDIRECT;
		}	
		return VN_PRICE_CHANGE_REDIRECT;			
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute PriceChange priceChange,	BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			priceChangeService.add(priceChange);
			redirectAttributes.addFlashAttribute("success", "priceChange.create.success");
			return VN_PRICE_CHANGE_REDIRECT;			
		}
		return VN_PRICE_CHANGE_EDIT;
	}
	
}
