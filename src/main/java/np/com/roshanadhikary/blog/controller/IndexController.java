package np.com.roshanadhikary.blog.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

	public String index() {
		return "index.html";
	}
}
