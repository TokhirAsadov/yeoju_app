package uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter;

import java.sql.Timestamp;

public interface GetDownloadCountAndFileName {
    String getFileId();
    String getFileName();
    Integer getCount();
    Timestamp getLastDownloadTime();
}
