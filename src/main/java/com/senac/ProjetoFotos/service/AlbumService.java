package com.senac.ProjetoFotos.service;

import com.senac.ProjetoFotos.data.AlbumEntity;
import com.senac.ProjetoFotos.data.AlbumRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    //criando um album
    public AlbumEntity criar(AlbumEntity album) {
        album.setId(null);
        albumRepository.save(album);
        return album;
    }

    //obtendo album por código
    public AlbumEntity buscarPorCodigo(String codigo) {
        return albumRepository.findByCodigo(codigo);
    }

    //cria um diretório para alocar as midias
    public AlbumEntity criarDiretorio(AlbumEntity album) {
        String rootDir = System.getProperty("user.dir");
        Path path = Paths.get(rootDir, "medias", album.getNome());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return album;
    }
    
    public String salvarArquivos(MultipartFile[] files){
        String uploadDirectory = System.getProperty("user.dir") + "/upload";
        StringBuilder fileNames = new StringBuilder();
        
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename()+ " ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return "Arquivos carregados com sucesso: " + fileNames.toString();
    }

}
