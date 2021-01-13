package com.mzl0101.diary.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysStorageDto implements Serializable {
    private static final long serialVersionUID = -2701475846181102822L;
    private String storageName;
    private String storagePath;
}
