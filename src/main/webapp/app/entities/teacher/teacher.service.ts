import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {DivisionOwnerEntityService} from '../division-owner-entity.service';
import {Teacher} from './teacher.model';

@Injectable()
export class TeacherService extends DivisionOwnerEntityService<Teacher> {

    constructor(http: Http) {
        super(http, 'teachers')
    }

}
