package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.EducationType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.resDto.statistics.GetTotalClassroomAttendance;
import uz.yeoju.yeoju_app.repository.EducationTypeRepository;
import uz.yeoju.yeoju_app.repository.statistics.CardDbStatisticsRepository;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardDbStatisticsImplService implements CardDbStatisticsService {
    private final CardDbStatisticsRepository repository;
    private final EducationTypeRepository educationTypeRepository;

    @Override
    public ApiResponse getPassedTeachers(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true, "all passed teachers", repository.getPassedTeachers(year, week, weekday));
    }

    @Override
    public ApiResponse getAllClassroomStatistics(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true, "all classroom statistics", repository.getClassroomAttendance(year, week, weekday));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatistics(Integer year, Integer week, Integer weekday, String eduForm) {

        System.out.println("33+++++++++++++++++++++++");
        List<EducationType> all = educationTypeRepository.findAll();

        GetTotalClassroomAttendance get1 = (GetTotalClassroomAttendance) getTotalClassroomAttendanceByEduType(year, week, weekday, all.get(0).getName(), eduForm).getObj();
        GetTotalClassroomAttendance get2 = (GetTotalClassroomAttendance) getTotalClassroomAttendanceByEduType(year, week, weekday, all.get(1).getName(), eduForm).getObj();
        GetTotalClassroomAttendance get3 = (GetTotalClassroomAttendance) getTotalClassroomAttendanceByEduType(year, week, weekday, all.get(2).getName(), eduForm).getObj();
        GetTotalClassroomAttendance get4 = (GetTotalClassroomAttendance) getTotalClassroomAttendanceByEduType(year, week, weekday, all.get(3).getName(), eduForm).getObj();


        GetTotalClassroomAttendance result = new GetTotalClassroomAttendance() {

            @Override
            public Integer getTotalAllStudent() throws ExecutionException, InterruptedException {
                return (get1.getTotalAllStudent() != null ? get1.getTotalAllStudent() : 0) +
                        (get2.getTotalAllStudent() != null ? get2.getTotalAllStudent() : 0) +
                        (get3.getTotalAllStudent() != null ? get3.getTotalAllStudent() : 0) +
                        (get4.getTotalAllStudent() != null ? get4.getTotalAllStudent() : 0);
            }

            @Override
            public Integer getTotalComeCount() throws ExecutionException, InterruptedException {
                return (get1.getTotalComeCount() != null ? get1.getTotalComeCount() : 0) +
                        (get2.getTotalComeCount() != null ? get2.getTotalComeCount() : 0) +
                        (get3.getTotalComeCount() != null ? get3.getTotalComeCount() : 0) +
                        (get4.getTotalComeCount() != null ? get4.getTotalComeCount() : 0);
            }
        };

        return new ApiResponse(true, "total all classroom statistics", result);
    }


    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeek(Integer year, Integer week) {
        return new ApiResponse(true, "total all classroom statistics with week", repository.getTotalAllClassroomStatisticsWithWeek(year, week));
    }

    public ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday,
                                                            String eduType, String eduForm) {

        ExecutorService executor = Executors.newFixedThreadPool(8);
        // Har bir course uchun parallel ishga tushiramiz
        CompletableFuture<GetTotalClassroomAttendance> f1 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 1, eduForm), executor);
        CompletableFuture<GetTotalClassroomAttendance> f2 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 2, eduForm), executor);
        CompletableFuture<GetTotalClassroomAttendance> f3 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 3, eduForm), executor);
        CompletableFuture<GetTotalClassroomAttendance> f4 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 4, eduForm), executor);
        CompletableFuture<GetTotalClassroomAttendance> f5 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 5, eduForm), executor);
        CompletableFuture<GetTotalClassroomAttendance> f6 =
                CompletableFuture.supplyAsync(() -> repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 6, eduForm), executor);

        // Barcha ishlar tugaguncha kutamiz
        CompletableFuture.allOf(f1, f2, f3, f4, f5, f6).join();

        // Natijalarni yig‘ish
        GetTotalClassroomAttendance result = new GetTotalClassroomAttendance() {
            @Override
            public Integer getTotalAllStudent() throws ExecutionException, InterruptedException {
                return safeGetTotal(f1.get()) +
                        safeGetTotal(f2.get()) +
                        safeGetTotal(f3.get()) +
                        safeGetTotal(f4.get()) +
                        safeGetTotal(f5.get()) +
                        safeGetTotal(f6.get());
            }

            @Override
            public Integer getTotalComeCount() throws ExecutionException, InterruptedException {
                return safeComeCount(f1.get()) +
                        safeComeCount(f2.get()) +
                        safeComeCount(f3.get()) +
                        safeComeCount(f4.get()) +
                        safeComeCount(f5.get()) +
                        safeComeCount(f6.get());
            }

            private int safeGetTotal(GetTotalClassroomAttendance obj) throws ExecutionException, InterruptedException {
                return obj != null && obj.getTotalAllStudent() != null ? obj.getTotalAllStudent() : 0;
            }

            private int safeComeCount(GetTotalClassroomAttendance obj) throws ExecutionException, InterruptedException {
                return obj != null && obj.getTotalComeCount() != null ? obj.getTotalComeCount() : 0;
            }
        };

        return new ApiResponse(true,
                "total all classroom statistics by edu type: " + eduType, result);

    }

    /*@Override
    public ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday, String eduType, String eduForm) {
        GetTotalClassroomAttendance get1 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 1, eduForm);
        GetTotalClassroomAttendance get2 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 2, eduForm);
        GetTotalClassroomAttendance get3 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 3, eduForm);
        GetTotalClassroomAttendance get4 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 4, eduForm);
        GetTotalClassroomAttendance get5 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 5, eduForm);
        GetTotalClassroomAttendance get6 = repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, 6, eduForm);

        GetTotalClassroomAttendance result = new GetTotalClassroomAttendance() {

            @Override
            public Integer getTotalAllStudent() {
                return (get1.getTotalAllStudent() != null ? get1.getTotalAllStudent() : 0) +
                        (get2.getTotalAllStudent() != null ? get2.getTotalAllStudent() : 0) +
                        (get3.getTotalAllStudent() != null ? get3.getTotalAllStudent() : 0) +
                        (get4.getTotalAllStudent() != null ? get4.getTotalAllStudent() : 0) +
                        (get5.getTotalAllStudent() != null ? get5.getTotalAllStudent() : 0) +
                        (get6.getTotalAllStudent() != null ? get6.getTotalAllStudent() : 0);
            }

            @Override
            public Integer getTotalComeCount() {
                return (get1.getTotalComeCount() != null ? get1.getTotalComeCount() : 0) +
                        (get2.getTotalComeCount() != null ? get2.getTotalComeCount() : 0) +
                        (get3.getTotalComeCount() != null ? get3.getTotalComeCount() : 0) +
                        (get4.getTotalComeCount() != null ? get4.getTotalComeCount() : 0) +
                        (get5.getTotalComeCount() != null ? get5.getTotalComeCount() : 0) +
                        (get6.getTotalComeCount() != null ? get6.getTotalComeCount() : 0);
            }
        };
        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType,result *//*repository.getTotalClassroomAttendanceByEduType(year, week, weekday, eduType, eduForm)*//*);
    }*/

    /*public ApiResponse getTotalClassroomAttendanceByEduType(Integer year, Integer week, Integer weekday, String eduType, String eduForm) {
        List<String> facultiesShortNames = facultyRepository.findAll().stream()
                .map(Faculty::getShortName)
                .collect(Collectors.toList());

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            List<CompletableFuture<GetTotalClassroomAttendance>> futures = facultiesShortNames.stream()
                    .map(faculty -> CompletableFuture.supplyAsync(() -> processFaculty(year, week, weekday, eduType, eduForm, faculty), executor))
                    .collect(Collectors.toList());

            // barcha natijalarni kutish
            List<GetTotalClassroomAttendance> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

            // yakuniy natija hisoblash
            GetTotalClassroomAttendance finalResult = new GetTotalClassroomAttendance() {
                @Override
                public Integer getTotalAllStudent() {
                    return results.stream().mapToInt(r -> r.getTotalAllStudent() != null ? r.getTotalAllStudent() : 0).sum();
                }

                @Override
                public Integer getTotalComeCount() {
                    return results.stream().mapToInt(r -> r.getTotalComeCount() != null ? r.getTotalComeCount() : 0).sum();
                }
            };

            return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType, finalResult);
        } finally {
            executor.shutdown();
        }
    }*/

    private GetTotalClassroomAttendance processFaculty(Integer year, Integer week, Integer weekday, String eduType, String eduForm, String faculty) {
        GetTotalClassroomAttendance get1 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 1, eduForm);
        GetTotalClassroomAttendance get2 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 2, eduForm);
        GetTotalClassroomAttendance get3 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 3, eduForm);
        GetTotalClassroomAttendance get4 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 4, eduForm);

        return new GetTotalClassroomAttendance() {
            @Override
            public Integer getTotalAllStudent() throws ExecutionException, InterruptedException {
                return sum(get1.getTotalAllStudent(), get2.getTotalAllStudent(), get3.getTotalAllStudent(), get4.getTotalAllStudent());
            }

            @Override
            public Integer getTotalComeCount() throws ExecutionException, InterruptedException {
                return sum(get1.getTotalComeCount(), get2.getTotalComeCount(), get3.getTotalComeCount(), get4.getTotalComeCount());
            }

            private int sum(Integer... values) {
                return Arrays.stream(values).filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
            }
        };
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduType(Integer year, Integer week, String eduType) {
        return new ApiResponse(true, "total all classroom statistics with week by edu type: " + eduType, repository.getTotalClassroomAttendanceWithWeekByEduType(year, week, eduType));
    }

    @Override
