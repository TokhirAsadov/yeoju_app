package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/otherService")
@RequiredArgsConstructor
public class GetDataOtherServiceController {
}
