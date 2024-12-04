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
    public List<FeedbackModel> getFeedbacksbyStoreId(Integer storeId) {
        return feedbackDb.findByStoreUuid(storeId);
    };

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

    @Override
    public float calculateCombinedAverage() {
        List<FeedbackModel> feedbacks = feedbackDb.findAll(); // Get all feedback entries

        float totalScore = 0.0f; // Sum of all scores across aspects
        int totalFeedbacks = feedbacks.size();

        // Sum up all scores for all feedbacks
        for (FeedbackModel feedback : feedbacks) {
            totalScore += feedback.getMenu();
            totalScore += feedback.getPelayanan();
            totalScore += feedback.getFasilitas();
        }

        // Calculate combined average
        int totalScores = totalFeedbacks * 3; // Each feedback has 3 scores
        return totalScores > 0 ? totalScore / totalScores : 0.0f;
    }
}

