import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Subject} from './subject.model';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';

@Injectable()
export class SubjectService extends DivisionOwnerEntityService<Subject> {

    constructor(http: Http) {
        super(http, 'subjects')
    }

}
