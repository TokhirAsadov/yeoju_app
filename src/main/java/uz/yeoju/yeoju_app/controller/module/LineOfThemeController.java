package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateThemeAndLine;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeAndLine;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.lineOfTheme.LineOfThemeService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/line")
@RequiredArgsConstructor
public class LineOfThemeController {
    private final LineOfThemeService service;

    @GetMapping("/getLinesByPlanId/{planId}")
    public HttpEntity<?> getLinesByPlanId(@CurrentUser User user, @PathVariable String planId) {
        return ResponseEntity.ok(service.getAllQueueByPlanId(planId));
    }

    @GetMapping("/getOldThemes/{subjectId}")
    public HttpEntity<?> getOldThemes(@CurrentUser User user, @PathVariable String subjectId) {
        return ResponseEntity.ok(service.getOldThemesForCreateLine(user.getId(), subjectId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createLineAndTheme")
    public HttpEntity<?> createLineAndTheme(@CurrentUser User user, @RequestBody CreateThemeAndLine dto){
        ApiResponse lineAndTheme = service.createLineAndTheme(dto);
        return ResponseEntity.status(lineAndTheme.isSuccess() ? 202 : 403).body(lineAndTheme);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/updateLineAndTheme")
    public HttpEntity<?> updateLineAndTheme(@CurrentUser User user, @RequestBody UpdateThemeAndLine dto){
        ApiResponse lineAndTheme = service.updateLineAndTheme(dto);
        return ResponseEntity.status(lineAndTheme.isSuccess() ? 203 : 403).body(lineAndTheme);
    }

    @GetMapping("/isMultiUsedTheme/{themeId}")
    public HttpEntity<?> isMultiUsedTheme(@CurrentUser User user, @PathVariable String themeId) {
        return ResponseEntity.ok(service.isMultiUsedTheme(themeId));
    }
}
