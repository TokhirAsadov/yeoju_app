package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import java.util.Set;

public interface KafedraV2DtoRes {

     String getId();
     String getNameUz();
     String getNameEn();
     String getNameRu();


     Set<String> getRoles();
     Set<String> positions();
}
