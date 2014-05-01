package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import java.util.List;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.api.services.oauth2.Oauth2.Userinfo;
import com.google.api.services.oauth2.model.Userinfoplus;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.PasswordChangeForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.RegistrationForm;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.TopupForm;

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
	private static final String VN_ACC_ADMIN_LIST = "account/admin/list";
	private static final String VN_ACC_ADMIN_FORM = "account/admin/edit";
	private static final String VN_ACC_ADMIN_TOPUP = "account/admin/topup";
	private static final String VN_ACC_ADMIN_OK = "redirect:/account?admin";

	@Autowired 
	private AccountService accountService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authMgr;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"currentPassword", "password", "confirmPassword", "firstName", "lastName",
			"email", "acceptTerms", "enabled", "internal", "amount", "credit"
		});
	}
	
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String getPasswordChangeForm(Model model) {
		model.addAttribute("form", new PasswordChangeForm());
		return VN_PASS_FORM;
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.POST)
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

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		log.debug("GET registration auth=" + SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("form", new RegistrationForm());
		return VN_REG_FORM;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
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
	
	@RequestMapping(method = RequestMethod.GET, params = {"admin"})
	public String list(Model model, @RequestParam(required=false) String success, final RedirectAttributes redirectAttributes) {
		if (success != null) redirectAttributes.addFlashAttribute("success", success);
		return page(model);
	}


	private String page(Model model) {
		List<Account> list = accountService.getAll();
		model.addAttribute("accountList", list);
		return VN_ACC_ADMIN_LIST;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model,  @RequestParam(required=false) String failed, final RedirectAttributes redirectAttributes) {
		if (failed != null)	{
			redirectAttributes.addFlashAttribute("error", "account.login.fail");
			return "redirect:/account/login";
		}
		return "account/login";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String item(Model model, @PathVariable Long id) {
		return adminForm(model, accountService.get(id));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String itemPut(Model model, @PathVariable Long id, Account account, BindingResult result, final RedirectAttributes redirectAttributes) {
		account.setId(id);		
		accountService.update(id, account, result);
		if (!result.hasErrors()) {
			redirectAttributes.addFlashAttribute("success", "account.message.sucess");
			return VN_ACC_ADMIN_OK;
		}
		return adminForm(model, account);
	}
	

	private String adminForm(Model model, Account account) {
		model.addAttribute("account", account);
		return VN_ACC_ADMIN_FORM;
	}
	
	@RequestMapping(value="/{id}/topup", method=RequestMethod.GET)
	public String topupGet(Model model, @PathVariable Long id) {
		return topup(model, accountService.get(id), new TopupForm());
	}
	
	@RequestMapping(value="/{id}/topup", method=RequestMethod.PUT)
	public String topupPut(Model model, TopupForm topup, BindingResult result, @PathVariable Long id,  final RedirectAttributes redirectAttributes) {
		if (topup.getAmount() > 0) {
			accountService.topup(id,  topup.getAmount());			
			redirectAttributes.addFlashAttribute("success", "account.admin.topup.success");
			return VN_ACC_ADMIN_OK;
		}
		result.rejectValue("amount", "account.admin.topup.zeroOrLess");
		return topup(model, accountService.get(id), topup);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, params={"makeAdmin"})
	public String makeAdmin(Model model, @PathVariable Long id,  final RedirectAttributes redirectAttributes) {
		accountService.makeAdmin(id);
		redirectAttributes.addFlashAttribute("success", "account.admin.makeAdmin.success");
		return VN_ACC_ADMIN_OK;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, params={"makeUser"})
	public String makeUser(Model model, @PathVariable Long id,  final RedirectAttributes redirectAttributes) {
		accountService.makeUser(id);
		redirectAttributes.addFlashAttribute("success", "account.admin.makeUser.success");
		return VN_ACC_ADMIN_OK;
	}
	
	private String topup(Model model, Account account, TopupForm topup) {
		model.addAttribute("account", account);
		model.addAttribute("topup", topup);
		return VN_ACC_ADMIN_TOPUP;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAccountForm(Model model, HttpSession session) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.getAccountByEmail(email);
		Userinfoplus userInfo = accountService.getGoogleUserinfo();
		model.addAttribute("googleUserinfo", userInfo);
		if (userInfo == null) {
			model.addAttribute("googleConnectUrl", accountService.googleConnectUrl(session));
		}
		model.addAttribute("form", AccountForm.create(account));
		return VN_ACC_FORM;
	}
	
	@RequestMapping(value = "oauth2callback", method = RequestMethod.GET, params = { "state" })
	public String oauth2callback(Model model, @RequestParam(required=false) String error, @RequestParam String state, @RequestParam(required=false) String code, HttpSession session) {
		if (error==null && code != null) {
			accountService.authorizeGoogle(code, state, session);
		}
		return VN_ACC_OK;
	}
	
	@RequestMapping(method=RequestMethod.PUT, params={"forgetGoogle"})
	public String forgetGoogle(Model model) {
		accountService.forgetGoogle();
		return VN_ACC_OK;
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
