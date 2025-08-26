package moaon.backend.api.techStack;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import moaon.backend.api.BaseApiTest;
import moaon.backend.fixture.Fixture;
import moaon.backend.fixture.ProjectFixtureBuilder;
import moaon.backend.fixture.RepositoryHelper;
import moaon.backend.global.config.QueryDslConfig;
import moaon.backend.techStack.domain.TechStack;
import moaon.backend.techStack.dto.TechStackUsageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({RepositoryHelper.class, QueryDslConfig.class})
public class TechStackApiTest extends BaseApiTest {

    @Autowired
    private RepositoryHelper helper;

    @DisplayName("GET /stacks/trending/projects : 프로젝트에서 인기 있는 아티클 제공")
    @Test
    void getPopularTechStacksByProjects() {
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

        // when
        TechStackUsageResponse[] techStackUsageResponses = RestAssured.given(documentationSpecification).log().all()
                .when().get("/stacks/trending/projects")
                .then().log().all()
                .statusCode(200)
                .extract().as(TechStackUsageResponse[].class);

        // then
        assertThat(techStackUsageResponses)
                .extracting(TechStackUsageResponse::name)
                .containsExactly(
                        java.getName(),
                        spring.getName(),
                        mySQL.getName(),
                        docker.getName(),
                        aws.getName(),
                        ec2.getName()
                );
    }
}
