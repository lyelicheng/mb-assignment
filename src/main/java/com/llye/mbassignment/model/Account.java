package com.llye.mbassignment.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "customer_id_fk")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @OneToMany(
            mappedBy = "account",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH,
                    CascadeType.REMOVE
            },
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;
}
