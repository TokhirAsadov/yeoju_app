package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetEnterOutTimes;

public interface GetForBadStudent {

    String getId();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-1)}")
    GetEnterOutTimes get1();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-2)}")
    GetEnterOutTimes get2();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-3)}")
    GetEnterOutTimes get3();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-4)}")
    GetEnterOutTimes get4();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-5)}")
    GetEnterOutTimes get5();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-6)}")
    GetEnterOutTimes get6();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-7)}")
    GetEnterOutTimes get7();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-8)}")
    GetEnterOutTimes get8();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-9)}")
    GetEnterOutTimes get9();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-10)}")
    GetEnterOutTimes get10();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-11)}")
    GetEnterOutTimes get11();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-12)}")
    GetEnterOutTimes get12();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-13)}")
    GetEnterOutTimes get13();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-14)}")
    GetEnterOutTimes get14();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-15)}")
    GetEnterOutTimes get15();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-16)}")
    GetEnterOutTimes get16();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-17)}")
    GetEnterOutTimes get17();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-18)}")
    GetEnterOutTimes get18();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-19)}")
    GetEnterOutTimes get19();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-20)}")
    GetEnterOutTimes get20();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-21)}")
    GetEnterOutTimes get21();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-22)}")
    GetEnterOutTimes get22();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-23)}")
    GetEnterOutTimes get23();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-24)}")
    GetEnterOutTimes get24();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-25)}")
    GetEnterOutTimes get25();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-26)}")
    GetEnterOutTimes get26();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-27)}")
    GetEnterOutTimes get27();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-28)}")
    GetEnterOutTimes get28();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-29)}")
    GetEnterOutTimes get29();

    @Value("#{@teacherRepository.getEnterOutTimesForBadStudents(target.id,-30)}")
    GetEnterOutTimes get30();
}
