package moaon.backend.techStack.repository;

import java.util.List;
import moaon.backend.techStack.dto.TechStackUsageResponse;

public interface CustomizedTechStackRepository {

    List<TechStackUsageResponse> findPopularTechStacksByProjects();
}
