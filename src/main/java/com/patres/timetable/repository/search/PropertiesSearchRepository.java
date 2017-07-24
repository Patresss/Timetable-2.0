package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Properties;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Properties entity.
 */
public interface PropertiesSearchRepository extends ElasticsearchRepository<Properties, Long> {
}
