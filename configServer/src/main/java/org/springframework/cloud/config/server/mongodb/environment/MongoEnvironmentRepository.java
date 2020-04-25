package org.springframework.cloud.config.server.mongodb.environment;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * MongoEnvironmentRepository stores configuration data using a MongoDB backend.
 *
 * The default implementation uses GIT as a source for configuration repository.
 * MongoEnvironmentRepository is a custom implementation of EnvironmentRepository based on the following:
 * https://github.com/spring-cloud-incubator/spring-cloud-config-server-mongodb
 *
 * EnvironmentRepository represents a place where configuration data  is stored.
 * EnvironmentRepository returns Environment objects containing a list of propertySources.
 * EnvironmentController implements a REST API that clients call for config data:
 *
 * 		URI FORMAT: http://host:ip/application/profile/label
 * 		Banyan URI Format: http://localhost:8888/ModuleA/prod
 * 		Banyan URI Format with shared settings: http://localhost:8888/ModuleA-{shared1^shared2}/prod
 *

 */
@Repository
public class MongoEnvironmentRepository implements EnvironmentRepository {

	private static final String SETTINGS_TABLE = "settings";
	private static final String MODULE_NAME = "moduleName";
	private static final String SETTING_KEY = "key";
	private MongoTemplate mongoTemplate;

	public MongoEnvironmentRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Environment findOne(String moduleNameWithSettings, String profile, String label) {

		String[] moduleNameWithSettingsArray = StringUtils.delimitedListToStringArray(moduleNameWithSettings, "-");
		String moduleName = moduleNameWithSettingsArray[0];

		Query moduleQuery = new Query();
		moduleQuery.addCriteria(Criteria.where(MODULE_NAME).is(moduleName));

		List<MongoDocument> settingList = mongoTemplate.find(moduleQuery, MongoDocument.class, SETTINGS_TABLE);

		if (moduleNameWithSettingsArray.length == 2) {
			String sharedSettingNames = moduleNameWithSettingsArray[1].replace("{","").replace("}","");
			String[] sharedSettingNameArray = StringUtils.delimitedListToStringArray(sharedSettingNames, "^");
			List<String> sharedSettingNameList = new ArrayList<String>(Arrays.asList(sharedSettingNameArray));

			Query sharedSettingsQuery = new Query();
			sharedSettingsQuery.addCriteria(Criteria.where(MODULE_NAME).is("System"));
			sharedSettingsQuery.addCriteria(Criteria.where(SETTING_KEY).in(sharedSettingNameList.toArray()));
			List<MongoDocument> sharedSettingList = mongoTemplate.find(sharedSettingsQuery, MongoDocument.class, SETTINGS_TABLE);
			settingList.addAll(sharedSettingList);
		}

		Environment environment = new Environment(moduleName, profile, null, null, null);

		Map<String, Object> settingMap = new HashMap<>();
		for (MongoDocument document : settingList) {
			settingMap.put(document.getKey(), document.getValue());
			document.setStatus(1);
			mongoTemplate.save(document);
		}

		String sourceName = generateSourceName(moduleName, profile);
		PropertySource propSource = new PropertySource(sourceName, settingMap);
		environment.add(propSource);

		return environment;
	}

	public MongoDocument updateSetting(MongoDocument settingsBean){

		Query settingsQuery = new Query();
		settingsQuery.addCriteria(Criteria.where(MODULE_NAME).is(settingsBean.getModuleName()));
		settingsQuery.addCriteria(Criteria.where(SETTING_KEY).is(settingsBean.getKey()));
		MongoDocument document = mongoTemplate.findOne(settingsQuery, MongoDocument.class, SETTINGS_TABLE);
		document.setValue(settingsBean.getValue());
		mongoTemplate.save(document);
		return document;
	}

	public List<String> getModulesToRefresh(){
		MatchOperation filter = match(new Criteria("status").is(0));
		GroupOperation groupBy = group(MODULE_NAME);
		Aggregation aggregation = newAggregation(filter, groupBy);
		AggregationResults<String> aggregationResults = mongoTemplate.aggregate(aggregation, SETTINGS_TABLE, String.class);
		List<String> moduleNames = aggregationResults.getMappedResults();
		return moduleNames;
	}

	private String generateSourceName(String moduleName, String profile) {
		return String.format("%s-%s", moduleName, profile);
	}

}
