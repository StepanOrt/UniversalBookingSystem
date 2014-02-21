package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.UserDetailsAdapter;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.PasswordChangeForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.RegistrationForm;

@Controller
@RequestMapping("/account")
public class AccountController {
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
			"username", "password", "confirmPassword", "firstName", "lastName",
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
		
		convertPasswordError(result);
		String password = form.getPassword();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		accountService.changePassword(username, password, result);
		
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "passwordChange.message.success");
			return VN_PASS_OK;
		}
		return VN_PASS_FORM;
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return VN_REG_FORM;
	}

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("form") @Valid RegistrationForm form,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		convertPasswordError(result);
		String password = form.getPassword();
		Account account = new Account();
		form.fill(account);
		accountService.registerAccount(account, password, result);

		if (!result.hasErrors()) {
			Authentication authRequest = new UsernamePasswordAuthenticationToken(form.getUsername(), password);
			Authentication authResult = authMgr.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authResult);
			redirectAttributes.addFlashAttribute("message", "registration.message.thanks");
			return VN_REG_OK;
		}
		return VN_REG_FORM;
	}
	


	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAccountForm(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.getAccountByUsername(username);
		model.addAttribute("form", AccountForm.create(account));
		return VN_ACC_FORM;
	}


	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postAccountForm(
			@ModelAttribute("form") @Valid AccountForm form,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		accountService.updateAccount(username, form, result);
		Account account = accountService.getAccountByUsername(form.getUsername());
		UserDetails details = new UserDetailsAdapter(account);

		if (!result.hasErrors()) {
			Authentication authRequest = new UsernamePasswordAuthenticationToken(details, "");
			SecurityContextHolder.getContext().setAuthentication(authRequest);
			redirectAttributes.addFlashAttribute("message", "account.message.sucess");
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
