package propensi.propensiun.abuya.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.propensiun.abuya.model.FeedbackModel;

@Repository
public interface FeedbackDb extends JpaRepository<FeedbackModel, Integer> {

}
