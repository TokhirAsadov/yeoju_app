package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.repository.DekanRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dekan")
@RequiredArgsConstructor
public class DekanController {
    private final DekanRepository dekanRepository;

    @GetMapping("/get")
    public HttpEntity<?> get(@RequestParam("facultyId") String facultyId,
                             @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                             @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {

        return ResponseEntity.ok(dekanRepository.getCourseStatisticsForDekan(facultyId,startTime,endTime));
    }
}
