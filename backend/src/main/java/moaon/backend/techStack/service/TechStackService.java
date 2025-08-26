package moaon.backend.techStack.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moaon.backend.techStack.dto.TechStackUsageResponse;
import moaon.backend.techStack.repository.TechStackRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackRepository techStackRepository;

    public List<TechStackUsageResponse> getPopularTechStacksByProjects() {
        return techStackRepository.findPopularTechStacksByProjects();
    }
}
