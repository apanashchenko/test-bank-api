package com.test.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.bank.enums.TestCaseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"projectId", "title"})
)
@NoArgsConstructor
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String reference;
    private String labels;
    private TestCaseStatus status;
    private String changedBy;
    @Column(updatable = false, insertable = false)
    private Long projectId;
    private Long reviewId;
    private Date createdAt = new Date();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId")
    private Project project;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "caseToReview",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "reviewId")}
    )
    @OrderBy("id")
    private List<Review> reviewList = new ArrayList<>();

    public void addReview(Review review) {
        reviewList.add(review);
        review.setTestCase(this);
    }
}
