package gs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/log4j2")
public class Log4j2RestController extends AbstractController {

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public void print() {

    }
}
