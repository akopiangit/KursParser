package akopyan.project.kursparser.parser.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KursEntity {

    private String name;

    private String cost;

    private String description;

    private String link;
}
