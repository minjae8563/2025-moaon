package moaon.backend.techStack.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moaon.backend.techStack.dto.TechStackUsageResponse;
import moaon.backend.techStack.service.TechStackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stacks/trending")
@RequiredArgsConstructor
public class TrendingTechStackController {

    private final TechStackService techStackService;

    @GetMapping("/projects")
    public ResponseEntity<List<TechStackUsageResponse>> getPopularTechStacksByProjects() {
        return ResponseEntity.ok(techStackService.getPopularTechStacksByProjects());
    }
}
