package com.itwill.gaebokchi.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.gaebokchi.dto.JoinPostCreateDto;
import com.itwill.gaebokchi.dto.JoinPostListDto;
import com.itwill.gaebokchi.dto.JoinPostSearchDto;
import com.itwill.gaebokchi.dto.JoinPostUpdateDto;
import com.itwill.gaebokchi.filter.AuthenticationFilter;
import com.itwill.gaebokchi.repository.JoinPost;
import com.itwill.gaebokchi.repository.User;
import com.itwill.gaebokchi.service.JoinPostService;
import com.itwill.gaebokchi.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/join")
public class JoinController {

	private final JoinPostService joinPostService;
	private final UserService userService;

	@ModelAttribute("loggedInUser")
	public User addLoggedInUserToModel(HttpSession session) {
		String userId = (String) session.getAttribute(AuthenticationFilter.SESSION_ATTR_USER);
		if (userId != null) {
			return joinPostService.getLoggedInUser(userId);
		}
		return null;
	}

	@GetMapping("/join_create")
	public void createJoinPost(@ModelAttribute("loggedInUser") User loggedInUser, Model model) {
		if (loggedInUser != null) {
			model.addAttribute("user", loggedInUser);
		}
	}

	@PostMapping("/join_create")
	public String create(JoinPostCreateDto dto, @ModelAttribute("loggedInUser") User loggedInUser) {
		if (loggedInUser != null) {
			log.debug("user={}", loggedInUser);
		}
		joinPostService.create(dto);
		return "redirect:/join/join_main";
	}

	@GetMapping("/join_main")
	public String viewJoinMain(@RequestParam(value = "teeoffDate", required = false) String teeoffDateStr,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int pageSize, Model model) {
		List<JoinPostListDto> posts;
		LocalDate today = LocalDate.now();
		List<LocalDate> dates = new ArrayList<>();
		int pageBlockSize = 10;

		// 오늘부터 5일간의 날짜 목록 생성
		for (int i = 0; i < 5; i++) {
			dates.add(today.plusDays(i));
		}

		// 검색 처리
		if ((category != null && !category.isEmpty()) && (keyword != null && !keyword.isEmpty())) {
			JoinPostSearchDto searchDto = new JoinPostSearchDto(category, keyword);
			posts = joinPostService.search(searchDto);
		} else if (teeoffDateStr != null && !teeoffDateStr.isEmpty()) {
			try {
				LocalDate teeoffDate = LocalDate.parse(teeoffDateStr);
				posts = joinPostService.findByTeeoffDate(teeoffDate);
			} catch (DateTimeParseException e) {
				log.error("날짜 형식 오류: {}", teeoffDateStr, e);
				posts = joinPostService.read();
			}
		} else {
			posts = joinPostService.getPagedPosts(page, pageSize);
		}

		int totalPosts = joinPostService.getTotalPostCount();
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
		int startPage = ((page - 1) / pageBlockSize) * pageBlockSize + 1;
		int endPage = Math.min(startPage + pageBlockSize - 1, totalPages);

		Map<String, String> userNicknames = userService.getUserNicknames();

		model.addAttribute("userNicknames", userNicknames);

		model.addAttribute("dates", dates);
		model.addAttribute("posts", posts);
		model.addAttribute("selectedCategory", category); // 선택된 카테고리를 모델에 추가
		model.addAttribute("keyword", keyword); // 검색어를 모델에 추가
		model.addAttribute("today", LocalDateTime.now()); // 오늘 날짜를 모델에 추가
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "join/join_main";
	}

	@GetMapping({ "/join_details", "/join_modify" })
	public void detailsjoinPost(@RequestParam("id") Integer id, @ModelAttribute("loggedInUser") User loggedInUser,
			@RequestParam(name = "commentId", required = false) Integer commentId, Model model) {

		if (loggedInUser != null) {
			log.debug("user={}", loggedInUser);
			model.addAttribute("user", loggedInUser);
		}

		Map<String, String> userNicknames = userService.getUserNicknames();

		model.addAttribute("userNicknames", userNicknames);
		JoinPost post = joinPostService.read(id);
		JoinPost previousPost = joinPostService.getPreviousPost(post.getTeeoff());
		JoinPost nextPost = joinPostService.getNextPost(post.getTeeoff());
		model.addAttribute("post", post);
		model.addAttribute("previousPost", previousPost);
		model.addAttribute("nextPost", nextPost);
	}

	@PostMapping("/update")
	public String update(JoinPostUpdateDto dto) {
		log.debug("update(dto={})", dto);
		int result = joinPostService.update(dto);
		log.debug("Update result: {}", result);
		return "redirect:/join/join_details?id=" + dto.getId();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") int id) {
		log.debug("delete(id={})", id);
		joinPostService.delete(id);
		return "redirect:/join/join_main";
	}
}
