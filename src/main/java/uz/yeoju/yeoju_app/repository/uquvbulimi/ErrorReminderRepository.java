package uz.yeoju.yeoju_app.repository.uquvbulimi;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.uquvbulim.ErrorReminder;

import java.util.List;

public interface ErrorReminderRepository extends JpaRepository<ErrorReminder, String> {
    List<ErrorReminder> findAllByCreatedByAndActiveOrderByCreatedAtDesc(String createdBy, Boolean active);
}
