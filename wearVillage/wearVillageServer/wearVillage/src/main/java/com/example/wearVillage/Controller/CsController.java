package com.example.wearVillage.Controller;


import com.example.wearVillage.DAO.AskPostDAO;
import com.example.wearVillage.DAO.FaqPostDAO;
import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import com.jcraft.jsch.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class CsController {

    private final AskPostDAO askPostDAO;
    private final FaqPostDAO faqPostDAO;

    @GetMapping("/askpost")
    public String askpost(){
        log.info("askpost 1:1 문의 작성화면 반환");

        return "askpost";
    }

    // 쿼리파라미터
    @GetMapping("/askdetail/{askpostid}")
    public String askdetail(@PathVariable String askpostid,
                            Model model){

        AskObject askObject = askPostDAO.askFind(askpostid);
        model.addAttribute("askObject",askObject);
        return "askdetail";
    }

    @GetMapping("/asklist")    // GET http://localhost:8090/asklist
    public String asklist(Model model){
        log.info("asklist 화면 반환");

        List<AskObject> askObjectList = askPostDAO.askFindAll();
        model.addAttribute("askObjectList",askObjectList);

        return "asklist";
    }

    @GetMapping("/FAQlist")         // GET http://localhost:8090/FAQlist
    public String FAQlist(Model model) {
        List<FaqObject> faqObjectList = faqPostDAO.faqFindAll();
        model.addAttribute("FAQlist",faqObjectList);

        log.info("FAQlist={}",faqObjectList);
        return "FAQlist";
    }

    @GetMapping("/FAQ/{faqpostid}")             // GET http://localhost:8090/FAQ/{PathVariable}
    public String FAQ(@PathVariable String faqpostid, Model model) {
        log.info("FAQ 화면 반환");

        FaqObject faqObject = faqPostDAO.faqFind(faqpostid);
        model.addAttribute("faqObject",faqObject);
        return "FAQ";
    }

    @GetMapping("/supportmain")     // GET http://localhost:8090/supportmain
    public String supportMain() {
        log.info("supportmain 화면 반환");

        return "support_main";
    }

    /////////////////////////// POST //////////////////////////

    @PostMapping("/askwrite")              // POST http://localhost:8090/askwrite
    public String askWrite(AskObject askObject, Model model, HttpSession httpSession) {

        Object sessionId = httpSession.getAttribute("id");
        askObject.setId(sessionId.toString());
        log.info("askObject={}",askObject);
        Long askpostid = askPostDAO.askWrite(askObject);
        long l_askpostid = askpostid.longValue();


        if(l_askpostid != -1) {
            model.addAttribute("askdetail", askObject);
            return "redirect:/askdetail/"+l_askpostid; // 상세 페이지로 갈때 컨트롤러 엔드포인트
        } else {
            return "redirect:/asklist"; // 실패 시 리스트로
        }
    }

    //삭제
    @PostMapping("/askdelete")
    public String askDelete(AskObject askObject, HttpSession httpSession) {
        String askpostid = askObject.getAskpostid();

        try {
            boolean result = askPostDAO.askDelete(askpostid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }





}
