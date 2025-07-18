package com.jocata.dto;

import java.util.List;

public class SourceDTO {

    private List<String> sources;

    private String target;

    public <T> SourceDTO(List<T> list, String target) {
        this.sources = (List<String>) list;
        this.target = target;
    }

    public List<String> getSources() {
        return sources;
    }
    public void setSource(List<String> sources) {
        this.sources = sources;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
