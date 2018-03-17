import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Lesson} from './lesson.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class LessonService extends DivisionOwnerEntityService<Lesson> {

    constructor(http: Http) {
        super(http, 'lessons')
    }

}
