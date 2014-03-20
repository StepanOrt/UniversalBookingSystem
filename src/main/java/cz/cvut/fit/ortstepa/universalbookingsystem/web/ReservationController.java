package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ReservationService;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ScheduleService;

@Controller
@RequestMapping("/resource/{resourceId}/schedule/{scheduleId}/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ScheduleService scheduleService;
	
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	private static final String VN_SCH_REDIRECT = "redirect:/resource/{resourceId}/schedule";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] {});
	}
	
	@RequestMapping(method = RequestMethod.PUT, params={"reserve"})
	public String reserve(Model model, @PathVariable Long resourceId, @PathVariable Long scheduleId, final RedirectAttributes redirectAttributes ) {
		reservationService.reserve(scheduleId);
		redirectAttributes.addFlashAttribute("success", "reservation.reserve.success");
		return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
	}
	
	@RequestMapping(method = RequestMethod.PUT, params={"cancel"})
	public String cancel(Model model, @PathVariable Long resourceId, @PathVariable Long scheduleId, final RedirectAttributes redirectAttributes) {
		reservationService.cancel(scheduleId);
		redirectAttributes.addFlashAttribute("success", "reservation.cancel.success");
		return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
	}
	
	@ExceptionHandler(ReservationService.NotEnoughCreditException.class)
	public String notEnoughCreditExceptionHandler() {
		return "forward:/error/" + "reservation.error.notEnouthCredit";
	}
	
	@RequestMapping("error/{message}")
	public String errorRedirect(@PathVariable Long resourceId, @PathVariable String message, final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", message);
		return VN_SCH_REDIRECT.replace("{resourceId}", resourceId.toString());
	}
}
