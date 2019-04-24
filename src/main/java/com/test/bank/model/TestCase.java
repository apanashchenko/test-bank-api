package com.test.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.bank.enums.TestCaseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"projectId", "title"})
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
    private Date createdAt = new Date();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId")
    private Project project;
}
