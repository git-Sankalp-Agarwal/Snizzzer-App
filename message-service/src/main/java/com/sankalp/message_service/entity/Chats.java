package com.sankalp.message_service.entity;

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
@Table(name = "chats")
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Participant participantOne;

    @OneToOne
    private Participant participantTwo;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
 //   @JoinColumn(name = "chat_id")
    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime chatCreationTime;

    @UpdateTimestamp
    private LocalDateTime lastMessageAt;

}
