package org.teamplus.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
@Slf4j
@Controller
public class IndexController {

        @GetMapping("/")
        public String markdownView(@RequestParam(required = false) String page, Model model) throws Exception {
            if (page == null) {
                page="README.md";
            }
            log.info("page: {}", page);
            String markdownValueFormLocal = getMarkdownValueFormLocal(page);
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdownValueFormLocal);
            HtmlRenderer renderer = HtmlRenderer.builder().build();

            model.addAttribute("contents", renderer.render(document));



            return "index"; // index.html을 반환합니다.
        }

        public String getMarkdownValueFormLocal(String manualPage) throws Exception {
            StringBuilder stringBuilder = new StringBuilder();
            ClassPathResource classPathResource = new ClassPathResource("markdown/"+manualPage);

            BufferedReader br = Files.newBufferedReader(Paths.get(classPathResource.getURI()));
            br.lines().forEach(line -> stringBuilder.append(line).append("\n"));

            return stringBuilder.toString();
        }
    }

