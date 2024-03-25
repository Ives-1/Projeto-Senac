package com.senac.ProjetoFotos.controller;

import com.senac.ProjetoFotos.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AlbumController {

    @Autowired
    AlbumService albumService;
    
    //@PostMapping("/album")
    
}
