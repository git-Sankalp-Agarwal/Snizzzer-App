package com.sankalp.message_service.entity;

import com.sankalp.message_service.entity.enums.MessageStatus;
import com.sankalp.message_service.entity.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageContent;

    private String messageSender;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @CreationTimestamp
    private LocalDateTime messageCreationTime;

    @UpdateTimestamp
    private LocalDateTime editedAt;

}