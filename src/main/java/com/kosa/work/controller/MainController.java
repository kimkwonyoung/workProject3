package com.kosa.work.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosa.work.service.impl.BoardServiceImpl;
import com.kosa.work.service.model.common.SearchVO;

import lombok.extern.log4j.Log4j;
/**메인 컨트롤러
 * @author kky
 *
 */
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class MainController extends PrtController {
	@Autowired
	private BoardServiceImpl _boardService;
	
	@RequestMapping("main.do")
	public String mainIndex(SearchVO search, Model model) throws Exception {
    	log.info(">>>>>>>>>>>> 세션 값 = " + getSession().getAttribute("loginMember"));
    	model.addAttribute("noticeList", _boardService.mainList(search).get("noticeList"));
    	model.addAttribute("nomalList", _boardService.mainList(search).get("nomalList"));
		
		return "index";
	}
}
