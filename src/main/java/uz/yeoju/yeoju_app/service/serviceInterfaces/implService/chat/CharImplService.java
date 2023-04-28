package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.chat.Chat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.chat.ChatDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.chat.ChatRepository;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CharImplService implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse findAll() {
        List<ChatDto> chatDtos = chatRepository.findAll().stream().map(this::generateChatDto).collect(Collectors.toList());
        return new ApiResponse(true,"chat",chatDtos);
    }

    @Override
    public ApiResponse save(User user,String receiverId) {

        Optional<User> optional = userRepository.findById(receiverId);
        if (optional.isPresent()) {

            chatRepository.save(
                Chat.builder()
                        .members(new HashSet<>(Arrays.asList(user, optional.get())))
                    .build()
            );

            return new ApiResponse(true,"saveOrUpdate Chat");
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }

    @Override
    public ApiResponse findUserChats(String userId) {
        List<ChatDto> userChats = chatRepository.findUserChats(userId).stream().map(this::generateChatDto).collect(Collectors.toList());
        return new ApiResponse(true,"Chat user",userChats);
    }

    @Override
    public ApiResponse findChat(String firstId, String secondId) {
        Chat Chat = chatRepository.findChat(firstId, secondId);
        if (Chat !=null)
            return new ApiResponse(true,"find Chat",generateChatDto(Chat));

        return new ApiResponse(false,"not fount Chat");
    }

    @Override
    public ApiResponse findById(String id) {

        return new ApiResponse(true,"send",chatRepository.findById(id));

    }

    public ChatDto generateChatDto(Chat chat){
        return ChatDto.builder()
                .id(chat.getId())
                .members(chat.getMembers().stream().map(i->userService.generateUserDto(i)).collect(Collectors.toSet()))
                .build();
    }

    public Chat generateChat(ChatDto dto){
        Chat chat = new Chat();
        chat.setId(dto.getId());
        chat.setMembers(dto.getMembers().stream().map(i->userService.generateUser(i)).collect(Collectors.toSet()));
        return chat;
    }

    @Autowired
    private UserService userService;
}
