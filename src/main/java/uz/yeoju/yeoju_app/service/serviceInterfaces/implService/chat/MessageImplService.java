package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.controller.chat.MessageBuild;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.chat.Chat;
import uz.yeoju.yeoju_app.entity.chat.Message;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.chat.MessageDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.chat.ChatRepository;
import uz.yeoju.yeoju_app.repository.chat.MessageRepository;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageImplService implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse findMessagesByChatId(String id) {
//        List<Message> messages = messageRepository.getMessagesByChatId(id);
        List<MessageBuild> messages = messageRepository.getMessagesByChatId(id).stream().map(this::generateMessageBuild).collect(Collectors.toList());
        return new ApiResponse(true,"messages",messages);
    }

    @Override
    public ApiResponse saveOrUpdate(MessageBuild messageDto) {
        Optional<User> userOptional = userRepository.findById(messageDto.getSenderId());
        Optional<Chat> chatOptional = chatRepository.findById(messageDto.getChatId());
        Message message = new Message();
        message.setChat(chatOptional.get());
        message.setSender(userOptional.get());
        message.setText(messageDto.getText());
        Message save = messageRepository.save(message);
        System.out.println(messageDto);
        return new ApiResponse(true,"send message",generateMessageBuild(save));
    }

    public Message generateMessage(MessageDto dto){

        return new Message(
                dto.getId(),
                charImplService.generateChat(dto.getChatDto()),
                userService.generateUser(dto.getSender()),
                dto.getText()
                );
    }

    public MessageBuild generateMessageBuild(Message message){
        return new MessageBuild(message.getId(),message.getChat().getId(),message.getSender().getId(), message.getText(),message.getCreatedAt());
    }

    @Autowired
    private CharImplService charImplService;

    @Autowired
    private UserService userService;

}
