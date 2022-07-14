package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.service.useServices.LessonService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/lesson")
@RequiredArgsConstructor
public class LessonController {

    public final LessonService lessonService;

    @PostMapping("/multiSaved")
    public HttpEntity<?> multiSaved(@RequestBody List<String> subjectNames){
        return ResponseEntity.status(201).body(lessonService.multiSaved(subjectNames));
    }

    @GetMapping("/allLessons")
    public HttpEntity<?> allLessons(){
        return ResponseEntity.ok(lessonService.findAll());
    }

    @GetMapping("/getLessonById/{id}")
    public HttpEntity<?> getLessonById(@PathVariable String id)
    {
        return ResponseEntity.ok(lessonService.findById(id));
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
