package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.section.SectionDto;
import uz.yeoju.yeoju_app.payload.section.SectionDtoV2;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.SectionService;

import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/section")
@RequiredArgsConstructor
public class SectionController {
    public final SectionService sectionService;

    @PostMapping("/createSectionV2")
    public HttpEntity<?> createNewSection(@RequestBody SectionDtoV2 dto){
        return ResponseEntity.status(201).body(sectionService.saveOrUpdate(dto));
    }
    @GetMapping("/getSectionById")
    public HttpEntity<?> getSectionById(@CurrentUser User user,@RequestParam("id") String id){
        ApiResponse response = sectionService.getSection3ById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @GetMapping("/getStatisticsForRektor")
    public HttpEntity<?> getStatisticssForRektor(@CurrentUser User user,@RequestParam("sectionId") String sectionId,@RequestParam("date")
    @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(sectionService.getStatisticsForRektor(sectionId,date));
    }


    @GetMapping("/getStatistics")
    public HttpEntity<?> getStatistics(@CurrentUser User user){
        return ResponseEntity.ok(sectionService.getStatistics(user));
    }

    @GetMapping("/getStatisticsForDekan")
    public HttpEntity<?> getStatisticsForDekan(@CurrentUser User user){
        return ResponseEntity.ok(sectionService.getStatisticsForDekan(user));
    }

    @GetMapping("/getStatisticsForSectionDashboard")
    public HttpEntity<?> getStatisticsForSectionDashboard(@RequestParam("index") Integer index,@RequestParam("sectionId") String sectionId){
        return ResponseEntity.ok(sectionService.getStatisticsForSectionDashboard(index,sectionId));
    }

    @GetMapping("/getStaffComeCountTodayStatistics")
    public HttpEntity<?> getStaffComeCountTodayStatistics(@CurrentUser User user,@RequestParam("s") Boolean s){
        return ResponseEntity.ok(sectionService.getStaffComeCountTodayStatistics(user.getId(),s));
    }


    @GetMapping("/getSectionDatas")
    public HttpEntity<?> getSectionDatas(@CurrentUser User user){
        return ResponseEntity.ok(sectionService.getSectionDatas(user.getId()));
    }

    @GetMapping("/getSectionDatasByUserId")
    public HttpEntity<?> getSectionDatasByUserId(@CurrentUser User user){
        return ResponseEntity.ok(sectionService.getSectionDatasByUserId(user.getId()));
    }

    @GetMapping("/allSections")
    public HttpEntity<?> getAllSections(){
        return ResponseEntity.ok(sectionService.findAll());
    }

    @GetMapping("/getSectionById/{id}")
    public HttpEntity<?> getSectionById(@PathVariable String id){
        return ResponseEntity.ok(sectionService.findById(id));
    }

    @PostMapping("/createSection")
    public HttpEntity<?> createNewSection(@RequestBody SectionDto dto){
        return ResponseEntity.status(201).body(sectionService.saveOrUpdate(dto));
    }

    @PostMapping("/updateSection")
    public HttpEntity<?> updateSection(@RequestBody SectionDto dto){
        return ResponseEntity.status(202).body(sectionService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteSection/{id}")
    public HttpEntity<?> deleteSection(@PathVariable String id){
        return ResponseEntity.status(204).body(sectionService.deleteById(id));
    }
}
