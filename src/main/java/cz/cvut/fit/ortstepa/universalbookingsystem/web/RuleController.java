package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import java.util.ArrayList;
import java.util.Arrays;
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

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Rule;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.PriceEngine;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.PriceChangeService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.RuleService;

@Controller
@RequestMapping("/rule")
public class RuleController {
	
	private static final Logger log = LoggerFactory.getLogger(RuleController.class);
	
	private static final String VN_RULE_LIST = "rule/list";
	private static final String VN_RULE_EDIT = "rule/edit";
	private static final String VN_RULE_REDIRECT = "redirect:/rule";

	@Autowired 
	private RuleService ruleService;
	@Autowired
	private PriceChangeService priceChangeService;
	@Autowired
	private PriceEngine priceEngine;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		List<String> allowedFields = new ArrayList<String>(priceEngine.exposedVariables());
		allowedFields.addAll(Arrays.asList(new String[] { 
				"expression", "event", "name", "enabled", "priceChange"
			}));
		binder.setAllowedFields((String[])allowedFields.toArray());
	}
		
	private String page(Model model) {
		List<Rule> rules = ruleService.getAll();
		model.addAttribute("ruleList", rules);
		return VN_RULE_LIST;
	}
	
	private String form(Model model, Rule rule) {
		model.addAttribute("rule", rule);
		model.addAttribute("priceChangeList", priceChangeService.getAll());
		model.addAttribute("exposedVariables", priceEngine.exposedVariables());
		return VN_RULE_EDIT;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RequestParam(required=false) String success, final RedirectAttributes redirectAttributes) {
		if (success != null) redirectAttributes.addFlashAttribute("success", success);
		return page(model);
	}
	
	@RequestMapping(method = RequestMethod.GET, params={"form"})
	public String create(Model model) {
		Rule rule = ruleService.createEmptyRule();
		return form(model, rule);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params= { "form" })
	public String edit(Model model, @PathVariable Long id) {
		Rule rule = ruleService.get(id);
		return form(model, rule);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable Long id, Model model,  final RedirectAttributes redirectAttributes) {
		if (ruleService.delete(id))
			redirectAttributes.addFlashAttribute("success", "rule.remove.success");
		else
			model.addAttribute("error", "rule.remove.error");
		return VN_RULE_REDIRECT;				
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@ModelAttribute Rule rule, BindingResult result, @PathVariable Long id, Model model, final RedirectAttributes redirectAttributes) {
		rule.setId(id);
		ruleService.update(rule, result);
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("success", "rule.edit.success");
			return VN_RULE_REDIRECT;
		}	
		return VN_RULE_REDIRECT;			
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute Rule rule,	BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			ruleService.add(rule);
			redirectAttributes.addFlashAttribute("success", "rule.create.success");
			return VN_RULE_REDIRECT;			
		}
		return VN_RULE_EDIT;
	}
}
