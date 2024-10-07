package propensi.propensiun.abuya.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
// @Table(name = "admin", uniqueConstraints = {
// @UniqueConstraint(columnNames = "username"),
// @UniqueConstraint(columnNames = "email")
// })
public class MemberModel {
    private String username;
    private String name;
    // TODO: password

    private Long phoneNum;
    private Integer securityQuestion;
    private String securtiyAns;
    private Integer promoId;
    private List<FeedbackModel> feedbackList;
}