//    @Async
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndFaculty(Integer year, Integer week, Integer weekday, String eduType, String faculty, String eduForm) {
        GetTotalClassroomAttendance get1 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 1, eduForm);
        GetTotalClassroomAttendance get2 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 2, eduForm);
        GetTotalClassroomAttendance get3 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 3, eduForm);
        GetTotalClassroomAttendance get4 = repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, 4, eduForm);



        GetTotalClassroomAttendance result = new GetTotalClassroomAttendance() {

            @Override
            public Integer getTotalAllStudent() throws ExecutionException, InterruptedException {
                return (get1.getTotalAllStudent() != null ? get1.getTotalAllStudent() : 0) +
                        (get2.getTotalAllStudent() != null ? get2.getTotalAllStudent() : 0) +
                        (get3.getTotalAllStudent() != null ? get3.getTotalAllStudent() : 0) +
                        (get4.getTotalAllStudent() != null ? get4.getTotalAllStudent() : 0);
            }

            @Override
            public Integer getTotalComeCount() throws ExecutionException, InterruptedException {
                return (get1.getTotalComeCount() != null ? get1.getTotalComeCount() : 0) +
                        (get2.getTotalComeCount() != null ? get2.getTotalComeCount() : 0) +
                        (get3.getTotalComeCount() != null ? get3.getTotalComeCount() : 0) +
                        (get4.getTotalComeCount() != null ? get4.getTotalComeCount() : 0);
            }
        };

        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType + " and faculty: " + faculty, result);
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(Integer year, Integer week, String eduType, String faculty) {
        return new ApiResponse(true, "total all classroom statistics with week by edu type: " + eduType + " and faculty: " + faculty, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndFaculty(year, week, eduType, faculty));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, String courses, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType + " and faculty: " + faculty + " and courses: " + courses, repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, courses, eduForm));
    }

    @Override
