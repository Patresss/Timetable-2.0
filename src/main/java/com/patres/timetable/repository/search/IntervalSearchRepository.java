package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Interval;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Interval entity.
 */
public interface IntervalSearchRepository extends ElasticsearchRepository<Interval, Long> {
}
