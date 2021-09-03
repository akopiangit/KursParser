package akopyan.project.kursparser.parser.service;

import akopyan.project.kursparser.parser.model.KursEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISiteParseService {
  List<KursEntity> getCourseInfo ();

  ResponseEntity<String> fetchSkillbox();
}
