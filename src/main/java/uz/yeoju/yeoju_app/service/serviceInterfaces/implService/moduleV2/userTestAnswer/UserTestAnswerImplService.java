package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.TestOption;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.entity.moduleV2.UserTestAnswer;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestOptionRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestQuestionRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserTestAnswerImplService implements UserTestAnswerService{
    private final UserTestAnswerRepository userTestAnswerRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final UserRepository userRepository;
    private final TestOptionRepository testOptionRepository;

    @Value("${hugging_face.access_token}")
    private String huggingFaceApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserTestAnswerImplService(UserTestAnswerRepository userTestAnswerRepository, TestQuestionRepository testQuestionRepository, UserRepository userRepository, TestOptionRepository testOptionRepository) {
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.userRepository = userRepository;
        this.testOptionRepository = testOptionRepository;
    }

    @Override
    public ApiResponse finishCourseTest(UserTestAnswerFinisher finisher) {
        if (!userRepository.existsById(finisher.userId)) {
            return new ApiResponse(false, "User not found by id = " + finisher.userId);
        }

        User user = userRepository.getById(finisher.userId);

        for (UserTestAnswerFinisherChild answer : finisher.answers) {
            if (!testQuestionRepository.existsById(answer.testQuestionId)) {
                throw new RuntimeException("Test question not found by id = " + answer.testQuestionId);
            }

            if (userTestAnswerRepository.existsByUserIdAndQuestionId(finisher.userId, answer.testQuestionId)) {
                throw new RuntimeException("Bunday javob allaqachon mavjud!");
            }

            TestQuestion question = testQuestionRepository.getById(answer.testQuestionId);

            // Tanlangan variantlar (ID lar orqali TestOption obyektlari)
            List<TestOption> selectedOptions = testOptionRepository.findAllById(answer.getSelectedOptionsIds());

            // To'g'ri javoblarni topish
            List<String> correctOptionIds = question.getOptions().stream()
                    .filter(TestOption::getCorrect) // correct == true
                    .map(option -> option.getId().trim())
                    .collect(Collectors.toList());

            // Foydalanuvchi tanlagan variantlar
            List<String> selectedOptionIds = selectedOptions.stream()
                    .map(option -> option.getId().trim())
                    .collect(Collectors.toList());

            // To'g'rilikni tekshirish (Set sifatida solishtirish)
            boolean isCorrect = new HashSet<>(correctOptionIds).equals(new HashSet<>(selectedOptionIds));

            // Javobni saqlash
            UserTestAnswer userTestAnswer = new UserTestAnswer();
            userTestAnswer.setUser(user);
            userTestAnswer.setQuestion(question);
            userTestAnswer.setSelectedOptions(selectedOptions);
            userTestAnswer.setCorrect(isCorrect);

            userTestAnswerRepository.save(userTestAnswer);
        }

        return new ApiResponse(true, "User test answers saved successfully");
    }

    @Override
    @Transactional
    public ApiResponse create(UserTestAnswerCreator creator) {
        if (!userRepository.existsById(creator.userId)) {
            return new ApiResponse(false, "User not found by id = " + creator.userId);
        }
        if (!testQuestionRepository.existsById(creator.testQuestionId)) {
            return new ApiResponse(false, "Test question not found by id = " + creator.testQuestionId);
        }
        if (userTestAnswerRepository.existsByUserIdAndQuestionId(creator.userId, creator.testQuestionId)) {
            return new ApiResponse(false, "Bunday javob allaqachon mavjud!");
        }

        User user = userRepository.getById(creator.userId);
        TestQuestion question = testQuestionRepository.getById(creator.testQuestionId);

        // Foydalanuvchi tanlagan variantlar
        List<TestOption> selectedOptions = testOptionRepository.findAllById(creator.getSelectedOptionsIds());

        // To'g'ri variantlarni olish
        List<String> correctOptionIds = question.getOptions().stream()
                .filter(TestOption::getCorrect)
                .map(option -> option.getId().trim())
                .collect(Collectors.toList());

        List<String> selectedOptionIds = selectedOptions.stream()
                .map(option -> option.getId().trim())
                .collect(Collectors.toList());

        boolean isCorrect = new HashSet<>(correctOptionIds).equals(new HashSet<>(selectedOptionIds));

        // Javob obyektini to‘ldirish
        UserTestAnswer userTestAnswer = new UserTestAnswer();
        userTestAnswer.setUser(user);
        userTestAnswer.setQuestion(question);
        userTestAnswer.setSelectedOptions(selectedOptions);
        userTestAnswer.setWrittenAnswer(creator.getWrittenAnswer()); // faqat WRITTEN turdagi testlar uchun
        userTestAnswer.setCorrect(isCorrect);

        UserTestAnswer saved = userTestAnswerRepository.save(userTestAnswer);
        return new ApiResponse(true, "User test answer created successfully", saved);
    }


    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"User test answer",userTestAnswerRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String userTestAnswerId) {
        UserTestAnswer userTestAnswer = userTestAnswerRepository.findById(userTestAnswerId)
                .orElseThrow(() -> new RuntimeException("User test answer not found by id="+userTestAnswerId));
        return new ApiResponse(true,"User test answer by id="+userTestAnswerId,userTestAnswer);
    }

    @Override
    public boolean deleteById(String userTestAnswerId) {
        UserTestAnswer userTestAnswer = userTestAnswerRepository.findById(userTestAnswerId)
                .orElseThrow(() -> new RuntimeException("User test answer not found by id="+userTestAnswerId));
        userTestAnswer.setSelectedOptions(new ArrayList<>());
        userTestAnswerRepository.delete(userTestAnswer);
        return true;
    }

    @Override
    public TestResultDto calculateUserScore(String userId, String courseTestId) {
        List<TestQuestion> testQuestions = testQuestionRepository.findAllByTestId(courseTestId);
        int totalQuestions = testQuestions.size();

        List<UserTestAnswer> userAnswers = userTestAnswerRepository.findAllByUserIdAndQuestionTestId(userId, courseTestId);

        int correctCount = 0;
        for (UserTestAnswer answer : userAnswers) {
            if (answer.isCorrect()) {
                correctCount++;
            }
        }

        return new TestResultDto(totalQuestions, correctCount);
    }

    @Override
    public ResponseEntity<AiResponseDto> ai(AiRequestDto dto) throws IOException {
        String aiUrl = "http://localhost:5050/api/ai/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AiRequestDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(aiUrl, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        AiResponseDto responseDto = mapper.readValue(response.getBody(), AiResponseDto.class);

        return ResponseEntity.status(response.getStatusCode()).body(responseDto);
    }

}
