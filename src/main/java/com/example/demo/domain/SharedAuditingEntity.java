package com.example.demo.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SharedAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "created_timestamp", updatable = false)
    @JsonIgnore
    @CreationTimestamp
    private Instant createdTimestamp;

    @Column(name = "updated_timestamp")
    @JsonIgnore
    @UpdateTimestamp
    private Instant updatedTimestamp;

}
