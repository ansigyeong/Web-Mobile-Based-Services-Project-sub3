package com.ssafy.sk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ssafy.sk.PageNavigation;
import com.ssafy.sk.dto.Question;
import com.ssafy.sk.dto.Reply;
import com.ssafy.sk.dto.User;
import com.ssafy.sk.service.QuestionService;
import com.ssafy.sk.service.ReplyService;
import com.ssafy.sk.service.UserService;


@Controller
public class MainController extends HttpServlet {

	@Autowired
    UserService userService;
	@Autowired
	QuestionService questionService;
	@Autowired
	ReplyService replyService;
	
	@GetMapping("signin")
	public String signinForm() {
		return "user/join";
	}
	
	@PostMapping("signin")
	public String signin(String uid, String name, String email, String pw, String img, String lang) {
		User user = new User(uid, name, email, pw, img, lang);
		System.out.println(user);
		try {
			userService.signIn(user);
			System.out.println(user);
			return "user/joinsuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}

	@GetMapping("login")
	public String loginForm() {
		return "user/login";
	}
	
	@PostMapping("login")
	public String login(String uid, String pw, HttpSession session) {
		User user;
		try {
			user = userService.login(uid, pw);
			session.setAttribute("userinfo", user);
			return "redirect:/main";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) throws IOException {
		session.invalidate();
		return "redirect:/main";
	}
	
	@GetMapping("/")
	public String indexForm(Model model) {
		return "index";
	}
	
	
	@GetMapping("main")
	public String newIndexForm() {
		return "index";
	}
	
	@GetMapping("intro")
	public String intro() {
		return "intro";
	}
	
	@GetMapping("question")
	public String questionView(Model model) {
		try {
			List<Question> list = questionService.questionList();
			model.addAttribute("questions", list);
			List<Integer> list2 = replyService.replyCount();
			model.addAttribute("rc",list2);
			return "question/view";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}
		
	@GetMapping("writeQuestion")
	public String writeForm() {
		return "question/write";
	}
	
	@PostMapping("writeQuestion")
	public String writeQuestion(String writer, String title, String contents, String lang) {
		try {
			questionService.writeQuestion(writer, title, contents, lang);
			return "question/writesuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}
	
	@PostMapping("writeReply")
	public String writeReply(String writer, String contents, int que_no) {
		try {
			replyService.writeReply(writer, contents, que_no);
			return "question/writesuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}
	

	@PostMapping("modifyQuestion")
	public String modifyQuestion(int que_no, String title, String contents, String lang) throws ServletException, IOException {
		try {
			questionService.modifyQuestion(que_no, title, contents, lang);
			return "question/modifysuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}

	@GetMapping("modifyQuestion")
	public String modifyForm() {
		return "question/modify";
	}

	@GetMapping("deleteQuestion")
	public String deleteQuestion(int que_no) {
		try {
			questionService.deleteQuestion(que_no);
			return "question/deletequestion";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}

	@GetMapping("selectQuestion")
	public String selectQuestion(Model model, int que_no, HttpSession session) {
		try {
			List<Reply> list = replyService.replyList(que_no);
			model.addAttribute("replys", list);
			Question question = questionService.oneQuestion(que_no);
			session.setAttribute("questioninfo", question);
			return "question/detailquestion";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/error";
		}
	}

	

}
