package ddareunging.ddareunging_server.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy!";
        // Health 체크용 API
    }
}
