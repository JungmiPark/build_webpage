package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.MemberDao;
import com.example.vo.memberVO;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberDao mDAO = null; // MemberDAO mDAO = new MemberDAO()

	@RequestMapping(value = "/memberlist")
	public String memberlist(Model model) {
        //Model model = new Model();
		List<memberVO> list = mDAO.selectMemberList();
//		for(memberVO tmp : list) {
//			System.out.println(tmp.getUserid());
//		}
		
//		model.addAttribute("name", "가나다");
		model.addAttribute("list", list); //hashmap<key, value> Controller -> VO로 값 전달
		return "memberlist";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginpost(@ModelAttribute memberVO obj, HttpSession httpSession) {
		memberVO obj1 = mDAO.selectMemberLogin(obj);//Dao로 전달
		if(obj1 != null) {//login 성공
			httpSession.setAttribute("SESSION_ID", obj.getUserid());
			return "redirect:/";
		}//login 실패
		return "redirect:/member/login";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "join"; // join.jsp를 표시하시오.
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinpost(@ModelAttribute memberVO obj) {
		System.out.println(obj.toString());
		int ret = mDAO.insertMember(obj);

		if (ret > 0) { // 회원가입 성공시
			return "redirect:/";
		}

		// 회원가입 실패시
		return "redirect:/member/join";
	}
}
