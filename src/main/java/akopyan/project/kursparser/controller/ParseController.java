package akopyan.project.kursparser.controller;

import akopyan.project.kursparser.parser.model.KursEntity;
import akopyan.project.kursparser.parser.service.ISiteParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class ParseController {

    private final ISiteParseService siteParseService;

    @GetMapping
    public List<KursEntity> getList() {
        return siteParseService.getCourseInfo();
    }


    @GetMapping("/fetch")
    public ResponseEntity<String> fetchSkillBox() {
        return siteParseService.fetchSkillbox();
    }
}
