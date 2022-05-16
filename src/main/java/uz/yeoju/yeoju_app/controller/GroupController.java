package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.service.useServices.GroupService;

@RestController
@RequestMapping("/v1/group")
@RequiredArgsConstructor
public class GroupController {

    public final GroupService groupService;

    @GetMapping("/allGroups")
    public HttpEntity<?> allGroups(){
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/getGroupByIdOrName/{id}")
    public HttpEntity<?> getGroupByIdOrName(
            @PathVariable String id,
            @RequestParam String name
    ){
        return id!=null ? ResponseEntity.ok(groupService.findById(id))
                :
                ResponseEntity.ok(groupService.findGroupByName(name));
    }

    @PostMapping("/createGroup")
    public HttpEntity<?> createNewGroup(@RequestBody GroupDto dto){
        return ResponseEntity.status(201).body(groupService.saveOrUpdate(dto));
    }

    @PostMapping("/updateGroup")
    public HttpEntity<?> updateGroup(@RequestBody GroupDto dto){
        return ResponseEntity.status(202).body(groupService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteGroup/{id}")
    public HttpEntity<?> deleteGroup(@PathVariable String id){
        return ResponseEntity.status(204).body(groupService.deleteById(id));
    }

    @GetMapping("/findGroupsByLevel/{level}")
    public HttpEntity<?> findGroupsByLevel(@PathVariable Integer level){
        return ResponseEntity.ok(groupService.findGroupsByLevel(level));
    }
    @GetMapping("/findGroupsByFacultyId/{faculty_id}")
    public HttpEntity<?> findGroupsByFacultyId(@PathVariable String faculty_id){
        return ResponseEntity.ok(groupService.findGroupsByFacultyId(faculty_id));
    }

}
