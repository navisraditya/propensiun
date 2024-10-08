//package propensi.propensiun.abuya.model;
//
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import java.util.List;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//// @Table(name = "admin", uniqueConstraints = {
//// @UniqueConstraint(columnNames = "username"),
//// @UniqueConstraint(columnNames = "email")
//// })
//public class MemberModel {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer uuid;
//
//    @Column(unique = true)
//    @Size(min = 6, message = "Username must at least 6 characters long")
//    private String username;
//
//    private String name;
//
//    @Column(name="password", nullable=false)
//    @NotNull
//    @Size(min = 8, message = "Password must contain at least one uppercase letter, one number, and be at least 8 characters long")
//    @Pattern(regexp = "^[A-Za-z0-9]*$")
//    private String password;
//
//    @Size(min = 10, max = 13,  message = "phoneNum must at least 10 and less than 13 characters long")
//    private Long phoneNum;
//
//    private Integer securityQuestion;
//
//    private String securtiyAns;
//
//    private Integer promoId;
//
////    private List<FeedbackModel> feedbackList;
//}
