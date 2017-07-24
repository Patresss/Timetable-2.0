package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Timetable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Timetable entity.
 */
public interface TimetableSearchRepository extends ElasticsearchRepository<Timetable, Long> {
}
