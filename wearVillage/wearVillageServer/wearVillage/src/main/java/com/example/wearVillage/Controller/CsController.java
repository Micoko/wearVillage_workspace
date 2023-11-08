package com.example.wearVillage.Controller;


import com.example.wearVillage.DAO.AskPostDAO;
import com.example.wearVillage.DAO.FaqPostDAO;
import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class CsController {

    private final AskPostDAO askPostDAO;
    private final FaqPostDAO faqPostDAO;

    @GetMapping("/askpost") // 1:1 문의 등록 페이지 호출
    public String askpost(HttpSession session){
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            return "askpost";
        }
    }

    // 쿼리파라미터
    @GetMapping("/askdetail/{askpostid}")
    public String askdetail(@PathVariable String askpostid,
                            Model model,
                            HttpSession session){
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            AskObject askObject = askPostDAO.askFind(askpostid);
            model.addAttribute("askObject",askObject);
            return "askdetail";
        }
    }

    @GetMapping("/asklist")    // GET http://localhost:8090/asklist
    public String asklist(HttpSession session, Model model){
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}",sid);
            List<AskObject> askObjectList = askPostDAO.askFindAll(sid);
            model.addAttribute("askObjectList", askObjectList);
            return "asklist";
        }
    }

    @GetMapping("/FAQlist")         // GET http://localhost:8090/FAQlist
    public String FAQlist(Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            List<FaqObject> faqObjectList = faqPostDAO.faqFindAll();
            model.addAttribute("FAQlist",faqObjectList);
            log.info("FAQlist={}",faqObjectList);
            return "FAQlist";
        }
    }

    @GetMapping("/FAQ/{faqpostid}")             // GET http://localhost:8090/FAQ/{PathVariable}
    public String FAQ(@PathVariable String faqpostid, Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            FaqObject faqObject = faqPostDAO.faqFind(faqpostid);
            model.addAttribute("faqObject",faqObject);
            return "FAQ";
        }
    }

    @GetMapping("/supportmain")     // GET http://localhost:8090/supportmain
    public String supportMain(HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            return "support_main";
        }
    }

    @PostMapping("/askwrite")              // POST http://localhost:8090/askwrite
    public String askWrite(AskObject askObject, Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            Object sessionId = session.getAttribute("id");
            askObject.setId(sessionId.toString());
            String askpostid = askPostDAO.askWrite(askObject);
            if(!(askpostid.isEmpty())) { // 성공
                model.addAttribute("askdetail", askObject);
                return "redirect:/askdetail/"+askpostid; // 상세 페이지로 갈때 컨트롤러 엔드포인트
            } else {    // 실패
                return "redirect:/errorpage"; // 실패 시 에러페이지
            }
        }
    }

    //삭제
    @GetMapping("/askdelete/{askpostid}")
    public String askDelete(@PathVariable("askpostid") String askpostid, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String id = (String) session.getAttribute("id");
            log.info("sid={}", id);
            boolean b_result = askPostDAO.askDelete(askpostid, id);
            if(b_result) {
                return "redirect:/asklist";
            } else {
                return "redirect:/errorpage";
            }
        }
    }





}
