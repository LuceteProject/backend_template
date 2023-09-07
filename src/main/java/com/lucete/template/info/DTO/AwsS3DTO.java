package com.lucete.template.info.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3DTO {
    private String key;
    private String path;

    @Builder
    public AwsS3DTO(String key, String path) {
        this.key = key;
        this.path = path;
    }
}