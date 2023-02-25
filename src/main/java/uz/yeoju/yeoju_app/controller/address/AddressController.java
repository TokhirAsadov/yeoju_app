package uz.yeoju.yeoju_app.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address.AddressService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/createForUser")
    public HttpEntity<?> createForUser(@CurrentUser User user, @RequestBody AddressDto dto){
       return ResponseEntity.ok(addressService.saveForUser(user,dto));
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(addressService.findById(id));
    }
}
