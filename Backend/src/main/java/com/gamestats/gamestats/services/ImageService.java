package com.gamestats.gamestats.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamestats.gamestats.entities.Image;
import com.gamestats.gamestats.repositories.ImageRepository;
import com.gamestats.gamestats.util.ImageUtil;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    // public String post(MultipartFile file) throws IOException {

    //     Image image = new Image();
    //     image.setName(file.getOriginalFilename());
    //     image.setType(file.getContentType());
    //     image.setImage(ImageUtil.compressImage(file.getBytes()));

    //     // imageRepository.save(Image.builder()
    //     // .name(file.getOriginalFilename())
    //     // .type(file.getContentType())
    //     // .image(ImageUtil.compressImage(file.getBytes())).build());

    //     imageRepository.save(image);

    //     return "Image uploaded successfully";
    // }

    public byte[] get(String nameString) {
        Optional<Image> file = imageRepository.findByName(nameString);
        if (file.isPresent())
            return ImageUtil.decompressImage(file.get().getImage());
        return null;
    }
}
