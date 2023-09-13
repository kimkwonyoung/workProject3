package com.kosa.work.controller;


import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosa.work.service.GlobalProperty;

/**
 * 에러 컨트롤러
 * @author kky
 */
@Controller
@RequestMapping("/common/error")
public class ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@Resource(name="globalProperty")
	GlobalProperty configBean;
	
	@Autowired
	ServletContext servletContext;
	
	private GlobalProperty getConfig()
	{
		return this.configBean;
	}
	
	private ServletContext getServletContext() {
		return this.servletContext;
	}
	
	@RequestMapping("/throw.do")
	public String thrwoable(Model model) {
		model.addAttribute("messageEng", "Throwable");
		model.addAttribute("message", "예외가 발생했습니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/exception.do")
	public String exception(Model model) {
		model.addAttribute("messageEng", "Exception");
		model.addAttribute("message", "예외가 발생했습니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/400.do")
	public String error400(Model model) {
		model.addAttribute("messageEng", "Bad Request");
		model.addAttribute("message", "잘못된 요청입니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/403.do")
	public String error403(Model model) {
		model.addAttribute("messageEng", "Forbidden");
		model.addAttribute("message", "접근할 수 없는 요청입니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/404.do")
	public String error404(Model model) {
		model.addAttribute("messageEng", "Not Found");
		model.addAttribute("message", "잘못된 요청입니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/405.do")
	public String error405(Model model) {
		model.addAttribute("messageEng", "Method Not Allowed");
		model.addAttribute("message", "요청된 메서드는 허용되지 않습니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/500.do")
	public String error500(Model model) {
		model.addAttribute("messageEng", "Internal Server Error");
		model.addAttribute("message", "요청된 메서드는 허용되지 않습니다.");
		return getConfig().getViewError();
	}
	
	@RequestMapping("/503.do")
	public String error503(Model model) {
		model.addAttribute("messageEng", "Service Temporarily Unavailable");
		model.addAttribute("message", "서버를 일시적으로 사용할 수 없습니다.");
		return getConfig().getViewError();
	}
	
}
