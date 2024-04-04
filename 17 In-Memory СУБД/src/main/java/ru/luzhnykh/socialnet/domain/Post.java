package ru.luzhnykh.socialnet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.Tuple;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;

@Tuple("posts")
@Data
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @Field("postid")
    private String postId;

    @Field("authorid")
    private String authorId;

    @Field("text")
    private String text;

    @Field("postdate")
    @DateTimeFormat(pattern = "yyyy-mm-ddTHH:mm:ss.SSSSSSZ")
    private Instant postDate;
}
