package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.weekOfEducationYear;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekEduType;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.WeekOfYearDto;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.educationYear.WeekOfEducationYearRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WeekOfEducationYearImplService implements WeekOfEducationYearService {
    private final WeekOfEducationYearRepository weekRepository;
    private final EducationYearRepository educationYearRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all weeks", weekRepository.findAllByCreatedAt());
    }

    @Override
    public ApiResponse findById(String id) {
        return new ApiResponse(true,"find by id -> "+id,weekRepository.findById(id).orElseGet(null));
    }

    @Override
    public ApiResponse saveOrUpdate(WeekOfYearDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    @Override
    public ApiResponse saveV2(WeekOfYearDto dto) {
        Optional<EducationYear> optionalEducationYear = educationYearRepository.findById(dto.getEducationYearId());
        if (optionalEducationYear.isPresent()) {
            boolean exists = weekRepository.existsByStart(dto.getStart());
            if (exists) {
                return new ApiResponse(false, "Error.. " + dto.getStart() + " already exists. Enter other start week day.");
            } else {
                WeekOfEducationYear week = new WeekOfEducationYear();
                week.setStart(dto.getStart());
                week.setEnd(dto.getEnd());
                week.setYear(dto.getYear());
                week.setEduType(Enum.valueOf(WeekEduType.class, dto.getEduType()));
                week.setSortNumber(dto.getSortNumber());
                week.setWeekNumber(dto.getWeekNumber());
                week.setType(WeekType.valueOf(dto.getType()));
                WeekOfEducationYear save = weekRepository.save(week);

                EducationYear educationYear = optionalEducationYear.get();
                Set<WeekOfEducationYear> educationYearSet = new HashSet<>(educationYear.getWeeks());
                educationYearSet.add(save);
                educationYear.setWeeks(educationYearSet);
                educationYearRepository.save(educationYear);
                return new ApiResponse(true, "save week successfully.");
            }
        }
        else {
            return new ApiResponse(false, "Error.. Education year was not found by id: "+dto.getEducationYearId());
        }
    }

    @Override
    public ApiResponse deletedById(String id) {
        Optional<WeekOfEducationYear> yearOptional = weekRepository.findById(id);
        if (yearOptional.isPresent()) {
            weekRepository.deleteById(id);
            return new ApiResponse(true,"deleted week of year successfully.");
        }
        else {
            return new ApiResponse(false,"Error.. Not fount by id -> "+id);
        }
    }

    public ApiResponse save(WeekOfYearDto dto){
        boolean exists = weekRepository.existsByStart(dto.getStart());
        if (exists){
            return new ApiResponse(false,"Error.. "+dto.getStart()+" already exists. Enter other start week day.");
        }
        else {
            WeekOfEducationYear week = new WeekOfEducationYear();
            week.setStart(dto.getStart());
            week.setEnd(dto.getEnd());
            week.setYear(dto.getYear());
            week.setEduType(Enum.valueOf(WeekEduType.class,dto.getEduType()));
            week.setSortNumber(dto.getSortNumber());
            week.setWeekNumber(dto.getWeekNumber());
            week.setType(WeekType.valueOf(dto.getType()));
            weekRepository.save(week);
            return new ApiResponse(true,"save week successfully.");
        }
    }

    public ApiResponse update(WeekOfYearDto dto){
        Optional<WeekOfEducationYear> yearOptional = weekRepository.findById(dto.getId());
        if (yearOptional.isPresent()) {
            WeekOfEducationYear week = yearOptional.get();
            Optional<WeekOfEducationYear> startOptional = weekRepository.findWeekOfEducationYearByStart(dto.getStart());
            if (startOptional.isPresent()) {
                WeekOfEducationYear startWeek = startOptional.get();
                if (Objects.equals(startWeek.getId(), week.getId())){
                    week.setType(WeekType.valueOf(dto.getType()));
                    week.setEnd(dto.getEnd());
                    week.setSortNumber(dto.getSortNumber());
                    week.setWeekNumber(dto.getWeekNumber());
                    weekRepository.save(week);
                    return new ApiResponse(true,"updated week successfully.");
                }
                else {
                    return  new ApiResponse(false,"Error.."+dto.getStart()+" already exists start date. Choose other start date");
                }
            }
            else {
                week.setType(WeekType.valueOf(dto.getType()));
                week.setStart(dto.getStart());
                week.setEnd(dto.getEnd());
                week.setSortNumber(dto.getSortNumber());
                week.setWeekNumber(dto.getWeekNumber());
                weekRepository.save(week);
                return new ApiResponse(true,"updated week successfully.");
            }
        }
        else {
            return new ApiResponse(false,"Error.. Not fount by id -> "+dto.getId());
        }
    }
}
