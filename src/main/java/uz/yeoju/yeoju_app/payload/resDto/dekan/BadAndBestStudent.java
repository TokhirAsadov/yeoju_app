package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface BadAndBestStudent {
    String getId();

    @Value("#{@dekanRepository.getBestStudents(target.id)}")
    List<DekanBestStudent> getBestStudents();

    @Value("#{@dekanRepository.getAllStudentsOfFaculty(target.id)}")
    List<AllStudentsOfFaculty> getAllStudents();

    @Value("#{@dekanRepository.getFailsCountOfGroup(target.id)}")
    List<FailsCountOfGroup> getFailsOfGroup();
}
