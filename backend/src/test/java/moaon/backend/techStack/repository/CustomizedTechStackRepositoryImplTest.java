package moaon.backend.techStack.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import moaon.backend.fixture.Fixture;
import moaon.backend.fixture.ProjectFixtureBuilder;
import moaon.backend.fixture.RepositoryHelper;
import moaon.backend.global.config.QueryDslConfig;
import moaon.backend.techStack.domain.TechStack;
import moaon.backend.techStack.dto.TechStackUsageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({RepositoryHelper.class, QueryDslConfig.class})
class CustomizedTechStackRepositoryImplTest {

    @Autowired
    private CustomizedTechStackRepositoryImpl repository;

    @Autowired
    private RepositoryHelper helper;

    @DisplayName("프로젝트에서 많이 사용된 순으로 테크 스택을 정렬한다.")
    @Test
    void findPopularTechStacksByProjects() {
        // given
        TechStack java = Fixture.anyTechStack();
        TechStack spring = Fixture.anyTechStack();
        TechStack mySQL = Fixture.anyTechStack();
        TechStack docker = Fixture.anyTechStack();
        TechStack aws = Fixture.anyTechStack();
        TechStack ec2 = Fixture.anyTechStack();

        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java, spring, mySQL, docker, aws, ec2)
                        .build()
        );

        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java, spring, mySQL, docker, aws)
                        .build()
        );
        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java, spring, mySQL, docker)
                        .build()
        );
        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java, spring, mySQL)
                        .build()
        );
        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java, spring)
                        .build()
        );
        helper.save(
                new ProjectFixtureBuilder()
                        .techStacks(java)
                        .build()
        );

        TechStackUsageResponse javaWithCount6 = new TechStackUsageResponse(java.getName(), 6);
        TechStackUsageResponse springWithCount5 = new TechStackUsageResponse(spring.getName(), 5);
        TechStackUsageResponse mySQLWithCount4 = new TechStackUsageResponse(mySQL.getName(), 4);
        TechStackUsageResponse dockerWithCount3 = new TechStackUsageResponse(docker.getName(), 3);
        TechStackUsageResponse awsWithCount2 = new TechStackUsageResponse(aws.getName(), 2);
        TechStackUsageResponse ec2WithCount1 = new TechStackUsageResponse(ec2.getName(), 1);

        // when
        List<TechStackUsageResponse> stacksByProjects = repository.findPopularTechStacksByProjects();

        // then
        assertThat(stacksByProjects).containsExactly(
                javaWithCount6,
                springWithCount5,
                mySQLWithCount4,
                dockerWithCount3,
                awsWithCount2,
                ec2WithCount1
        );
    }
}
