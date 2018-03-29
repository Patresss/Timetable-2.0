import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Lesson} from './lesson.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';
import {Time} from '../../util/time.model';
import {Timetable} from '../timetable/timetable.model';

@Injectable()
export class LessonService extends DivisionOwnerEntityService<Lesson> {

    constructor(http: Http) {
        super(http, 'lessons')
    }

    convertEntity(entity: Lesson) {
        if (entity.startTimeString) {
            entity.startTime = new Time(entity.startTimeString);
        }
        if (entity.endTimeString) {
            entity.endTime = new Time(entity.endTimeString);
        }
    }

    convert(lesson: Lesson): Lesson {
        const copy: Lesson = Object.assign({}, lesson);
        copy.startTimeString = Time.createTimeFromTimePicker(lesson.startTime).getFormatted();
        copy.endTimeString = Time.createTimeFromTimePicker(lesson.endTime).getFormatted();
        return copy;
    }

}
