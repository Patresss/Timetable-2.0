package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Division;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Division entity.
 */
public interface DivisionSearchRepository extends ElasticsearchRepository<Division, Long> {
}
