package akopyan.project.kursparser.parser.service;

import akopyan.project.kursparser.parser.model.KursEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteParseService implements ISiteParseService {

    private final RestTemplate restTemplate;

    public List<KursEntity> getCourseInfo() {
        List<KursEntity> kurses = getList(connect("https://skillbox.ru/courses/"));
        return kurses.stream().peek(kurs -> kurs.setCost(connect(kurs.getLink())
                        .getElementsByClass("price block block--special")
                        .select(".price__item, .price__item--present").not(".price__item--old")
                        .text()))
                .collect(Collectors.toList());
    }

    public Document connect(String link) {
        try {
            return Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (Exception e) {
            log.info("Не удалось подключиться к сайту");
            return null;
        }
    }

    public List<KursEntity> getList(Document doc) {
        String query = "article";
        Elements kursElements = doc.select(query);
        return kursElements.stream().map(element -> KursEntity.builder()
                .name(element.select("a").text())
                .link(element.select("a").attr("href"))
                .build()).collect(Collectors.toList());
    }


    public ResponseEntity<String> fetchSkillbox () {


        try {
            ResponseEntity<String> result = restTemplate.exchange(
                    "https://skillbox.ru/api/v6/ru/sales/skillbox/directions/all/nomenclature/course/?page=40",
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return result;

        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


}
