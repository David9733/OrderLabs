package org.example.groworders.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    // blue pod
    @GetMapping("/blue")
    public String stable() {
        return "blue deploy";
    }

    // green pod
    @GetMapping("/green")
    public String canary() {
        return "green deploy";
    }
}
