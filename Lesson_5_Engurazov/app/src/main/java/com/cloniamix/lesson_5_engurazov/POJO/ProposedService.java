package com.cloniamix.lesson_5_engurazov.POJO;

public class ProposedService {
    private String name;
    private String content;
    private String address;
    private String imageUrl;

    public ProposedService(String name, String content, String address, String imageUrl) {
        this.name = name;
        this.content = content;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
