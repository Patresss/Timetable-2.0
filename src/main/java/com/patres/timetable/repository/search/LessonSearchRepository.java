package com.patres.timetable.repository.search;

import com.patres.timetable.domain.Lesson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lesson entity.
 */
public interface LessonSearchRepository extends ElasticsearchRepository<Lesson, Long> {
}
