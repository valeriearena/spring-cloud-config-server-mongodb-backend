package org.springframework.cloud.config.server.mongodb.environment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "settings")
public class MongoDocument {

    @Id
    private String id;
    private String moduleName;
    private String key;
    private String value;
    private String settingLabel;
    private String description;
    private String type;
    private int order;
    private int status;

}