package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Period;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Period entity.
 */
public interface PeriodSearchRepository extends ElasticsearchRepository<Period, Long> {
}
