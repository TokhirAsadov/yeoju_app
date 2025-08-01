package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.GetCountOfQuestionsWithType;

import java.util.List;

public interface TestQuestionV2Repository extends JpaRepository<TestQuestionV2,String> {

    long countByCourseIdAndType(String courseId, TestType type);

    @Query(value = "SELECT type, COUNT(*) AS questionCount " +
            "FROM test_question_v2 " +
            "WHERE course_id = :courseId " +
            "GROUP BY type", nativeQuery = true)
    List<GetCountOfQuestionsWithType> countQuestionsByTypeForCourse(@Param("courseId") String courseId);

    @Query(value = "SELECT type, COUNT(*) AS questionCount " +
            "FROM test_question_v2 " +
            "WHERE module_id = :moduleId " +
            "GROUP BY type", nativeQuery = true)
    List<GetCountOfQuestionsWithType> countQuestionsByTypeForModule(@Param("moduleId") String moduleId);


    @Query(value = "EXEC sp_GetRandomQuestionsByTestId :testId", nativeQuery = true)
    List<TestQuestionV2> getRandomQuestionsForTest(@Param("testId") String testId);

    @Query(
            value = "EXEC sp_GetRandomQuestionsByTestIdAndModuleId :testId, :moduleId",
            nativeQuery = true
    )
    List<TestQuestionV2> getRandomQuestionsByTestIdAndModuleId(
            @Param("testId") String testId,
            @Param("moduleId") String moduleId
    );


    List<TestQuestionV2> findAllByCourseId(String courseId);


}
