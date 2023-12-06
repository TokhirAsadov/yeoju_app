package uz.yeoju.yeoju_app.payload.resDto.kafedra.month;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface GetDate28 {

    String getId();
    Date getDate();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/01')}")
    GetEnterOutTimes get1();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/02')}")
    GetEnterOutTimes get2();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/03')}")
    GetEnterOutTimes get3();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/04')}")
    GetEnterOutTimes get4();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/05')}")
    GetEnterOutTimes get5();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/06')}")
    GetEnterOutTimes get6();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/07')}")
    GetEnterOutTimes get7();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/08')}")
    GetEnterOutTimes get8();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/09')}")
    GetEnterOutTimes get9();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/10')}")
    GetEnterOutTimes get10();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/11')}")
    GetEnterOutTimes get11();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/12')}")
    GetEnterOutTimes get12();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/13')}")
    GetEnterOutTimes get13();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/14')}")
    GetEnterOutTimes get14();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/15')}")
    GetEnterOutTimes get15();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/16')}")
    GetEnterOutTimes get16();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/17')}")
    GetEnterOutTimes get17();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/18')}")
    GetEnterOutTimes get18();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/19')}")
    GetEnterOutTimes get19();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/20')}")
    GetEnterOutTimes get20();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/21')}")
    GetEnterOutTimes get21();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/22')}")
    GetEnterOutTimes get22();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/23')}")
    GetEnterOutTimes get23();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/24')}")
    GetEnterOutTimes get24();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/25')}")
    GetEnterOutTimes get25();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/26')}")
    GetEnterOutTimes get26();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/27')}")
    GetEnterOutTimes get27();

    @Value("#{@teacherRepository.getEnterOutTimesNEW(target.id,target.date,'/28')}")
    GetEnterOutTimes get28();

}
