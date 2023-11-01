package org.teamplus.mvc.controller;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
@Controller
public class IndexController {

        @GetMapping("/")
        public String markdownView(@RequestParam(required = false) String page, Model model) throws Exception {
            if (page == null) {
                page="README.md";
            }
            String markdownValueFormLocal = getMarkdownValueFormLocal(page);
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdownValueFormLocal);
            HtmlRenderer renderer = HtmlRenderer.builder().build();

            model.addAttribute("contents", renderer.render(document));

            //모달을 위한 login
            Object login = model.getAttribute("login");
            if(login == null)
                model.addAttribute("login",0);

            return "index";
        }

        public String getMarkdownValueFormLocal(String manualPage) throws Exception {
            StringBuilder stringBuilder = new StringBuilder();
            ClassPathResource classPathResource = new ClassPathResource("markdown/"+manualPage);

            BufferedReader br = Files.newBufferedReader(Paths.get(classPathResource.getURI()));
            br.lines().forEach(line -> stringBuilder.append(line).append("\n"));

            return stringBuilder.toString();
        }
    }


