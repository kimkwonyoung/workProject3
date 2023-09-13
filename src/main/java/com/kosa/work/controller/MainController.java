package com.kosa.work.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.model.common.SearchVO;


/**메인 컨트롤러
 * @author kky
 *
 */
@Controller
public class MainController extends PrtController {
	
	@Autowired
	private BoardServiceImpl _boardService;
	
	@RequestMapping("main.do")
	public String mainIndex(SearchVO search, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		System.out.println("세션 값 = " + request.getSession().getAttribute("loginMember"));
    	
    	model.addAttribute("noticeList", _boardService.mainList(search).get("noticeList"));
    	model.addAttribute("nomalList", _boardService.mainList(search).get("nomalList"));
		
		return "index";
	}
}
