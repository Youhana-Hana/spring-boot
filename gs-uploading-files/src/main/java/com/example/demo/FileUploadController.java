package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    @Autowired
    FileStorage fileStorage;

    @GetMapping("/")
    public String listUploadedFiles(Model model) {

        model.addAttribute("files", fileStorage.loadAll()
                .collect(Collectors.toList()));

        return "uploadForm";
    }

}
