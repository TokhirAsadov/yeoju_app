package uz.yeoju.yeoju_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.yeoju.yeoju_app.entity.*; 
import uz.yeoju.yeoju_app.entity.enums.Gandername;
import uz.yeoju.yeoju_app.entity.enums.PhoneType;
import uz.yeoju.yeoju_app.payload.sms.SmsSendBodyDTO;
import uz.yeoju.yeoju_app.payload.sms.SmsSendContentDTO;
import uz.yeoju.yeoju_app.payload.sms.SmsSendMessagesDTO;
import uz.yeoju.yeoju_app.payload.sms.SmsSendRequestDTO;
import uz.yeoju.yeoju_app.repository.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    public final SectionRepository sectionRepository;
    public final RoleRepository roleRepository;
    public final UserRepository userRepository;
    public final GanderRepository ganderRepository;
    public final PasswordEncoder passwordEncoder;
    public final AccMonitoringLogRepo accMonitoringLogRepo;
    public final FacultyRepository facultyRepository;
    public final DekanRepository dekanRepository;
    public final StudentRepository studentRepository;
    public final GroupRepository groupRepository;
    public final PhoneNumberRepository phoneNumberRepository;

    private final RestTemplate smsClient;

    @Value("${spring.sql.init.mode}")
    private String type;

    @Override
    public void run(String... args) throws Exception {
        //2022-05-19 13:04:30.000
        //2022-05-19 13:07:12.000

        LocalDateTime time1 = LocalDateTime.of(2022,05,19,5,4);
        LocalDateTime time2 = LocalDateTime.of(2022,05,19,13,54);
        System.out.println(time1+"\n"+time2);
        System.out.println(
                accMonitoringLogRepo.findAccMonitorLogsByTimeBetween(
            time1, time2
        )
        );

        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_SUPER_ADMIN");
        if (!roleOptional.isPresent()){

            User super_admin = userRepository.getUserByLogin("super_admin");
            if (super_admin==null){
                Role superAdmin = roleRepository.save(new Role("ROLE_SUPER_ADMIN"));
                User user = new User();
                user.setLogin("super_admin");
                user.setPassword(passwordEncoder.encode("r00t123"));
                user.setRoles(new HashSet<>(Collections.singleton(superAdmin)));
                userRepository.save(user);
            }
        }else{
            User super_admin = userRepository.getUserByLogin("super_admin");
            if (super_admin==null){
//                Role superAdmin = roleRepository.saveOrUpdate(new Role("ROLE_SUPER_ADMIN"));
                User user = new User();
                user.setLogin("super_admin");
                user.setPassword(passwordEncoder.encode("r00t123"));
                user.setRoles(new HashSet<>(Collections.singleton(roleOptional.get())));
                userRepository.save(user);
            }
        }

//        Optional<User> optional = userRepository.findById("c6b603ca-cfe2-4095-a2e3-d21ab376f1ea");
//        User user = optional.get();
//
//        if (user.getLogin()==null){
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setCredentialsNonExpired(true);
//        user.setEnabled(true);
//        user.setEmail("mashrab");
//            user.setLogin("mashrab");
//            user.setPassword(passwordEncoder.encode("mashrab123"));
//            userRepository.saveOrUpdate(user);
//        }


//        Optional<User> userOptional = userRepository.findById("53625939-0cf5-4ef4-a395-c6c8007cba4e");
//        if (userOptional.isPresent()){
//            User user = userOptional.get();
//            Set<Role> roles = user.getRoles();
//            Set<Role> userRoles = new HashSet<>();
//            for (Role role : roles) {
//                if (role.getRoleName().equals("ROLE_STUDENT")){
//                    userRoles.add(roleRepository.findRoleByRoleName("ROLE_TEACHER").get());
//                    user.setRoles(userRoles);
//                    break;
//                }
//            }
//
//            userRepository.saveOrUpdate(user);
//        }

//        SmsSendContentDTO smsSendContentDTO = new SmsSendContentDTO("Hello bro, by yeoju ERP");
//        SmsSendBodyDTO smsSendBodyDTO = new SmsSendBodyDTO(smsSendContentDTO);
//        SmsSendMessagesDTO messagesDTO = new SmsSendMessagesDTO("998993361603","1");
//        SmsSendMessagesDTO messagesDTO2 = new SmsSendMessagesDTO("908092962","2");
//        List<SmsSendMessagesDTO> messagesDTOList = new ArrayList<>();
//        messagesDTOList.add(messagesDTO);
//        messagesDTOList.add(messagesDTO2);
//        SmsSendRequestDTO request = new SmsSendRequestDTO(smsSendBodyDTO,messagesDTOList);

//        var responseEntity = smsClient.postForEntity("/send", request, String.class);
//        System.out.println(responseEntity+" send sms");



//                Optional<Role> role_dekan = roleRepository.findRoleByRoleName("ROLE_STUDENT");
//        User user = new User(
//                "kafedra",
//                "kafedra",
//                passwordEncoder.encode("kafedra"),
//                "9876",
//                "guvalakat16032@gmail.com",
//                ganderRepository.getGanderByGandername(Gandername.MALE),
//                new HashSet<>(Collections.singletonList(role_dekan.get()))
//        );
//        Optional<User> userOptional = userRepository.findById("5ad503f7-a0fb-4acb-ba56-b1ee414fdd8d");
//        User user1 = userOptional.get();
//        user1.setPassword(passwordEncoder.encode("AD1153084"));
//        userRepository.saveAndFlush(user1);
//
//        Student student = new Student();
//        Optional<Group> group = groupRepository.findById("910494b7-a590-4c77-b016-f4f007ba065d");
//        student.setUser(user.get());
//        student.setGroup(group.get());

//        Student saveOrUpdate = studentRepository.saveOrUpdate(student);
//
//        Optional<User> user = userRepository.findById("5d5eeca7-ae7d-4e73-8a40-9ccc55cafde2");
//        System.out.println(user.get());
//        PhoneNumber phoneNumber = new PhoneNumber();
//        phoneNumber.setPhoneNumber("993361603");
//        phoneNumber.setUser(user.get());
//        phoneNumber.setPhoneType(PhoneType.MOBILE_PHONE);
//        phoneNumberRepository.saveOrUpdate(phoneNumber);

        // DEKAN
//        Optional<Role> role_dekan = roleRepository.findRoleByRoleName("ROLE_DEKAN");
//        User user = new User(
//                "dekan",
//                "dekan",
//                passwordEncoder.encode("dekan"),
//                "5678",
//                "guvalakat1603@gmail.com",
//                ganderRepository.getGanderByGandername(Gandername.MALE),
//                new HashSet<>(Collections.singletonList(role_dekan.get()))
//        );
//        userRepository.saveAndFlush(user);
//
//        Faculty facultyByName = facultyRepository.getFacultyByName("(B.Sc.) TOURISM");
//        dekanRepository.saveOrUpdate(new Dekan(
//           user,
//           new HashSet<>(Collections.singletonList(facultyByName))
//        ));

//        roleRepository.saveOrUpdate(new Role("ROLE_DEKAN"));

//        Role roleA = new Role("ROLE_ADMIN");
//        roleRepository.saveAndFlush(roleA);
//
//        Optional<Role> user = roleRepository.findRoleByRoleName("ROLE_ADMIN");
//        Role role = user.get();
//        userRepository.save(new User(
//                "device admin",
//                "admin",
//                passwordEncoder.encode("device"),
//                "123456798",
//                "1604@gmail.com",
//                ganderRepository.getGanderByGandername(Gandername.MALE),
//                new HashSet<>(Collections.singletonList(role))
//        ));

        if (type.equals("always")) {

            /***======================== BO`LIMLAR ===========================***/
            /*Section section1 = new Section("Rahbariyat");
            Section section2 = new Section("Kadrlar bo`limi");
            Section section3 = new Section("Yuridek bo`limi");
            Section section4 = new Section("Jamoatchilik bilan aloqalar bo`limi Axborot xizmati");
            Section section5 = new Section("Ijro nazorati va murojaatlar bilan ishlash bo`limi");
            Section section6 = new Section("Kanselyariya");
            Section section7 = new Section("Arxiv");
            Section section8 = new Section("O`quv-uslubiy boshqarma");
            Section section9 = new Section("O`quv rejalari va fan dasturlarini joriy etish bo`limi");
            Section section10 = new Section("Registrator bo`limi");
            Section section11 = new Section("Talabalar amalyoti bo`limi");
            Section section12 = new Section("Ta'lim yo`nalishlari rahbarlari");
            Section section13 = new Section("Kafedralar va professor-o`qituvchilar");
            Section section14 = new Section("Talabalar bilan ishlash bo`limi");
            Section section15 = new Section("Koreya madaniyati markazi");
            Section section16 = new Section("Ilmiy bo`lim");
            Section section17 = new Section("Axborot texnologiyalari markazi");
            Section section18 = new Section("Dasturiy va texnik ta;minot bo`limi");
            Section section19 = new Section("Tarmoqlarni boshqarish va axborot xavfsizligi bo`limi");
            Section section20 = new Section("Axborot-resurs markazi");
            Section section21 = new Section("Innovatsiya markazi");
            Section section22 = new Section("Ta'lim sifatini nazorat qilish bo`limi");
            Section section23 = new Section("Xalqaro hamkorlik bo`limi");
            Section section24 = new Section("Uzluksiz ta'lim markazi");
            Section section25 = new Section("Marketing va bitiruvchilar bo`limi");
            Section section26 = new Section("Buxgalteriya");
            Section section27 = new Section("Tahririy-nashriyot bo`limi");
            Section section28 = new Section("Tibbiy bo`lim");
            Section section29 = new Section("Jamoat xavfsizligini ta'minlash bo`limi");
            Section section30 = new Section("Texnik foydalanish va xo`jalik bo`limi");

            sectionRepository.saveAndFlush(section1);
            sectionRepository.saveAndFlush(section2);
            sectionRepository.saveAndFlush(section3);
            sectionRepository.saveAndFlush(section4);
            sectionRepository.saveAndFlush(section5);
            sectionRepository.saveAndFlush(section6);
            sectionRepository.saveAndFlush(section7);
            sectionRepository.saveAndFlush(section8);
            sectionRepository.saveAndFlush(section9);
            sectionRepository.saveAndFlush(section10);
            sectionRepository.saveAndFlush(section11);
            sectionRepository.saveAndFlush(section12);
            sectionRepository.saveAndFlush(section13);
            sectionRepository.saveAndFlush(section14);
            sectionRepository.saveAndFlush(section15);
            sectionRepository.saveAndFlush(section16);
            sectionRepository.saveAndFlush(section17);
            sectionRepository.saveAndFlush(section18);
            sectionRepository.saveAndFlush(section19);
            sectionRepository.saveAndFlush(section20);
            sectionRepository.saveAndFlush(section21);
            sectionRepository.saveAndFlush(section22);
            sectionRepository.saveAndFlush(section23);
            sectionRepository.saveAndFlush(section24);
            sectionRepository.saveAndFlush(section25);
            sectionRepository.saveAndFlush(section26);
            sectionRepository.saveAndFlush(section27);
            sectionRepository.saveAndFlush(section28);
            sectionRepository.saveAndFlush(section29);
            sectionRepository.saveAndFlush(section30);
*//*
            System.out.println("saved sections");
            System.out.println(section1.getId() + "\n" + section10.getId());*/

            /***========================  LAVOZIMLAR -- ROLES  ===========================***/

//            ROLE_ADMIN
//ROLE_DEKAN
//ROLE_KAFEDRA
//ROLE_MONITORING
//ROLE_MONITORING_ASSISTANT
//ROLE_REKTOR
//ROLE_STAFF
//ROLE_STUDENT
//ROLE_SUPER_ADMIN
//ROLE_TEACHER
//ROLE_USER

            //--------Ректор
            Role role1 = new Role("ROLE_REKTOR",1011);

            Role role70 = new Role("ROLE_DEKAN");
            Role role71 = new Role("ROLE_KAFEDRA");
            Role role72 = new Role("ROLE_MONITORING");
            Role role73 = new Role("ROLE_MONITORING_ASSISTANT");
            Role role74 = new Role("ROLE_STUDENT");
            Role role75 = new Role("ROLE_TEACHER");
            Role role76 = new Role("ROLE_STAFF");
            Role role77 = new Role("ROLE_ADMIN");
            Role role78 = new Role("ROLE_USER");

//            Role role2 = new Role("O`quv ishlari bo`yicha prorektor");
//            Role role3 = new Role("Innovatsiya va ilmiy ishlar bo`yicha prorektor");
//            Role role4 = new Role("Ta'lim sifati va xalqaro hamkorlik bo`yicha prorektor");
//            Role role5 = new Role("Moliya-xo`jalik ishlar bo`yicha prorektor");
//
//            //--------Кадрлар бўлими
//            Role role6= new Role("Boshqarma boshlig`i");
//            Role role7 = new Role("Bo`lim boshlig`i");
//            Role role8 = new Role("Bosh mutaxassis");
//            Role role9 = new Role("Inspektor");
//            Role role10 = new Role("Yetakchi mutaxassis");
//            Role role11= new Role("Yuriskonsult");
//            Role role12 = new Role("Veb-dizayner");
//            Role role13 = new Role("Kantselyariya mudiri");
//            Role role14 = new Role("Arxivarius");
//            Role role15 = new Role("Dispetcher");
//            Role role16 = new Role("Uslubchi");
//            Role role17 = new Role("Ta'lim yo`nalishi rahbari");
//            Role role18 = new Role("Ta'lim yo`nalishi rahbari o`rinbosari");
//            Role role19 = new Role("Markaz boshlig`i");
//            Role role20 = new Role("Dasturchi-muhandis");
//            Role role21 = new Role("Muhandis-elektronchi");
//            Role role22 = new Role("Texnik");
//            Role role23 = new Role("Tizim administratori");
//            Role role24 = new Role("Ma'lumotlar bazasi administratori");
//            Role role25 = new Role("Tarmoq administratori");
//            Role role26 = new Role("Bibliograf");
//            Role role27 = new Role("Kutubxonachi");
//            Role role28 = new Role("Kompyuter operatori");
//            Role role29 = new Role("Laboratoriya mudiri");
//            Role role30 = new Role("Laborant");
//            Role role31 = new Role("Muhandis-dizayner");
//            Role role32 = new Role("Muhandis-konstruktor");
//            Role role33 = new Role("Muhandis-dasturchi");
//            Role role34 = new Role("Marketolog");
//            Role role35 = new Role("Tahrirlovchi");
//            Role role36 = new Role("Kompyuter operatori");
//
//            Role role37 = new Role("Bosh buxgalter");
//            Role role38 = new Role("Buxgalter");
//            Role role39 = new Role("Buxgalter-kassir");
//
//            Role role40 = new Role("Shifokor");
//            Role role41 = new Role("Hamshira");
//            Role role42 = new Role("Psixolog");
//
//            Role role43 = new Role("Qoravul");
//            Role role44 = new Role("Bosh muhandis");
//            Role role45 = new Role("Mehnat va texnika xavfsizligi muhandisi");
//            Role role46 = new Role("Energetik");
//            Role role47 = new Role("Komendant");
//            Role role48 = new Role("Shofyor");
//            Role role49 = new Role("Ombor mudiri");
//            Role role50 = new Role("Elektrik");
//            Role role51 = new Role("Santexnik");
//            Role role52 = new Role("Farrosh");
//            Role role53 = new Role("Bog`bon");
//            Role role54 = new Role("Qozonxona operatori");
//            Role role55 = new Role("Duradgor");
//
//
//
//            Role role56 = new Role("Kafedra mudiri");
//            Role role57 = new Role("Professor");
//            Role role58 = new Role("Dotsent");
//            Role role59 = new Role("Katta o`qituvchi");
//            Role role60 = new Role("O`qituvchi");
            Role role61 = new Role("Assistent");
            Role role62 = new Role("Ish yurituvchi");

/***
 *
            //--------Юридик бўлим

            //--------Жамоатчилик билан алоқалар бўлими - Ахборот хизмати

            //--------Ижро назорати ва мурожаатлар билан ишлаш бўлими (2)


            //--------Канцелярия

            //--------Архив

            //--------Ўқув-услубий бошқарма

            //--------Ўқув режалари ва фан дастурларини жорий этиш бўлими

            //---------Регистратор бўлими

            //---------Талабалар амалиёти бўлими

            //---------Таълим йўналишлари раҳбарлари

            //---------Кафедралар ва профессор-ўқитувчилар


            //---------Талабалар билан ишлаш бўлими


            //---------Корея маданияти маркази

            //---------Илмий бўлим

            //---------Ахборот технологиялари маркази

            //---------Дастурий ва техник таъминот бўлими


            //---------Тармоқларни бошқариш ва ахборот хавфсизлиги бўлими


            //---------Ахборот-ресурс маркази


            //---------Инновация маркази



            //---------Таълим сифатини назорат қилиш бўлими


            //---------Халқаро ҳамкорлик бўлими

            //---------Узлуксиз таълим маркази

            //---------Маркетинг ва битирувчилар бўлими


            //---------Бухгалтерия


            //---------Таҳририй-нашриёт бўлими


            //---------Тиббий бўлим


            //---------Жамоат хавфсизлигини таъминлаш бўлими


            //---------Техник фойдаланиш ва хўжалик бўлими

***/

            roleRepository.saveAndFlush(role1);
//            roleRepository.saveAndFlush(role2);
//            roleRepository.saveAndFlush(role3);
//            roleRepository.saveAndFlush(role4);
//            roleRepository.saveAndFlush(role5);
//            roleRepository.saveAndFlush(role6);
//            roleRepository.saveAndFlush(role7);
//            roleRepository.saveAndFlush(role8);
//            roleRepository.saveAndFlush(role9);
//            roleRepository.saveAndFlush(role10);
//            roleRepository.saveAndFlush(role11);
//            roleRepository.saveAndFlush(role12);
//            roleRepository.saveAndFlush(role13);
//            roleRepository.saveAndFlush(role14);
//            roleRepository.saveAndFlush(role15);
//            roleRepository.saveAndFlush(role16);
//            roleRepository.saveAndFlush(role17);
//            roleRepository.saveAndFlush(role18);
//            roleRepository.saveAndFlush(role19);
//            roleRepository.saveAndFlush(role20);
//            roleRepository.saveAndFlush(role21);
//            roleRepository.saveAndFlush(role22);
//            roleRepository.saveAndFlush(role23);
//            roleRepository.saveAndFlush(role24);
//            roleRepository.saveAndFlush(role25);
//            roleRepository.saveAndFlush(role26);
//            roleRepository.saveAndFlush(role27);
//            roleRepository.saveAndFlush(role28);
//            roleRepository.saveAndFlush(role29);
//            roleRepository.saveAndFlush(role30);
//            roleRepository.saveAndFlush(role31);
//            roleRepository.saveAndFlush(role32);
//            roleRepository.saveAndFlush(role33);
//            roleRepository.saveAndFlush(role34);
//            roleRepository.saveAndFlush(role35);
//            roleRepository.saveAndFlush(role36);
//            roleRepository.saveAndFlush(role37);
//            roleRepository.saveAndFlush(role38);
//            roleRepository.saveAndFlush(role39);
//            roleRepository.saveAndFlush(role40);
//            roleRepository.saveAndFlush(role41);
//            roleRepository.saveAndFlush(role42);
//            roleRepository.saveAndFlush(role43);
//            roleRepository.saveAndFlush(role44);
//            roleRepository.saveAndFlush(role45);
//            roleRepository.saveAndFlush(role46);
//            roleRepository.saveAndFlush(role47);
//            roleRepository.saveAndFlush(role48);
//            roleRepository.saveAndFlush(role49);
//            roleRepository.saveAndFlush(role50);
//            roleRepository.saveAndFlush(role51);
//            roleRepository.saveAndFlush(role52);
//            roleRepository.saveAndFlush(role53);
//            roleRepository.saveAndFlush(role54);
//            roleRepository.saveAndFlush(role55);
//            roleRepository.saveAndFlush(role56);
//            roleRepository.saveAndFlush(role57);
//            roleRepository.saveAndFlush(role58);
//            roleRepository.saveAndFlush(role59);
//            roleRepository.saveAndFlush(role60);
            roleRepository.saveAndFlush(role61);
            roleRepository.saveAndFlush(role62);


            roleRepository.saveAndFlush(role70);
            roleRepository.saveAndFlush(role71);
            roleRepository.saveAndFlush(role72);
            roleRepository.saveAndFlush(role73);
            roleRepository.saveAndFlush(role74);
            roleRepository.saveAndFlush(role75);
            roleRepository.saveAndFlush(role76);
            roleRepository.saveAndFlush(role77);
            roleRepository.saveAndFlush(role78);


//            Role user = roleRepository.saveOrUpdate(new Role("Student"));




            /***=====================  GANDER  ============================***/
            Gander male = ganderRepository.save(new Gander(Gandername.MALE));
            Gander feMale = ganderRepository.save(new Gander(Gandername.FEMALE));


            /***=====================  USER  ============================***/
            System.err.println("saved roles");


            userRepository.save(new User(
                    Integer.toString(1),
                    "user",
                    "user",
                    passwordEncoder.encode("1234"),
                    "12345678",
                    "user@gmail.com",
                    ganderRepository.getGanderByGandername(Gandername.MALE),
                    new HashSet<>(Collections.singletonList(role1))

            ));
            System.out.println("saqlandi!!!!");
        }
//        else {
//            Role user = roleRepository.saveOrUpdate(new Role("user"));
//            userRepository.saveOrUpdate(new User(
//                    1L,
//                    "user2",
//                    "user2",
//                    passwordEncoder.encode("4321"),
//                    "12345679",
//                    "user1@gmail.com",
//                    ganderRepository.getGanderByGandername(Gandername.MALE),
//                    new HashSet<>(Collections.singletonList(user)),
//                    true,
//                    true,
//                    true,
//                    true
//
//            ));
//        }
    }
}
