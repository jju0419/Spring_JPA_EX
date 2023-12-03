package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {//Model : 컨트롤러에서 뷰로 데이터 전송때 쓰임
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}
