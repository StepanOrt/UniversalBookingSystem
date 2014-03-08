package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.PasswordChangeForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.RegistrationForm;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	private static final String VN_ACC_FORM = "account/accountForm";
	private static final String VN_ACC_OK = "redirect:/account";
	private static final String VN_REG_FORM = "account/registrationForm";
	private static final String VN_REG_OK = "redirect:/home";
	private static final String VN_PASS_FORM = "account/passwordChangeForm";
	private static final String VN_PASS_OK = "redirect:/account";

	@Autowired 
	private AccountService accountService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authMgr;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"currentPassword", "password", "confirmPassword", "firstName", "lastName",
			"email", "marketingOk", "acceptTerms"
		});
	}
	
	
	@RequestMapping(value = "password", method = RequestMethod.GET)
	public String getPasswordChangeForm(Model model) {
		model.addAttribute("form", new PasswordChangeForm());
		return VN_PASS_FORM;
	}
	
	@RequestMapping(value = "password", method = RequestMethod.POST)
	public String postPasswordChangeForm(
			@ModelAttribute("form") @Valid PasswordChangeForm form,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		String credentials = auth.getCredentials().toString();
		String currentPassword = form.getCurrentPassword();
		
		convertPasswordError(result);
		if (!credentials.equals(currentPassword))
			result.rejectValue("currentPassword", "error.invalid.password");
		else {
			String password = form.getPassword();
			accountService.changePassword(email, password, result);
		}
		
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "passwordChange.message.success");
			return VN_PASS_OK;
		}
		return VN_PASS_FORM;
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		log.debug("GET registration auth=" + SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("form", new RegistrationForm());
		return VN_REG_FORM;
	}

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("form") @Valid RegistrationForm form,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		log.debug("POST registration auth=" + SecurityContextHolder.getContext().getAuthentication().getName());
		convertPasswordError(result);
		String password = form.getPassword();
		Account account = new Account();
		form.fill(account);
		accountService.registerAccount(account, password, result);

		if (!result.hasErrors()) {
			Authentication authRequest = new UsernamePasswordAuthenticationToken(account.getEmail(), password);
			Authentication authResult = authMgr.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authResult);
			redirectAttributes.addFlashAttribute("message", "registration.message.thanks");
			return VN_REG_OK;
		}
		return VN_REG_FORM;
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAccountForm(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.getAccountByEmail(email);
		model.addAttribute("form", AccountForm.create(account));
		return VN_ACC_FORM;
	}


	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postAccountForm(
			@ModelAttribute("form") @Valid AccountForm form,
			BindingResult result, final RedirectAttributes redirectAttributes) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		String credentials = auth.getCredentials().toString();
		String currentPassword = form.getCurrentPassword();
		
		if (!credentials.equals(currentPassword))
			result.rejectValue("currentPassword", "error.invalid.password");
		else 
			accountService.updateAccount(email, form, result);
		
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "account.message.sucess");
			Authentication authRequest = new UsernamePasswordAuthenticationToken(form.getEmail(), currentPassword);
			Authentication authResult = authMgr.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authResult);
			return VN_ACC_OK;
		}
		return VN_ACC_FORM;
	}

	private static void convertPasswordError(BindingResult result) {
		// Map class-level Hibernate error message to field-level Spring error message.
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("account.password.mismatch.message".equals(msg)) {
				// Don't show if there's already some other error message.
				if (!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.mismatch");
				}
			}
		}
	}
}
