package com.staselko.HelpDesk.model.entiti;
import com.staselko.HelpDesk.utils.annotations.TextValid;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Comments_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "TEXT", length = 500)
    private String text;

    @Column(name = "DATE")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
}
