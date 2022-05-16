package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.service.useServices.LessonService;

@RestController
@RequestMapping("/v1/lesson")
@RequiredArgsConstructor
public class LessonController {

    public final LessonService lessonService;

    @GetMapping("/allLessons")
    public HttpEntity<?> allLessons(){
        return ResponseEntity.ok(lessonService.findAll());
    }

    @GetMapping("/getLessonById/{id}")
    public HttpEntity<?> getLessonById(
            @PathVariable String id,
            @RequestParam String kafedraId,
            @RequestParam String facultyId)
    {

        return id!=null ? ResponseEntity.ok(lessonService.findById(id))
                :
                kafedraId!=null ? ResponseEntity.ok(lessonService.findLessonsByKafedraId(kafedraId))
                :
                ResponseEntity.ok(lessonService.findLessonsByFacultyId(facultyId));
    }

    @PostMapping("/createLesson")
    public HttpEntity<?> createNewLesson(@RequestBody LessonDto dto){
        return ResponseEntity.status(201).body(lessonService.saveOrUpdate(dto));
    }

    @PostMapping("/updateLesson")
    public HttpEntity<?> updateLesson(@RequestBody LessonDto dto){
        return ResponseEntity.status(202).body(lessonService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteLesson/{id}")
    public HttpEntity<?> deleteLesson(@PathVariable String id){
        return ResponseEntity.status(204).body(lessonService.deleteById(id));
    }
}
