package uz.yeoju.yeoju_app.repository.educationYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearsForCRUD;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearsForSelected;
import uz.yeoju.yeoju_app.payload.educationYear.WeekRestDtoForDean;
import uz.yeoju.yeoju_app.payload.resDto.educationYear.WeekOfEducationYearResDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.StudentsDynamicAttendance;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EducationYearRepository extends JpaRepository<EducationYear,String> {

    Optional<EducationYear> findEducationYearByName(String name);


    @Query(value = "select id,name from groups where faculty_id=?1",nativeQuery = true)
    List<EducationYearsForSelected> getGroupsByFacultyId(String id);

    @Query(value = "select Top 1 w.weekNumber+1 from WeekOfEducationYear w join dbo.EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id\n" +
            "                          where EYWOEY.EducationYear_id=?1 order by w.createdAt desc",nativeQuery = true)
    Integer getSortNumberOfWeek(String educationYearId);


    @Query(value = "select al.time, :week as week, :weekday as weekday,  :section as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.id=:userId",nativeQuery = true)
    Set<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserId(
            @Param("userId") String userId,
            @Param("room") String room,
            @Param("year") Integer year,
            @Param("week") Integer week,
            @Param("weekday") Integer weekday,
            @Param("section") Integer section
    );


    @Query(value = "select * from dbo.GetTimesForRoomStatisticsByUserId(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    Set<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserIdNEW(
            String userId,
            String room,
            Integer year,
            Integer week,
            Integer weekday,
            Integer section
    );

    @Query(value = "select * from dbo.GetTimesForRoomStatisticsByUserIdUnion2(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    Set<StudentsDynamicAttendance> getTimesForRoomStatisticsByUserIdUnionNEW(
            String userId,
            String room,
            Integer year,
            Integer week,
            Integer weekday,
            Integer section
    );

    @Query(value = "SELECT\n" +
            "        al.time,\n" +
            "        :week AS week,\n" +
            "        :weekday AS weekday,\n" +
            "        :section AS section\n" +
            "    FROM\n" +
            "        acc_monitor_log al\n" +
            "    JOIN acc_door ad ON ad.device_id = al.device_id\n" +
            "    JOIN users u ON CAST(u.RFID AS VARCHAR) = CAST(al.card_no AS VARCHAR) COLLATE Chinese_PRC_CI_AS\n" +
            "    WHERE\n" +
            "        al.time BETWEEN CAST(DATEADD(DAY, (:weekday - 1), DATEADD(WK, :week - 1, DATEFROMPARTS(:year, 1, 1))) AS DATETIME) + \n" +
            "                        DATEADD(HOUR, 8 + (:section - 1), CAST('1900-01-01' AS DATETIME)) AND\n" +
            "                        CAST(DATEADD(DAY, (:weekday - 1), DATEADD(WK, :week - 1, DATEFROMPARTS(:year, 1, 1))) AS DATETIME) +\n" +
            "                        DATEADD(HOUR, 9 + (:section - 1), CAST('1900-01-01' AS DATETIME))                        \n" +
            "        AND ad.door_name LIKE :room\n" +
            "        AND u.id = :userId",nativeQuery = true)
    Set<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserId2(
            @Param("userId") String userId,
            @Param("room") String room,
            @Param("year") Integer year,
            @Param("week") Integer week,
            @Param("weekday") Integer weekday,
            @Param("section") Integer section
    );


    @Query(value = "select [dbo].[GetMonitorLogData](:week,:weekday,:section,:year,:room,:userId)",nativeQuery = true)
    Set<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserId3(
            @Param("userId") String userId,
            @Param("room") String room,
            @Param("year") Integer year,
            @Param("week") Integer week,
            @Param("weekday") Integer weekday,
            @Param("section") Integer section
    );

    @Query(value = "select id,name from EducationYear order by createdAt desc",nativeQuery = true)
    Set<EducationYearsForSelected> getEducationYearsForSelected();

    @Query(value = "select id,name,start,[end] from EducationYear order by createdAt desc",nativeQuery = true)
    Set<EducationYearsForCRUD> getEducationYearsForCRUD();

    @Query(value = "select Top 1 id,name from EducationYear order by createdAt desc",nativeQuery = true)
    EducationYearsForSelected getEducationYearsForSelected2();

    @Query(value = "select w.id,w.start,w.sortNumber,w.course,w.eduType from WeekOfEducationYear w join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id where EYWOEY.EducationYear_id=?1 and w.eduType =?2",nativeQuery = true)
    Set<WeekRestDtoForDean> getWeeksByEduIdAndEduType(String educationYearId, String eduType);

    @Query(value = "select Top 1 WOEY.* from EducationYear ey join EducationYear_WeekOfEducationYear e_w on ey.id = e_w.EducationYear_id\n" +
            "join WeekOfEducationYear WOEY on e_w.weeks_id = WOEY.id\n" +
            "join groups g on g.level = WOEY.course\n" +
            "join EducationType ET on g.educationType_id = ET.id\n" +
            "where WOEY.eduType like ET.name and g.name=?1\n" +
            "order by WOEY.sortNumber desc",nativeQuery = true)
    WeekOfEducationYearResDto getWeekOfEducationLast(String groupName);

    @Query(value = "select Top 1 al.time as cardNo\n" +
            "         from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         \n" +
            "where u.id=?1 and al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)",nativeQuery = true)
    Date getTimesForRoomStatisticsByUserIdTime(String id);

    @Query(value = "select * from dbo.GetTimesForRoomStatisticsByUserIdTime(?1)",nativeQuery = true)
    Date getTimesForRoomStatisticsByUserIdTimeNEW(String id);
}
