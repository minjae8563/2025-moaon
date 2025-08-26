package moaon.backend.techStack.repository;

import static moaon.backend.project.domain.QProject.project;
import static moaon.backend.techStack.domain.QTechStack.techStack;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moaon.backend.techStack.dto.TechStackUsageResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomizedTechStackRepositoryImpl implements CustomizedTechStackRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TechStackUsageResponse> findPopularTechStacksByProjects() {
        return jpaQueryFactory
                .select(Projections.constructor(
                        TechStackUsageResponse.class,
                        techStack.name.as("name"),
                        project.id.countDistinct().as("usageCount")
                ))
                .from(project)
                .join(project.techStacks, techStack)
                .groupBy(techStack.id, techStack.name)
                .orderBy(project.id.countDistinct().desc())
                .fetch();
    }
}
