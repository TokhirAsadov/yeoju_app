package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import uz.yeoju.yeoju_app.entity.RoleWebClient;
import uz.yeoju.yeoju_app.entity.admin.Room;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.admin.RoomDto;
import uz.yeoju.yeoju_app.repository.RoomRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.RoomImplService;

import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomImplService<RoomDto> {
    private final RoomRepository roomRepository;


    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);// request time that how time is during

    private final WebClient webClient;// get web client

//    @Autowired
//    public UserService(WebClient webClient) {
//        this.webClient = webClient;
//    }


    public Object getUserFromOther(SignInDto sign) {

        //todo-----------   GET TOKEN FROM WEB CLIENT ------------
        ResToken resToken = getResToken(sign);

        assert resToken != null;
        return webClient
                .get()
                .uri("/user/findAllUsers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                .retrieve()
                .bodyToMono(Object.class)
                .block(REQUEST_TIMEOUT);
    }


    public ResToken getResToken(SignInDto sign) {
        //todo-----------   GET TOKEN FROM WEB CLIENT ------------
        ResponseEntity<ResToken> resTokenResponse = webClient.post()
                .uri("/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(sign), SignInDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    //logError("Client error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Bad Request", null, null, null));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    //logError("Server error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Server Error", null, null, null));
                })
                .toEntity(ResToken.class)
                .block(REQUEST_TIMEOUT);
        System.out.println(resTokenResponse.getBody()+"----------------------------------------- RES TOKEN");

        return resTokenResponse.getBody();
    }

    public Object getDataFromOther(Integer page,Integer size) {
        return webClient
                .get()
                .uri("/result/getAllResults?page="+page+"&size="+size)
                .retrieve()
                .bodyToMono(Object.class)
                .block(REQUEST_TIMEOUT);
    }


    public Object createRoleWebClient(RoleWebClient newRole) {
        System.out.println(newRole+"---------------------------------------------------------------------------------------------------------------");
        System.out.println(BodyInserters.fromValue(newRole)+"---------------------------------------------------------------------------------------------------------------");

        ResToken resToken = getResToken(new SignInDto("admin123","admin!@#"));
        return webClient.post()
                .uri("/role/createRole")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                .body(Mono.just(newRole), RoleWebClient.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    //logError("Client error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Bad Request", null, null, null));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    //logError("Server error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Server Error", null, null, null));
                })
                .toEntity(RoleWebClient.class)
                .block(REQUEST_TIMEOUT);
    }


    @Transactional
    public Object sendMultipartDataOtherServer(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return senderFile(file);
            }
        }
        return null;
    }

    public Object senderFile(MultipartFile multipartFile){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());
        ResToken resToken = getResToken(new SignInDto("kiut123","kiut123"));
        return webClient.post()
                .uri("/result/importStudentsResults")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
//                .exchangeToMono(response -> {
//                    if (response.statusCode().equals(HttpStatus.OK)) {
//                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
//                    } else {
//                        throw new ServiceException("Error uploading file");
//                    }
//                });
    }






    public ApiResponse getRoomsForSelect(){
        return new ApiResponse(true,"rooms",roomRepository.getRoomsForSelect());
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All rooms",
                roomRepository.findAll().stream()
                        .map(this::generateRoomDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> RoomDto generateRoomDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getName()
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return new ApiResponse(true,"Element by id",generateRoomDto(roomRepository.findById(id).get()));
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(RoomDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            if (!roomRepository.existsRoomByName(dto.getName())){
                roomRepository.save(generateRoom(dto));
                return new ApiResponse(true,"saveOrUpdate room successfully!..");
            }
            return new ApiResponse(false,"error, sorry already exist name of room");
        }
        else { //update
            if (!roomRepository.existsRoomByName(dto.getName())){
                Optional<Room> roomOptional = roomRepository.findById(dto.getId());
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    room.setName(dto.getName());
                    roomRepository.save(room);
                    return new ApiResponse(true,"update room successfully!..");
                }
                return new ApiResponse(false,"error, not fount room");
            }
            return new ApiResponse(false,"error, sorry already exist name of room");
        }
    }

    private Room generateRoom(RoomDto dto) {
        return new Room(
                dto.getId(),
                dto.getName()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        roomRepository.deleteById(id);
        return new ApiResponse(true,"Room deleted successfully!..");
    }
}
