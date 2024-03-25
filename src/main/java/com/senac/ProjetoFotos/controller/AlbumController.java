package com.senac.ProjetoFotos.controller;

import com.senac.ProjetoFotos.data.AlbumEntity;

import com.senac.ProjetoFotos.service.AlbumService;
import com.senac.ProjetoFotos.service.SenhaService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @Autowired
    SenhaService senhaService;

    @GetMapping("/album")
    public String inicioAlbum(Model model) {
        model.addAttribute("album", new AlbumEntity());

        return "carregaFotos";
    }

    @PostMapping("/cria")
    public String criarAlbum(@RequestParam("nome") String nome, @RequestParam("codigo") String codigo, @RequestParam("midias") MultipartFile[] midias, @RequestParam("senha") String senha, Model model) {

        senhaService.autentica(senha);
        AlbumEntity album = new AlbumEntity();
        album.setNome(nome);
        album.setCodigo(codigo);
        album = albumService.criarDiretorio(album);
        album = albumService.criar(album);
        String mensagem = albumService.salvarArquivos(midias, album.getCaminho());
        System.out.println(mensagem);
        model.addAttribute("album", new AlbumEntity());

        return "carregaFotos";

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String codigo) throws FileNotFoundException {
        Resource resource = albumService.download(codigo);

        String contentType = null;
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

    }
}
