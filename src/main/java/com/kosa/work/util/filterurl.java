package com.kosa.work.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kosa.work.service.model.MemberVO;

@WebFilter("/*")
public class filterurl implements Filter {
	
    public filterurl() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

    	String requestURI = httpRequest.getRequestURI();
    	String contextPath = httpRequest.getContextPath();
    	String action = requestURI.substring(contextPath.length());
    	HttpSession session = httpRequest.getSession();
    	
//    	System.out.println("requestURI = " + requestURI);
//    	System.out.println("contextPath = " + contextPath);
//    	System.out.println("action = " + action);
       
        if (session.getAttribute("loginMember") == null) {
//            if (action.equals("/board/boardWrite.do")) {
//                httpResponse.sendRedirect(contextPath + "/board/boardList.do");
//                return;
//            } else if (action.equals("/board/boardUpdateInfo.do")) {
//            	httpResponse.sendRedirect(contextPath + "/board/boardList.do");
//            	return;
//            } else if (action.equals("/board/boardUpdateInfo.do")) {
//           	 	httpResponse.sendRedirect(contextPath + "/board/boardList.do");
//           	 	return;
//            } else if (action.equals("/member/memberList.do")) {
//            	httpResponse.sendRedirect(contextPath + "/main.do");
//            	return; 
//            else if (action.equals("/admin/memberList.do")) {
//            	httpResponse.sendRedirect(contextPath + "/main.do");
//        	 	return;
//            } else if (action.equals("")) {
//            	httpResponse.sendRedirect(contextPath + "/main.do");
//        	 	return;
//            }
        } else {
//        	MemberVO mem = (MemberVO) session.getAttribute("loginMember");
//        	if (!mem.getMemberid().equals("admin")) {
//        		if (action.equals("/admin/*.do")) {
//	                 httpResponse.sendRedirect(contextPath + "/main.do");
//	                 return;
//	             } else if (action.equals("")) {
//	             	httpResponse.sendRedirect(contextPath + "");
//	             	return;
//	             }
//        		
//        	}
	        	 
        }
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
