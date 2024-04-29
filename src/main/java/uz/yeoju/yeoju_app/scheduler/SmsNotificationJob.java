//package uz.yeoju.yeoju_app.scheduler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StopWatch;
//import uz.yeoju.yeoju_app.service.useServices.SmsLogService;
//
//@Service
//@RequiredArgsConstructor
//public class SmsNotificationJob {
//
//    private final SmsLogService service;
//
//    @Scheduled(fixedDelay = 60000L)
//    public void sendSMSMessageJob() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("SMS NOTIFICATION");
//
//        service.sendSmsByScheduler();
//
//        stopWatch.stop();
////        log.info(stopWatch.toString());
//    }
//
//
////    @Scheduled(fixedDelay = 180000)
////    public void sendFailedSMSMessageJob() {
////        StopWatch stopWatch = new StopWatch();
////        stopWatch.start("FAILED SMS NOTIFICATION");
////
//////        service.sendFailedBatchMessage();
////
////        stopWatch.stop();
//////        log.info(stopWatch.toString());
////    }
//
//}
