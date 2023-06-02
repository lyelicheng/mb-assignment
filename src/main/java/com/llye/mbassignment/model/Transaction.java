package com.llye.mbassignment.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private String transactionDate;

    @Column(name = "transaction_time", nullable = false)
    private String transactionTime;

    @Version
    private Long version;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "account_id",
            foreignKey = @ForeignKey(name = "account_id_fk")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Account account;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
