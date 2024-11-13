package propensi.propensiun.abuya.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import propensi.propensiun.abuya.model.FeedbackModel;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.repository.FeedbackDb;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDb feedbackDb;

    @Override
    public FeedbackModel saveFeedback(FeedbackModel feedback) {
        return feedbackDb.save(feedback);
    }

    @Override
    public List<FeedbackModel> getAllFeedback() {return feedbackDb.findAll();}

    @Override
    public float averageScoreFeedback(FeedbackModel feedback){
        float fasilitas = feedback.getFasilitas();
        float menu = feedback.getMenu();
        float pelayanan = feedback.getPelayanan();
        float average = (fasilitas+menu+pelayanan)/3;
        return average;
    }

    @Override
    public float averageStore(String store){
        return 5;
    }
}
