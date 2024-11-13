package propensi.propensiun.abuya.service;

import java.util.List;

import propensi.propensiun.abuya.model.FeedbackModel;
import propensi.propensiun.abuya.model.StoreModel;

public interface FeedbackService {
    FeedbackModel saveFeedback(FeedbackModel feedback);

    List<FeedbackModel> getAllFeedback();

    float averageScoreFeedback(FeedbackModel feedback);

    float averageStore(String store);
}