package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.BoardDAO;
import com.example.mapper.BoardMapper;
import com.example.vo.BoardVO;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	
	@Autowired
	private BoardDAO bDAO =null;
//	@Autowired
//	private BoardMapper boardMapper =null;
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model, @RequestParam(value="no", defaultValue="0") int no) {
		BoardVO vo = bDAO.selectBoardOne(no);		
		model.addAttribute("vo", vo);		
		return "/board/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @ModelAttribute BoardVO obj, @RequestParam MultipartFile[] img) throws IOException{
		
		if(img != null) {
			for(MultipartFile one : img) {
				if(one.getSize() > 0) {
					obj.setBrd_img(one.getBytes());
				}				
			}
		}
		bDAO.updateBoard(obj);		
		
		return "redirect:" + request.getContextPath() + "/board/content?no=" + obj.getBrd_no();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, @RequestParam(value="no", defaultValue="0") int no) {
		BoardVO obj = new BoardVO();
		obj.setBrd_no(no);
		int ret = bDAO.deleteBoard(obj);
//		int ret = boardMapper.deleteBoard(obj);
		if(ret > 0) {
			return "redirect:" + request.getContextPath() + "/board/list";
		}
		return "redirect:" + request.getContextPath() + "/board/content?no=" + no;
	}
	
	
	//127.0.0.1:8080/board/getimg?no=11
	@RequestMapping(value="/getimg")
	public ResponseEntity<byte[]> getimg(@RequestParam("no") int no, HttpServletRequest request) {
		BoardVO obj = bDAO.selectBoardImg(no);
		try {
			if (obj.getBrd_img().length > 0) {
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.IMAGE_JPEG);
				ResponseEntity<byte[]> ret = new ResponseEntity<byte[]>(obj.getBrd_img(), header, HttpStatus.OK);
				return ret;
			}
			return null;
		}catch(Exception e){
			try{//InputStream in = request.getServletContext().getResourceAsStream() -> /src/main/webapp
			InputStream in = request.getServletContext().getResourceAsStream("/resources/img/default.jpg");
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.IMAGE_JPEG);
			ResponseEntity<byte[]> ret = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), header, HttpStatus.OK);
			return ret;
			}catch(Exception e1) {
				return null;
			}
		}
	}
	
	@RequestMapping(value = "/insertBatch")
	public String insertbatch() {
		return "/board/insertBatch";
	}
	@RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
	public String insertbatchpost(@RequestParam("title[]") String[] title, 
			@RequestParam("content[]") String[] content, 
			@RequestParam("id[]") String[] id) {
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		for(int i=0; i<title.length; i++) {
			BoardVO obj = new BoardVO();
			obj.setBrd_title(title[i]);		
			obj.setBrd_content(content[i]);
			obj.setBrd_id(id[i]);
			
			list.add(obj);
		}
		bDAO.insertBatch(list);
		
		return "redirect:/board/list";
	}
	@RequestMapping(value = "/insertBatch", method = RequestMethod.GET)
	public String insertBoardBatchpost(HttpSession httpSession, Model model) {
		String userid = (String) httpSession.getAttribute("SESSION_ID");
		if(userid == null) {
			return "redirect:/member/login";
		}
		model.addAttribute("userid", userid);
		return "/board/insertBatch";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession httpSession, HttpServletRequest request ,
			@RequestParam(value="page", defaultValue = "0", required = false) int page,
			@RequestParam(value="text", defaultValue = "", required = false) String text) {
		
		
		if(page==0) {
			return "redirect:"+request.getContextPath()+"/board/list?page=1";
		}
		httpSession.setAttribute("SESSION_BOARD_HIT_CHECK", 1);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", page*7-6);
		map.put("end", page*7);
		map.put("text", text);		
		
		List<BoardVO> list = bDAO.selectBoard(map);
		model.addAttribute("list", list);
		
		int cnt = bDAO.countBoard(text);		
		model.addAttribute("cnt", (cnt-1)/7+1);
		//System.out.println((int)
		return "/board/list";		
	}
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public String content(Model model, HttpSession httpSession, @RequestParam(value="no", defaultValue = "0", required = false) int no) {
		if(no == 0) {
			return "redirect:/board/list";
		}
		
		Integer chk = (Integer)httpSession.getAttribute("SESSION_BOARD_HIT_CHECK");
		if(chk != null) {
			if(chk == 1) {
				bDAO.updateHit(no);
			}
			httpSession.setAttribute("SESSION_BOARD_HIT_CHECK", 0);
		}
		
		BoardVO obj = bDAO.selectBoardOne(no);		
		model.addAttribute("obj", obj);
		
		int p = bDAO.selectBoardPrev(no);
		model.addAttribute("prev", p);
		
		int n = bDAO.selectBoardNext(no);
		model.addAttribute("next", n);
		
		return "/board/content";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertBoard(HttpSession httpSession, Model model) {
		String userid = (String) httpSession.getAttribute("SESSION_ID");
		if(userid == null) {
			return "redirect:/member/login";
		}
		model.addAttribute("userid", userid);
		return "/board/insert";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertBoardPost(@ModelAttribute BoardVO obj, @RequestParam MultipartFile[] brd_image) throws IOException {
		if(brd_image != null && brd_image.length > 0) {
			for(MultipartFile one : brd_image) {
				obj.setBrd_img(one.getBytes());
			}
		}
		//DAO로 obj값 전달하기
		bDAO.insertBoard(obj);
		
		return "redirect:/";
	}
	
}
