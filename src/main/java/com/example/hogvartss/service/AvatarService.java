package com.example.hogvartss.service;

import com.example.hogvartss.entity.Avatar;
import com.example.hogvartss.entity.Student;
import com.example.hogvartss.exceotion.AvatarNotFoundException;
import com.example.hogvartss.exceotion.AvatarProcessingException;
import com.example.hogvartss.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final Path pathToAvatarDir;

    public AvatarService(AvatarRepository avatarRepository,
                         @Value("${path.to.avatar.dir}") String pathToAvatarDir) {
        this.avatarRepository = avatarRepository;
        this.pathToAvatarDir = Path.of(pathToAvatarDir);
    }

    public Avatar create(Student student, MultipartFile multipartFile) {
        try {
            String contentType = multipartFile.getContentType();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            byte[] data = multipartFile.getBytes();
            String fileName = UUID.randomUUID() + "." + extension;
            Path pathToAvatar = pathToAvatarDir.resolve(fileName);
            Files.write(pathToAvatar, data);

            Avatar avatar = avatarRepository.findByStudent_Id(student.getId())
                    .orElse(new Avatar());

            if (avatar.getFilePath() != null) {
                Files.delete(Path.of(avatar.getFilePath()));
            }

            avatar.setMediaType(contentType);
            avatar.setFileSize(data.length);
            avatar.setData(data);
            avatar.setStudent(student);
            avatar.setFilePath(pathToAvatar.toString());
            return avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }
    public Pair<byte[], String> getFromDb(long id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }
    private byte[] read(Path path) {
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }
    public Pair<byte[], String> getFromFs(long id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(read(Path.of(avatar.getFilePath())), avatar.getMediaType());
    }

}

