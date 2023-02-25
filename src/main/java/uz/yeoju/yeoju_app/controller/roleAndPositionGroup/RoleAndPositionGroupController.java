package uz.yeoju.yeoju_app.controller.roleAndPositionGroup;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.roleAndPositionGroup.RoleAndPositionGroupService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/roleAndPositionGroup")
@RequiredArgsConstructor
public class RoleAndPositionGroupController {

    private final RoleAndPositionGroupService service;



}
