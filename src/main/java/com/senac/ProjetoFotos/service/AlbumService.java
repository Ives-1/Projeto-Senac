package com.senac.ProjetoFotos.service;

import com.senac.ProjetoFotos.data.AlbumEntity;
import com.senac.ProjetoFotos.data.AlbumRepository;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
        Path path = Paths.get(rootDir, "midias", album.getNome());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                album.setCaminho(path.toString());
            }
        }
        return album;
    }

    //salva os arquivos na pasta criada dentro de medias
    public String salvarArquivos(MultipartFile[] files, String caminhoDoAlbum) {
        StringBuilder fileNames = new StringBuilder();

        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(caminhoDoAlbum, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + " ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return "Arquivos carregados com sucesso: " + fileNames.toString();
    }

    public Resource download(String codigo) throws FileNotFoundException {
        try {
            AlbumEntity album = albumRepository.findByCodigo(codigo);
            String rootDir = System.getProperty("user.dir");
            Path midiasPath = Paths.get(rootDir, "midias");
            Path filePath = midiasPath.resolve(Paths.get(album.getCaminho())).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado " + codigo);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Arquivo não encontrado " + codigo + e);
        }
    }

}