//    @Async
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(Integer year, Integer week, Integer weekday, String eduType, String faculty, Integer course, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType + " and faculty: " + faculty + " and courses: " + course, repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(year, week, weekday, eduType, faculty, course, eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(Integer year, Integer week, String eduType, String faculty, Integer course) {
        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType + " and faculty: " + faculty + " and course: " + course, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndFacultyAndCourse(year, week, eduType, faculty, course));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, String courses, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by course: " + courses, repository.getTotalClassroomAttendanceByCourse(year, week, weekday, courses, eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByCourse(Integer year, Integer week, Integer weekday, Integer courses, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by course: " + courses, repository.getTotalClassroomAttendanceByCourse(year, week, weekday, courses, eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByCourse(Integer year, Integer week, Integer course) {
        return new ApiResponse(true, "total all classroom statistics with week by course: " + course, repository.getTotalAllClassroomStatisticsWithWeekByCourse(year, week, course));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByFaculty(Integer year, Integer week, Integer weekday, String faculty, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by faculty: " + faculty, repository.getTotalClassroomAttendanceByFaculty(year, week, weekday, faculty, eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByFaculty(Integer year, Integer week, String faculty) {
        return new ApiResponse(true, "total all classroom statistics with week by faculty: " + faculty, repository.getTotalAllClassroomStatisticsWithWeekByFaculty(year, week, faculty));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, String courses, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by faculty: " + faculty + " and course: " + courses, repository.getTotalClassroomAttendanceByFacultyAndCourse(year, week, weekday, faculty, courses, eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by faculty: " + faculty + " and course: " + course, repository.getTotalClassroomAttendanceByFacultyAndCourse(year, week, weekday, faculty, course, eduForm));
    }

    @Override
    public ApiResponse getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(Integer year, Integer week, String faculty, Integer course) {
        return new ApiResponse(true, "total all classroom statistics by faculty: " + faculty + " and course: " + course, repository.getTotalAllClassroomStatisticsWithWeekByFacultyAndCourse(year, week, faculty, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, String courses, String eduForm) {
        return new ApiResponse(true, "total all classroom statistics by edu type: " + eduType + " and course: " + courses, repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday, eduType, courses, eduForm));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course, String eduForm) {
        return new ApiResponse(true,"total all classroom statistics by edu type: "+eduType+" and course: "+course, repository.getTotalClassroomAttendanceByEduTypeAndCourse(year, week, weekday,eduType,course,eduForm));
    }

    /*public ApiResponse getTotalClassroomAttendanceByEduTypeAndCourse(
            Integer year, Integer week, Integer weekday, String eduType, Integer course, String eduForm) {

        // Fakultetlar ro‘yxatini olish
        List<String> facultiesShortNames = facultyRepository.findAll().stream()
                .map(Faculty::getShortName)
                .collect(Collectors.toList());

        // Thread pool (CPU yadrolar soniga teng)
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            // Har bir fakultet uchun parallel ish
            List<CompletableFuture<GetTotalClassroomAttendance>> futures = facultiesShortNames.stream()
                    .map(faculty -> CompletableFuture.supplyAsync(() ->
                            repository.getTotalClassroomAttendanceByEduTypeAndFacultyAndCourse(
                                    year, week, weekday, eduType, faculty, course, eduForm
                            ), executor))
                    .collect(Collectors.toList());

            // Natijalarni kutish
            List<GetTotalClassroomAttendance> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

            // Yakuniy natija hisoblash
            GetTotalClassroomAttendance finalResult = new GetTotalClassroomAttendance() {
                @Override
                public Integer getTotalAllStudent() {
                    return results.stream()
                            .mapToInt(r -> r.getTotalAllStudent() != null ? r.getTotalAllStudent() : 0)
                            .sum();
                }

                @Override
                public Integer getTotalComeCount() {
                    return results.stream()
                            .mapToInt(r -> r.getTotalComeCount() != null ? r.getTotalComeCount() : 0)
                            .sum();
                }
            };

            return new ApiResponse(true,
                    "total all classroom statistics by edu type: " + eduType + " and course: " + course,
                    finalResult);

        } finally {
            executor.shutdown();
        }
    }
*/
    @Override
    public ApiResponse getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(Integer year, Integer week, String eduType, Integer course) {
        return new ApiResponse(true, "total all classroom statistics with week by edu type: " + eduType + " and course: " + course, repository.getTotalClassroomAttendanceWithWeekByEduTypeAndCourse(year, week, eduType, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFaculties(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true, "all classroom statistics", repository.getTotalClassroomAttendanceWithFaculties(year, week, weekday));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndCourse(Integer year, Integer week, Integer weekday, Integer course) {
        return new ApiResponse(true, "all classroom statistics with course: " + course, repository.getTotalClassroomAttendanceWithFacultiesAndCourse(year, week, weekday, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduType(Integer year, Integer week, Integer weekday, String eduType) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType, repository.getTotalClassroomAttendanceWithFacultiesAndEduType(year, week, weekday, eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType + " and course: " + course, repository.getTotalClassroomAttendanceWithFacultiesAndEduTypeAndCourse(year, week, weekday, eduType, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(Integer year, Integer week, Integer weekday, String faculty, String eduType, Integer course) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType + " and course: " + course, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndEduTypeAndCourse(year, week, weekday, faculty, eduType, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(Integer year, Integer week, Integer weekday, String faculty, Integer course) {
        return new ApiResponse(true, "all classroom statistics with faculty: " + faculty + " and course: " + course, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndCourse(year, week, weekday, faculty, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(Integer year, Integer week, Integer weekday, String faculty, String eduType) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType + " and faculty: " + faculty, repository.getTotalClassroomAttendanceEveryGroupByFacultyAndEduType(year, week, weekday, faculty, eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(Integer year, Integer week, Integer weekday, String eduType, Integer course) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType + " and course: " + course, repository.getTotalClassroomAttendanceEveryGroupByEduTypeAndCourse(year, week, weekday, eduType, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByFaculty(Integer year, Integer week, Integer weekday, String faculty) {
        return new ApiResponse(true, "all classroom statistics with faculty: " + faculty, repository.getTotalClassroomAttendanceEveryGroupByFaculty(year, week, weekday, faculty));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByEduType(Integer year, Integer week, Integer weekday, String eduType) {
        return new ApiResponse(true, "all classroom statistics with edu type: " + eduType, repository.getTotalClassroomAttendanceEveryGroupByEduType(year, week, weekday, eduType));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroupByCourse(Integer year, Integer week, Integer weekday, Integer course) {
        return new ApiResponse(true, "all classroom statistics with course: " + course, repository.getTotalClassroomAttendanceEveryGroupByCourse(year, week, weekday, course));
    }

    @Override
    public ApiResponse getTotalClassroomAttendanceEveryGroup(Integer year, Integer week, Integer weekday) {
        return new ApiResponse(true, "all classroom statistics", repository.getTotalClassroomAttendanceEveryGroup(year, week, weekday));
    }

    @Override
    public ApiResponse getDateRangeAttendance(Date start, Date end, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendance(start, end, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduType(Date start, Date end, String eduType, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByEduType(start, end, eduType, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByCourse(Date start, Date end, Integer course, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByCourse(start, end, course, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByFaculty(Date start, Date end, String faculty, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByFaculty(start, end, faculty, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndCourse(Date start, Date end, String eduType, Integer course, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByEduTypeAndCourse(start, end, eduType, course, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndFaculty(Date start, Date end, String eduType, String faculty, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByEduTypeAndFaculty(start, end, eduType, faculty, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByFacultyAndCourse(Date start, Date end, String faculty, Integer course, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByFacultyAndCourse(start, end, faculty, course, eduForm));
    }

    @Override
    public ApiResponse getDateRangeAttendanceByEduTypeAndFacultyAndCourse(Date start, Date end, String eduType, String faculty, Integer course, String eduForm) {
        return new ApiResponse(true, "data", repository.getDateRangeAttendanceByEduTypeAndFacultyAndCourse(start, end, eduType, faculty, course, eduForm));
    }
}
