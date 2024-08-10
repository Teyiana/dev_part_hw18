package org.example.dto;

import lombok.Data;

@Data
public class NoteDTO {
    private long id;
    private long userId;
    private String title;
    private String content;
}
