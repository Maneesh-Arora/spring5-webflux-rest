package guru.springframework.spring5webfluxrest.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;

    @Builder
    public Category(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
