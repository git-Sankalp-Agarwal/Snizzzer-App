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

    @ManyToOne
    @JoinColumn(name = "participant_one_id", referencedColumnName = "id", nullable = false)
    private Participant participantOne;

    @ManyToOne
    @JoinColumn(name = "participant_two_id", referencedColumnName = "id", nullable = false)
    private Participant participantTwo;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime chatCreationTime;

    @UpdateTimestamp
    private LocalDateTime lastMessageAt;

}
